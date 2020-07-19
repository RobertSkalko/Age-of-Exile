package com.robertx22.mine_and_slash.vanilla_mc.blocks.salvage_station;

import com.robertx22.mine_and_slash.mmorpg.ModRegistry;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.ISalvagable;
import com.robertx22.mine_and_slash.uncommon.localization.CLOC;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.SoundUtils;
import com.robertx22.mine_and_slash.vanilla_mc.blocks.bases.BaseTile;
import com.robertx22.mine_and_slash.vanilla_mc.items.misc.ItemCapacitor;
import com.robertx22.mine_and_slash.vanilla_mc.packets.particles.ParticleEnum;
import com.robertx22.mine_and_slash.vanilla_mc.packets.particles.ParticlePacketData;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class TileGearSalvage extends BaseTile {

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

    public ItemCapacitor getCapacitor() {

        if (!itemStacks[FIRST_CAPACITOR_SLOT].isEmpty()) {

            Item item = itemStacks[FIRST_CAPACITOR_SLOT].getItem();

            if (item instanceof ItemCapacitor) {
                return (ItemCapacitor) item;
            }

        }
        return null;

    }

    @Override
    public boolean isAutomatable() {
        return true;
    }

    @Override
    public boolean isOutputSlot(int slot) {
        return slot >= FIRST_OUTPUT_SLOT && slot <= FIRST_OUTPUT_SLOT + OUTPUT_SLOTS_COUNT;
    }

    private float getCapacitorBonus() {

        ItemStack stack = itemStacks[FIRST_CAPACITOR_SLOT];

        if (stack.getItem() instanceof ItemCapacitor) {
            ItemCapacitor cap = (ItemCapacitor) stack.getItem();

            return cap.getSalvageBonusChance();
        }

        return 0;

    }

    public ItemStack getSmeltingResultForItem(ItemStack st) {

        float bonus = getCapacitorBonus();

        ICommonDataItem data = ICommonDataItem.load(st);

        if (data != null) {
            if (data.isSalvagable(ISalvagable.SalvageContext.SALVAGE_STATION)) {
                return data.getSalvageResult(bonus);
            }
        } else {

            Item item = st.getItem();
            if (item instanceof ISalvagable) {
                ISalvagable sal = (ISalvagable) item;
                if (sal.isSalvagable(ISalvagable.SalvageContext.SALVAGE_STATION)) {
                    return sal.getSalvageResult(bonus);
                }
            }
        }

        return ItemStack.EMPTY;

    }

    // IMPORTANT STUFF ABOVE

    // Create and initialize the itemStacks variable that will store store the
    // itemStacks
    public static final int INPUT_SLOTS_COUNT = 5;
    public static final int OUTPUT_SLOTS_COUNT = 5;
    public static final int TOTAL_SLOTS_COUNT = INPUT_SLOTS_COUNT + OUTPUT_SLOTS_COUNT + 1;

    public static final int FIRST_INPUT_SLOT = 0;
    public static final int FIRST_OUTPUT_SLOT = FIRST_INPUT_SLOT + INPUT_SLOTS_COUNT;
    public static final int FIRST_CAPACITOR_SLOT = FIRST_OUTPUT_SLOT + OUTPUT_SLOTS_COUNT;

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
        for (int inputSlot = FIRST_INPUT_SLOT; inputSlot < FIRST_INPUT_SLOT + INPUT_SLOTS_COUNT; inputSlot++) {
            if (!itemStacks[inputSlot].isEmpty()) { // isEmpty()

                result = getSmeltingResultForItem(itemStacks[inputSlot]);

                if (!result.isEmpty()) { // isEmpty()

                    boolean merged = false;

                    // find the first suitable output slot- either empty, or with identical item
                    // that has enough space
                    for (int outputSlot = FIRST_OUTPUT_SLOT; outputSlot < FIRST_OUTPUT_SLOT + OUTPUT_SLOTS_COUNT; outputSlot++) {
                        ItemStack outputStack = itemStacks[outputSlot];
                        if (outputStack.isEmpty()) { // isEmpty()
                            firstSuitableInputSlot = inputSlot;
                            firstSuitableOutputSlot = outputSlot;

                            break;
                        }

                        if (outputStack.getItem() == result.getItem()
                            && ItemStack.areTagsEqual(outputStack, result)) {
                            int combinedSize = itemStacks[outputSlot].getCount() + result.getCount(); // getStackSize()
                            if (combinedSize <= getInvMaxStackAmount() && combinedSize <= itemStacks[outputSlot].getMaxCount()) {
                                firstSuitableInputSlot = inputSlot;
                                firstSuitableOutputSlot = outputSlot;

                                break;

                            }
                        }
                    }

                    boolean anyEmpty = false;
                    for (int outputSlot = FIRST_OUTPUT_SLOT; outputSlot < FIRST_OUTPUT_SLOT + OUTPUT_SLOTS_COUNT; outputSlot++) {
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
    public Container createMenu(int num, PlayerInventory inventory, PlayerEntity player) {
        return new ContainerGearSalvage(num, inventory, this, this.getPos());
    }
}