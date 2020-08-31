package com.robertx22.age_of_exile.vanilla_mc.blocks.salvage_station;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ISalvagable;
import com.robertx22.library_of_exile.utils.CLOC;
import com.robertx22.library_of_exile.packets.particles.ParticleEnum;
import com.robertx22.library_of_exile.packets.particles.ParticlePacketData;
import com.robertx22.library_of_exile.tile_bases.BaseTile;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Arrays;
import java.util.List;

public class TileGearSalvage extends BaseTile {

    public static List<Integer> FUEL_SLOTS = Arrays.asList(0);
    public static List<Integer> INPUT_SLOTS = Arrays.asList(1, 2, 3, 4, 5);
    public static List<Integer> OUTPUT_SLOTS = Arrays.asList(6, 7, 8, 9, 10);
    public static int TOTAL_SLOTS_COUNT = FUEL_SLOTS.size() + INPUT_SLOTS.size() + OUTPUT_SLOTS.size();

    @Override
    public List<Integer> inputSlots() {
        return INPUT_SLOTS;
    }

    @Override
    public int getCookTime() {

        return COOK_TIME_FOR_COMPLETION;
    }

    @Override
    public boolean isAutomatable() {
        return true;
    }

    @Override
    public boolean isOutputSlot(int slot) {
        return OUTPUT_SLOTS.contains(slot);
    }

    public static ItemStack getSmeltingResultForItem(ItemStack st) {

        ICommonDataItem data = ICommonDataItem.load(st);

        if (data != null) {
            if (data.isSalvagable(ISalvagable.SalvageContext.SALVAGE_STATION)) {
                return data.getSalvageResult(0);
            }
        } else {

            Item item = st.getItem();
            if (item instanceof ISalvagable) {
                ISalvagable sal = (ISalvagable) item;
                if (sal.isSalvagable(ISalvagable.SalvageContext.SALVAGE_STATION)) {
                    return sal.getSalvageResult(0);
                }
            }
        }

        return ItemStack.EMPTY;

    }

    private static final short COOK_TIME_FOR_COMPLETION = 200; // vanilla value is 200 = 10 seconds

    public TileGearSalvage() {
        super(ModRegistry.BLOCK_ENTITIES.GEAR_SALVAGE);
        itemStacks = new ItemStack[TOTAL_SLOTS_COUNT];
        clear();
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

        SoundUtils.playSound(world, pos, SoundEvents.BLOCK_ANVIL_USE, 0.3F, 1);

        ParticleEnum.sendToClients(
            pos.up(), world, new ParticlePacketData(pos.up(), ParticleEnum.AOE).radius(0.5F)
                .type(ParticleTypes.DUST)
                .amount(15));

        ParticleEnum.sendToClients(
            pos.up(), world, new ParticlePacketData(pos.up(), ParticleEnum.AOE).radius(0.5F)
                .type(ParticleTypes.FLAME)
                .motion(new Vec3d(0, 0, 0))
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

    private boolean canSmelt() {
        return smeltItem(false);
    }

    /**
     * Smelt an input item into an output slot, if possible
     */
    private void smeltItem() {
        smeltItem(true);
    }

    ItemStack result = ItemStack.EMPTY;

    private boolean smeltItem(boolean performSmelt) {
        Integer firstSuitableInputSlot = null;
        Integer firstSuitableOutputSlot = null;

        // finds the first input slot which is smeltable and whose result fits into an
        // output slot (stacking if possible)
        for (int inputSlot : INPUT_SLOTS) {
            if (!itemStacks[inputSlot].isEmpty()) { // isEmpty()

                result = getSmeltingResultForItem(itemStacks[inputSlot]);

                if (!result.isEmpty()) { // isEmpty()

                    boolean merged = false;

                    // find the first suitable output slot- either empty, or with identical item
                    // that has enough space
                    for (int outputSlot : OUTPUT_SLOTS) {
                        ItemStack outputStack = itemStacks[outputSlot];
                        if (outputStack.isEmpty()) { // isEmpty()
                            firstSuitableInputSlot = inputSlot;
                            firstSuitableOutputSlot = outputSlot;

                            break;
                        }

                        if (outputStack.getItem() == result.getItem()
                            && ItemStack.areTagsEqual(outputStack, result)) {
                            int combinedSize = itemStacks[outputSlot].getCount() + result.getCount(); // getStackSize()
                            if (combinedSize <= getMaxCountPerStack() && combinedSize <= itemStacks[outputSlot].getMaxCount()) {
                                firstSuitableInputSlot = inputSlot;
                                firstSuitableOutputSlot = outputSlot;

                                break;

                            }
                        }
                    }

                    boolean anyEmpty = false;
                    for (int outputSlot : OUTPUT_SLOTS) {
                        ItemStack outputStack = itemStacks[outputSlot];
                        if (outputStack.isEmpty()) { // isEmpty()
                            anyEmpty = true;
                            break;
                        }
                    }

                    if (!anyEmpty) {
                        return false;
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
        if (itemStacks[firstSuitableOutputSlot].isEmpty()) { // isEmpty()
            itemStacks[firstSuitableOutputSlot] = result.copy(); // Use deep .copy() to avoid altering the recipe
            result = ItemStack.EMPTY;

        } else {
            int newStackSize = itemStacks[firstSuitableOutputSlot].getCount() + result.getCount();
            itemStacks[firstSuitableOutputSlot].setCount(newStackSize); // setStackSize(), getStackSize()
        }

        markDirty();
        return true;
    }

    @Override
    public boolean isItemValidInput(ItemStack stack) {
        ItemStack result = this.getSmeltingResultForItem(stack);

        return result.isEmpty() == false;
    }

    @Override
    public MutableText getDisplayName() {
        return CLOC.blank("block.mmorpg.salvage_station");
    }

    @Override
    public ScreenHandler createMenu(int num, PlayerInventory inventory, PlayerEntity player) {
        return new ContainerGearSalvage(num, inventory, this, this.getPos());
    }
}