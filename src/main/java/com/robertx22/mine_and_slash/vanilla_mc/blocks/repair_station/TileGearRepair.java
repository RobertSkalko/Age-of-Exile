package com.robertx22.mine_and_slash.vanilla_mc.blocks.repair_station;

import com.robertx22.mine_and_slash.config.forge.ModConfig;
import com.robertx22.mine_and_slash.mmorpg.registers.common.ModTileEntities;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.mine_and_slash.uncommon.localization.CLOC;
import com.robertx22.mine_and_slash.vanilla_mc.blocks.bases.BaseTile;
import com.robertx22.mine_and_slash.vanilla_mc.blocks.slots.FuelSlot;
import com.robertx22.mine_and_slash.vanilla_mc.items.misc.ItemCapacitor;
import com.robertx22.mine_and_slash.vanilla_mc.packets.particles.ParticleEnum;
import com.robertx22.mine_and_slash.vanilla_mc.packets.particles.ParticlePacketData;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;


public class TileGearRepair extends BaseTile {

    @Override
    public boolean isAutomatable() {
        return true;
    }

    @Override
    public boolean isItemValidInput(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canInsertInvStack(int index, ItemStack itemStackIn, Direction direction) {

        if (this.isAutomatable() && containsSlot(index, this.inputSlots())) {
            // don't insert shit
            return this.isItemValidInput(itemStackIn);
        }

        if (FIRST_FUEL_SLOT == index) {
            if (direction == Direction.NORTH || direction == Direction.EAST ||
                direction == Direction.SOUTH || direction == Direction.WEST) {

                return true;

            }
        }

        return false;
    }

    @Override
    public int[] inputSlots() {
        int[] ints = new int[INPUT_SLOTS_COUNT];
        for (int i = 0; i < INPUT_SLOTS_COUNT; i++) {
            ints[i] = FIRST_INPUT_SLOT + i;
        }

        return ints;
    }

    @Override
    public int getCookTime() {

        ItemCapacitor cap = getCapacitor();
        if (cap != null) {
            return (int) (COOK_TIME_FOR_COMPLETION * cap.GetSpeedMultiplier());
        }

        return COOK_TIME_FOR_COMPLETION;
    }

    @Override
    public boolean isOutputSlot(int slot) {
        return slot >= FIRST_OUTPUT_SLOT && slot <= FIRST_OUTPUT_SLOT + OUTPUT_SLOTS_COUNT;
    }

    public ItemCapacitor getCapacitor() {

        if (!itemStacks[FIRST_CAPACITOR_SLOT].isEmpty()) {

            Item item = itemStacks[FIRST_CAPACITOR_SLOT].getItem();

            if (item instanceof ItemCapacitor) {
                return (ItemCapacitor) item;
            }

        }
        return null;

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

                stack.setDamage(dmg);

                return copy;
            }
        }
        return ItemStack.EMPTY;

    }

    // IMPORTANT STUFF ABOVE

    // Create and initialize the itemStacks variable that will store store the
    // itemStacks
    public static final int FUEL_SLOTS_COUNT = 1;
    public static final int INPUT_SLOTS_COUNT = 5;
    public static final int OUTPUT_SLOTS_COUNT = 5;
    public static final int TOTAL_SLOTS_COUNT = FUEL_SLOTS_COUNT + INPUT_SLOTS_COUNT + OUTPUT_SLOTS_COUNT + 1;

    public static final int FIRST_FUEL_SLOT = 0;
    public static final int FIRST_INPUT_SLOT = FIRST_FUEL_SLOT + FUEL_SLOTS_COUNT;
    public static final int FIRST_OUTPUT_SLOT = FIRST_INPUT_SLOT + INPUT_SLOTS_COUNT;
    public static final int FIRST_CAPACITOR_SLOT = FIRST_OUTPUT_SLOT + OUTPUT_SLOTS_COUNT;

    private static final short COOK_TIME_FOR_COMPLETION = 200; // vanilla value is 200 = 10 seconds

    public TileGearRepair() {
        super(ModTileEntities.GEAR_REPAIR.get());

        itemStacks = new ItemStack[TOTAL_SLOTS_COUNT];
        clear();
    }

    /**
     * Returns the amount of fuel remaining on the currently burning item in the
     * given fuel slot.
     *
     * @return fraction remaining, between 0 - 1
     * @fuelSlot the number of the fuel slot (0..3)
     */
    public double fractionOfFuelRemaining(int fuelSlot) {
        if (this.fuel <= 0)
            return 0;
        double fraction = fuel / (double) MaximumFuel;
        return MathHelper.clamp(fraction, 0.0, 1.0);
    }

    /**
     * Returns the amount of cook time completed on the currently cooking item.
     *
     * @return fraction remaining, between 0 - 1
     */
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

        this.burnFuel();

    }

    private int burnFuel() {
        int burningCount = 0;
        boolean inventoryChanged = false;
        // Iterate over all the fuel slots
        for (int i = 0; i < FUEL_SLOTS_COUNT; i++) {
            int fuelSlotNumber = i + FIRST_FUEL_SLOT;

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
                        // If the stack size now equals 0 set the slot contents to the items container
                        // item. This is for fuel
                        // items such as lava buckets so that the bucket is not consumed. If the item
                        // dose not have
                        // a container item getContainerItem returns null which sets the slot contents
                        // to null
                        if (itemStacks[fuelSlotNumber].getCount() == 0) { // getStackSize()
                            itemStacks[fuelSlotNumber] = itemStacks[fuelSlotNumber].getItem()
                                .getContainerItem(itemStacks[fuelSlotNumber]);
                        }
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
            return false;
        }

        Integer firstSuitableInputSlot = null;
        Integer firstOuputSlot = null;
        ItemStack result = ItemStack.EMPTY; // EMPTY_ITEM

        int fuelNeeded = 0;
        float fuelMulti = 1F;

        if (!itemStacks[FIRST_CAPACITOR_SLOT].isEmpty()) {

            Item item = itemStacks[FIRST_CAPACITOR_SLOT].getItem();

            if (item instanceof ItemCapacitor) {
                fuelMulti = ((ItemCapacitor) item).GetFuelMultiplier();
            }

        }

        // finds the first input slot which is smeltable and whose result fits into an
        // output slot (stacking if possible)
        for (int inputSlot = FIRST_INPUT_SLOT; inputSlot < FIRST_INPUT_SLOT + INPUT_SLOTS_COUNT; inputSlot++) {
            if (!itemStacks[inputSlot].isEmpty()) { // isEmpty()

                fuelNeeded = (int) (itemStacks[inputSlot].getDamage() * ModConfig.INSTANCE.Server.REPAIR_FUEL_NEEDED_MULTI.get());

                fuelNeeded *= fuelMulti;

                if (fuelNeeded > this.fuel) {
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
                    for (int outputSlot = FIRST_OUTPUT_SLOT; outputSlot < FIRST_OUTPUT_SLOT + OUTPUT_SLOTS_COUNT; outputSlot++) {
                        ItemStack outputStack = itemStacks[outputSlot];
                        if (outputStack.isEmpty()) { // isEmpty()
                            firstSuitableInputSlot = inputSlot;
                            firstOuputSlot = outputSlot;
                            break;
                        }

                        if (outputStack.getItem() == result.getItem()

                            && ItemStack.areTagsEqual(outputStack, result)) {
                            int combinedSize = itemStacks[outputSlot].getCount() + result.getCount(); // getStackSize()
                            if (combinedSize <= getInvMaxStackAmount() && combinedSize <= itemStacks[outputSlot].getMaxCount()) {
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

        fuel -= fuelNeeded * fuelMulti; // TODO

        markDirty();
        return true;
    }

    @Override
    public Text getDisplayName() {
        return CLOC.blank("block.mmorpg.repair_station");

    }

    @Nullable
    @Override
    public Container createMenu(int num, PlayerInventory inventory, PlayerEntity player) {
        return new ContainerGearRepair(num, inventory, this, this.getPos());
    }
}