package com.robertx22.age_of_exile.uncommon.effectdatas.interfaces;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum WeaponTypes {
    None(false, "none", false),
    Axe(true, "axe", true),
    Hammer(true, "hammer", true),
    Trident(true, "trident", false),
    Bow(true, "bow", false),
    Sword(true, "sword", true),
    CrossBow(true, "crossbow", false),
    Wand(true, "wand", true);

    WeaponTypes(boolean isSingleType, String id, boolean melee) {
        this.isSingleType = isSingleType;
        this.id = id;
        this.isMelee = melee;
    }

    boolean isSingleType = true;
    public String id;
    public boolean isMelee;

    public static List<WeaponTypes> getAll() {

        return Arrays.stream(WeaponTypes.values())
            .filter(x -> x.isSingleType)
            .collect(Collectors.toList());

    }
}
