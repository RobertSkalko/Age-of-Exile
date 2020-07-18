package com.robertx22.mine_and_slash.uncommon.datasaving;

import com.robertx22.mine_and_slash.api.MineAndSlashEvents;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.base.LoadSave;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;


public class Gear {

    private static final String LOC = "GEAR_ITEM_DATA";

    public static boolean has(ItemStack stack) {
        return stack.hasTag() && stack.getTag()
            .contains(LOC);
    }

    public static GearItemData Load(ItemStack stack) {

        if (stack == null) {
            return null;
        }
        if (!stack.hasTag()) {
            return null;
        }

        return LoadSave.Load(GearItemData.class, new GearItemData(), stack.getTag(), LOC);

    }

    @Nullable
    public static GearItemData loadOnlyValidWeaponData(ItemStack weapon) {
        if (MineAndSlashEvents.CollectGearStacksEvent.isStackValidGear(weapon)) {
            GearItemData wep = Gear.Load(weapon);
            if (wep != null && wep.GetBaseGearType() != null && wep.GetBaseGearType()
                .family()
                .equals(BaseGearType.SlotFamily.Weapon)) {
                return wep;
            }

        }
        return null;
    }

    public static void Save(ItemStack stack, GearItemData gear) {

        if (stack == null) {
            return;
        }
        if (!stack.hasTag()) {
            stack.setTag(new CompoundTag());
        }
        if (gear != null) {
            LoadSave.Save(gear, stack.getTag(), LOC);
        }

    }

}
