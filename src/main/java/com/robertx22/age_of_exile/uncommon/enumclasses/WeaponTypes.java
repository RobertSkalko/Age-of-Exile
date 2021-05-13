package com.robertx22.age_of_exile.uncommon.enumclasses;

import com.robertx22.age_of_exile.uncommon.utilityclasses.ErrorUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum WeaponTypes {

    none("none", 0, PlayStyle.melee, WeaponRange.MELEE, false, 0F, 0),
    axe("axe", 15, PlayStyle.melee, WeaponRange.MELEE, false, 1.25F, 1.25F),
    hammer("hammer", 0, PlayStyle.melee, WeaponRange.MELEE, false, 1.5F, 0.7F),
    mace("mace", 15, PlayStyle.melee, WeaponRange.MELEE, false, 1.2F, 0.8F),
    staff("staff", 0, PlayStyle.magic, WeaponRange.MELEE, false, 1F, 1F),
    dagger("dagger", 25, PlayStyle.melee, WeaponRange.MELEE, false, 0.7F, 1.5F),
    scythe("scythe", 0, PlayStyle.magic, WeaponRange.MELEE, false, 1F, 0.75F),
    spear("spear", 0, PlayStyle.melee, WeaponRange.MELEE, false, 1.25F, 0.75F),
    trident("trident", 0, PlayStyle.melee, WeaponRange.OPTIONALLY_RANGED, false, 1.2F, 1F),
    sword("sword", 15, PlayStyle.melee, WeaponRange.MELEE, false, 0.9F, 1F),
    glove("glove", 25, PlayStyle.melee, WeaponRange.MELEE, false, 0.8F, 1.5F),
    bow("bow", 0, PlayStyle.ranged, WeaponRange.RANGED, true, 0.8F, 1F),
    crossbow("crossbow", 0, PlayStyle.ranged, WeaponRange.RANGED, true, 0.9F, 1F),
    wand("wand", 0, PlayStyle.magic, WeaponRange.MELEE, false, 0.9F, 1F),
    scepter("scepter", 0, PlayStyle.magic, WeaponRange.MELEE, false, 0.9F, 1F);

    WeaponTypes(String id, int weapon_offhand_stat_util, PlayStyle style, WeaponRange range, boolean isProjectile, float statMulti, float atkPerSec) {
        this.id = id;
        this.style = style;
        this.range = range;
        this.isProjectile = isProjectile;
        this.statMulti = statMulti;
        this.weapon_offhand_stat_util = weapon_offhand_stat_util;
        this.atkPerSec = atkPerSec;

        ErrorUtils.ifFalse(this.id.equals(this.name()));
    }

    public int weapon_offhand_stat_util;
    public PlayStyle style;
    WeaponRange range;
    public String id;
    public boolean isProjectile;
    public float statMulti;
    public float atkPerSec;

    public String locName() {
        return StringUtils.capitalize(id);
    }

    public boolean isMelee() {
        return this.range == WeaponRange.MELEE;
    }

    public static List<WeaponTypes> getAll() {

        return Arrays.stream(WeaponTypes.values())
            .filter(x -> x != none)
            .collect(Collectors.toList());

    }

    public float getVanillaItemAttackSpeedModifier() {
        return -4 + atkPerSec; // 4 is the base atk speed
    }
}
