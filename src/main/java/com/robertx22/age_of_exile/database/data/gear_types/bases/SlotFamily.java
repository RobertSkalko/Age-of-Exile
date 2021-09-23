package com.robertx22.age_of_exile.database.data.gear_types.bases;

public enum SlotFamily {
    Weapon,
    Armor,
    Jewelry,
    OffHand,
    NONE;

    public boolean isJewelry() {
        return this == Jewelry;
    }

    public boolean isArmor() {
        return this == Armor;
    }

    public boolean isWeapon() {
        return this == Weapon;
    }

    public boolean isOffhand() {
        return this == OffHand;
    }

}
