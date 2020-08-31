package com.robertx22.age_of_exile.vanilla_mc.blocks.repair_station;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.library_of_exile.utils.CLOC;
import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.FuelSlot;
import com.robertx22.library_of_exile.packets.particles.ParticleEnum;
import com.robertx22.library_of_exile.packets.particles.ParticlePacketData;
import com.robertx22.library_of_exile.tile_bases.BaseTile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.MutableText;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

import java.util.Arrays;
import java.util.List;

public class TileGearRepair extends BaseTile {

    public static List<Integer> FUEL_SLOTS = Arrays.asList(0);
    public static List<Integer> INPUT_SLOTS = Arrays.asList(1, 2, 3, 4, 5);
    public static List<Integer> OUTPUT_SLOTS = Arrays.asList(6, 7, 8, 9, 10);
    public static int TOTAL_SLOTS_COUNT = FUEL_SLOTS.size() + INPUT_SLOTS.size() + OUTPUT_SLOTS.size();

    @Override
    public boolean isAutomatable() {
        return true;
    }

    @Override
    public boolean isItemValidInput(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canInsert(int index, ItemStack itemStackIn, Direction direction) {

        if (this.isAutomatable() && containsSlot(index)) {
            // don't insert shit
            return this.isItemValidInput(itemStackIn);
        }

        if (FUEL_SLOTS.contains(index)) {
            if (direction == Direction.NORTH || direction == Direction.EAST ||
                direction == Direction.SOUTH || direction == Direction.WEST) {
                return true;

            }
        }

        return false;
    }

    @Override
    public List<Integer> inputSlots() {
        return INPUT_SLOTS;
    }

    @Override
    public int getCookTime() {

        return COOK_TIME_FOR_COMPLETION;
    }

    @Override
    public boolean isOutputSlot(int slot) {
        return OUTPUT_SLOTS.contains(slot);
    }

    public static int MaximumFuel = 50000;

    public ItemStack getSmeltingResultForItem(ItemStack stack) {
        if (stack.getItem()
            .isDamageable()) {
            ICommonDataItem data = ICommonDataItem.load(stack);
            if (data != null) {
                ItemStack copy = stack.copy();
                int dmg = copy.getDamage() - fuel;

                if (dmg < 0) {
                    dmg = 0;
                }

                copy.setDamage(dmg);

                return copy;
            }
        }
        return ItemStack.EMPTY;

    }

    private static final short COOK_TIME_FOR_COMPLETION = 200; // vanilla value is 200 = 10 seconds

    public TileGearRepair() {
        super(ModRegistry.BLOCK_ENTITIES.GEAR_REPAIR);

        itemStacks = new ItemStack[TOTAL_SLOTS_COUNT];
        clear();
    }

    public double fractionOfCookTimeComplete() {
        double fraction = cookTime / (double) getCookTime();
        return MathHelper.clamp(fraction, 0.0, 1.0);
    }

    @Override
    public int ticksRequired() {
        return getCookTime();
    }

    @Override
    public void finishCooking() {
        this.smeltItem();

        ParticleEnum.sendToClients(
            pos.up(), world, new ParticlePacketData(pos.up(), ParticleEnum.AOE).radius(0.5F)
                .type(ParticleTypes.COMPOSTER)
                .amount(15));

    }

    @Override
    public boolean isCooking() {

        return canSmelt();
    }

    @Override
    public int tickRate() {
        return 2;
    }

    @Override
    public void doActionEveryTime() {

    }

    private int burnFuel() {
        int burningCount = 0;
        boolean inventoryChanged = false;
        // Iterate over all the fuel slots
        for (int fuelSlotNumber : FUEL_SLOTS) {

            if (this.fuel < this.MaximumFuel) {
                if (!itemStacks[fuelSlotNumber].isEmpty()) { // isEmpty()
                    // If the stack in this slot is not null and is fuel, set burnTimeRemaining &
                    // burnTimeInitialValue to the
                    // item's burn time and decrease the stack size

                    int fuelgained = FuelSlot.FUEL_VALUES.getOrDefault(itemStacks[fuelSlotNumber].getItem(), 0);

                    if (fuelgained > 0) {
                        fuel += fuelgained;

                        itemStacks[fuelSlotNumber].decrement(1); // decreaseStackSize()
                        ++burningCount;
                        inventoryChanged = true;

                    }
                }
            }
        }
        if (inventoryChanged)
            markDirty();
        return burningCount;
    }

    private boolean canSmelt() {
        return smeltItem(false);
    }

    private void smeltItem() {
        smeltItem(true);
    }

    private boolean smeltItem(boolean performSmelt) {

        if (this.fuel < 1) {
            this.burnFuel();
            return false;
        }

        Integer firstSuitableInputSlot = null;
        Integer firstOuputSlot = null;
        ItemStack result = ItemStack.EMPTY; // EMPTY_ITEM

        int fuelNeeded = 0;
        float fuelMulti = 1F;

        // finds the first input slot which is smeltable and whose result fits into an
        // output slot (stacking if possible)
        for (int inputSlot : INPUT_SLOTS) {
            if (!itemStacks[inputSlot].isEmpty()) { // isEmpty()

                fuelNeeded = (int) (itemStacks[inputSlot].getDamage() * ModConfig.get().Server.REPAIR_FUEL_NEEDED_MULTI);

                fuelNeeded *= fuelMulti;

                if (fuelNeeded > this.fuel) {
                    this.burnFuel();
                    fuelNeeded = this.fuel;
                }

                if (fuelNeeded <= this.fuel) {
                    result = getSmeltingResultForItem(itemStacks[inputSlot]);

                } else {
                    result = ItemStack.EMPTY;
                }

                if (!result.isEmpty()) { // isEmpty()
                    // find the first suitable output slot- either empty, or with identical item
                    // that has enough space
                    for (int outputSlot : OUTPUT_SLOTS) {
                        ItemStack outputStack = itemStacks[outputSlot];
                        if (outputStack.isEmpty()) { // isEmpty()
                            firstSuitableInputSlot = inputSlot;
                            firstOuputSlot = outputSlot;
                            break;
                        }

                        if (outputStack.getItem() == result.getItem()

                            && ItemStack.areTagsEqual(outputStack, result)) {
                            int combinedSize = itemStacks[outputSlot].getCount() + result.getCount(); // getStackSize()
                            if (combinedSize <= getMaxCountPerStack() && combinedSize <= itemStacks[outputSlot].getMaxCount()) {
                                firstSuitableInputSlot = inputSlot;
                                firstOuputSlot = outputSlot;
                                break;
                            }
                        }
                    }
                    if (firstSuitableInputSlot != null)
                        break;
                }
            }
        }

        if (firstSuitableInputSlot == null)
            return false;
        if (!performSmelt)
            return true;

        // alter input and output
        itemStacks[firstSuitableInputSlot].decrement(1); // decreaseStackSize()
        if (itemStacks[firstSuitableInputSlot].getCount() <= 0) {
            itemStacks[firstSuitableInputSlot] = ItemStack.EMPTY; // getStackSize(), EmptyItem
        }
        if (itemStacks[firstOuputSlot].isEmpty()) { // isEmpty()
            itemStacks[firstOuputSlot] = result.copy(); // Use deep .copy() to avoid altering the recipe
        } else {
            int newStackSize = itemStacks[firstOuputSlot].getCount() + result.getCount();
            itemStacks[firstOuputSlot].setCount(newStackSize); // setStackSize(), getStackSize()
        }

        fuel -= fuelNeeded * fuelMulti;

        markDirty();
        return true;
    }

    @Override
    public MutableText getDisplayName() {
        return CLOC.blank("block.mmorpg.repair_station");

    }

    @Override
    public ScreenHandler createMenu(int num, PlayerInventory inventory, PlayerEntity player) {
        return new ContainerGearRepair(num, inventory, this, this.getPos());
    }
}