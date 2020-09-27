package com.robertx22.age_of_exile.vanilla_mc.blocks.repair_station;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.BaseModificationStation;
import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.FuelSlot;
import com.robertx22.library_of_exile.utils.CLOC;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.MutableText;
import net.minecraft.util.math.MathHelper;

public class TileGearRepair extends BaseModificationStation {

    public ItemStack GearSlot() {
        return itemStacks[0];
    }

    public ItemStack CraftItemSlot() {
        return itemStacks[1];
    }

    @Override
    public boolean isAutomatable() {
        return true;
    }

    @Override
    public boolean isItemValidInput(ItemStack stack) {
        return true;
    }

    @Override
    public int getCookTime() {
        return COOK_TIME_FOR_COMPLETION;
    }

    @Override
    public boolean isOutputSlot(int slot) {
        return false;
    }

    private static final short COOK_TIME_FOR_COMPLETION = 200; // vanilla value is 200 = 10 seconds

    public TileGearRepair() {
        super(ModRegistry.BLOCK_ENTITIES.GEAR_REPAIR);
        itemStacks = new ItemStack[2];
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

    }

    @Override
    public boolean isCooking() {
        return false;
    }

    @Override
    public int tickRate() {
        return 50;
    }

    @Override
    public void doActionEveryTime() {

    }

    @Override
    public MutableText getDisplayName() {
        return CLOC.blank("block.mmorpg.repair_station");

    }

    @Override
    public ScreenHandler createMenu(int num, PlayerInventory inventory, PlayerEntity player) {
        return new ContainerGearRepair(num, inventory, this, this.getPos());
    }

    public int getRepairValueForCurrentItems() {

        ItemStack fuelstack = this.CraftItemSlot();
        int restore = FuelSlot.FUEL_VALUES.getOrDefault(fuelstack.getItem(), 0);

        if (restore > 0) {

            ItemStack stack = GearSlot();
            if (stack.getItem()
                .isDamageable()) {

                GearItemData data = Gear.Load(stack);
                if (data != null) {
                    float lvlmulti = 1 + LevelUtils.getDistanceFromMaxLevel(data.level) * 0.01F;

                    return (int) (restore * lvlmulti);
                }
            }
        }
        return 0;
    }

    @Override
    public boolean modifyItem() {
        try {

            ItemStack fuelstack = this.CraftItemSlot();
            int repair = getRepairValueForCurrentItems();
            ItemStack stack = GearSlot();

            if (!stack.isDamaged()) {
                return false;
            }

            int dmg = (stack.getDamage() - repair);

            if (dmg < 0) {
                dmg = 0;
            }

            fuelstack.decrement(1);
            stack.setDamage(dmg);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }
}