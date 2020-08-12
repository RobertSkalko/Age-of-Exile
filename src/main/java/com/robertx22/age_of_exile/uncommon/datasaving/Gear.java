package com.robertx22.age_of_exile.uncommon.datasaving;

import com.robertx22.library_of_exile.utils.LoadSave;
import com.robertx22.age_of_exile.api.MineAndSlashEvents;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
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
