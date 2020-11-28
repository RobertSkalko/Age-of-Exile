package com.robertx22.age_of_exile.uncommon.effectdatas.interfaces;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum WeaponTypes {
    None(false, "none", false, false),
    Axe(true, "axe", true, false),
    //Hammer(true, "hammer", true),
    Trident(true, "trident", false, false),
    Bow(true, "bow", false, true),
    Sword(true, "sword", true, false),
    CrossBow(true, "crossbow", false, true),
    Wand(true, "wand", true, false);

    WeaponTypes(boolean isSingleType, String id, boolean melee, boolean isProjectile) {
        this.isSingleType = isSingleType;
        this.id = id;
        this.isMelee = melee;
        this.isProjectile = isProjectile;
    }

    boolean isSingleType = true;
    public String id;
    public boolean isMelee;
    public boolean isProjectile;

    public static List<WeaponTypes> getAll() {

        return Arrays.stream(WeaponTypes.values())
            .filter(x -> x.isSingleType)
            .collect(Collectors.toList());

    }
}
