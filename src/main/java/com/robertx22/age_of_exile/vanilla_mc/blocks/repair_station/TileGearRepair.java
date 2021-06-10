package com.robertx22.age_of_exile.vanilla_mc.blocks.repair_station;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.BaseModificationStation;
import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.FuelSlot;
import com.robertx22.library_of_exile.utils.CLOC;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;

public class TileGearRepair extends BaseModificationStation {

    public ItemStack GearSlot() {
        return itemStacks[0];
    }

    public ItemStack CraftItemSlot() {
        return itemStacks[1];
    }

    public TileGearRepair() {
        super(ModRegistry.BLOCK_ENTITIES.GEAR_REPAIR, 2);

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
                    float lvlmulti = 2 - LevelUtils.getMaxLevelMultiplier(data.lvl);

                    return (int) (restore * lvlmulti);
                }
            }
        }
        return 0;
    }

    @Override
    public boolean modifyItem(int number, PlayerEntity player) {
        try {

            ItemStack fuelstack = this.CraftItemSlot();
            int repair = getRepairValueForCurrentItems();
            ItemStack stack = GearSlot();

            if (!stack.isDamaged()) {
                return false;
            }
            if (!Gear.has(stack)) {
                return false;
            }

            int dmg = (stack.getDamage() - repair);

            if (dmg < 0) {
                dmg = 0;
            }

            fuelstack.decrement(1);
            stack.setDamage(dmg);

            SoundUtils.playSound(world, pos, SoundEvents.BLOCK_ANVIL_USE, 0.8f, 1f);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }
}