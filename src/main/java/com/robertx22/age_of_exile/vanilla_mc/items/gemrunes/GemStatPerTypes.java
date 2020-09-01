package com.robertx22.age_of_exile.vanilla_mc.items.gemrunes;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotFamily;

import java.util.List;

public abstract class GemStatPerTypes {

    public abstract List<StatModifier> onArmor();

    public abstract List<StatModifier> onJewelry();

    public abstract List<StatModifier> onWeapons();

    public final List<StatModifier> getFor(SlotFamily sfor) {
        if (sfor == SlotFamily.Armor) {
            return onArmor();
        }
        if (sfor == SlotFamily.Jewelry) {
            return onJewelry();
        }
        if (sfor == SlotFamily.Weapon) {
            return onWeapons();
        }

        return null;

    }

}