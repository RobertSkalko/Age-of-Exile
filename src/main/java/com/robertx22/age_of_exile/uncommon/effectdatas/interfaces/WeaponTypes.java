package com.robertx22.age_of_exile.uncommon.effectdatas.interfaces;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum WeaponTypes {
    None(false, "none", false, false, 0F),
    Axe(true, "axe", true, false, 1.25F),
    //Hammer(true, "hammer", true),
    Trident(true, "trident", false, false, 1.2F),
    Bow(true, "bow", false, true, 0.8F),
    Sword(true, "sword", true, false, 0.9F),
    CrossBow(true, "crossbow", false, true, 0.9F),
    Wand(true, "wand", true, false, 0.9F);

    WeaponTypes(boolean isSingleType, String id, boolean melee, boolean isProjectile, float statMulti) {
        this.isSingleType = isSingleType;
        this.id = id;
        this.isMelee = melee;
        this.isProjectile = isProjectile;
        this.statMulti = statMulti;
    }

    boolean isSingleType = true;
    public String id;
    public boolean isMelee;
    public boolean isProjectile;
    public float statMulti;

    public static List<WeaponTypes> getAll() {

        return Arrays.stream(WeaponTypes.values())
            .filter(x -> x.isSingleType)
            .collect(Collectors.toList());

    }
}
