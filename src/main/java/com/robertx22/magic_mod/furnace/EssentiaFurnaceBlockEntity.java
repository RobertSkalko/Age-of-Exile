package com.robertx22.magic_mod.furnace;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.localization.CLOC;
import com.robertx22.age_of_exile.uncommon.utilityclasses.SoundUtils;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.BaseTile;
import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.EssentiaFuelSlot;
import com.robertx22.age_of_exile.vanilla_mc.packets.particles.ParticleEnum;
import com.robertx22.age_of_exile.vanilla_mc.packets.particles.ParticlePacketData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Arrays;
import java.util.List;

public class EssentiaFurnaceBlockEntity extends BaseTile {
    public static int MaximumFuel = 10000;

    public static List<Integer> FUEL_SLOTS = Arrays.asList(0);
    public static List<Integer> INPUT_SLOTS = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
    public static List<Integer> OUTPUT_SLOTS = Arrays.asList(10, 11, 12, 13, 14, 15, 16, 17, 18);

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

    public ItemStack getSmeltingResultForItem(ItemStack stack) {

        Recipe<?> recipe = this.world.getRecipeManager()
            .getFirstMatch(RecipeType.SMELTING, new SimpleInventory(stack), this.world)
            .orElse(null);

        if (recipe != null) {
            return recipe.getOutput();
        }

        return ItemStack.EMPTY;

    }

    private static final short COOK_TIME_FOR_COMPLETION = 100; // vanilla value is 200 = 10 seconds

    public EssentiaFurnaceBlockEntity() {
        super(ModRegistry.BLOCK_ENTITIES.ESSENTIA_FURNACE);
        itemStacks = new ItemStack[TOTAL_SLOTS_COUNT];
        clear();
    }

    public double fractionOfCookTimeComplete() {
        double fraction = cookTime / (double) getCookTime();
        return MathHelper.clamp(fraction, 0.0, 1.0);
    }

    public int getFuelNeededFor(ItemStack stack) {
        return this.world.getRecipeManager()
            .getFirstMatch(RecipeType.SMELTING, new SimpleInventory(stack), this.world)
            .map(AbstractCookingRecipe::getCookTime)
            .orElse(200);

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

    private void burnFuel() {
        int burningCount = 0;
        boolean inventoryChanged = false;

        for (int num : FUEL_SLOTS) {

            if (this.fuel < this.MaximumFuel) {
                if (!itemStacks[num].isEmpty()) { // isEmpty()

                    int fuelgained = EssentiaFuelSlot.FUEL_VALUES.getOrDefault(itemStacks[num].getItem(), 0);

                    if (fuelgained > 0) {
                        fuel += fuelgained;

                        itemStacks[num].decrement(1); // decreaseStackSize()
                        ++burningCount;
                        inventoryChanged = true;

                    }
                }
            }
        }
        if (inventoryChanged)
            markDirty();
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

        int fuelNeededForStack = 1000000;

        // finds the first input slot which is smeltable and whose result fits into an
        // output slot (stacking if possible)
        for (int inputSlot : INPUT_SLOTS) {
            if (!itemStacks[inputSlot].isEmpty()) { // isEmpty()

                result = getSmeltingResultForItem(itemStacks[inputSlot]);

                fuelNeededForStack = getFuelNeededFor(itemStacks[inputSlot]);

                if (fuelNeededForStack > this.fuel) {
                    burnFuel();
                    return false;
                }

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

        this.fuel -= fuelNeededForStack;

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
        return new EssentiaFurnaceContainer(num, inventory, this, this.getPos());
    }
}
