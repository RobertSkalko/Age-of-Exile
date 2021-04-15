package com.robertx22.age_of_exile.uncommon.effectdatas.interfaces;

import com.robertx22.age_of_exile.uncommon.effectdatas.AttackPlayStyle;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum WeaponTypes {
    None("none", 0, AttackPlayStyle.MELEE, WeaponRange.MELEE, false, 0F, 0),
    Axe("axe", 15, AttackPlayStyle.MELEE, WeaponRange.MELEE, false, 1.25F, 1.25F),
    Hammer("hammer", 0, AttackPlayStyle.MELEE, WeaponRange.MELEE, false, 1.5F, 0.7F),
    Mace("mace", 15, AttackPlayStyle.MELEE, WeaponRange.MELEE, false, 1.2F, 0.8F),
    Staff("staff", 0, AttackPlayStyle.MAGIC, WeaponRange.MELEE, false, 1F, 1F),
    Dagger("dagger", 25, AttackPlayStyle.MELEE, WeaponRange.MELEE, false, 0.7F, 1.5F),
    Scythe("scythe", 0, AttackPlayStyle.MAGIC, WeaponRange.MELEE, false, 1F, 0.75F),
    Spear("spear", 0, AttackPlayStyle.MELEE, WeaponRange.MELEE, false, 1.25F, 0.75F),
    Trident("trident", 0, AttackPlayStyle.MELEE, WeaponRange.OPTIONALLY_RANGED, false, 1.2F, 1F),
    Sword("sword", 15, AttackPlayStyle.MELEE, WeaponRange.MELEE, false, 0.9F, 1F),
    Glove("glove", 25, AttackPlayStyle.MELEE, WeaponRange.MELEE, false, 0.8F, 1.5F),
    Bow("bow", 0, AttackPlayStyle.RANGED, WeaponRange.RANGED, true, 0.8F, 1F),
    CrossBow("crossbow", 0, AttackPlayStyle.RANGED, WeaponRange.RANGED, true, 0.9F, 1F),
    Wand("wand", 0, AttackPlayStyle.MAGIC, WeaponRange.MELEE, false, 0.9F, 1F),
    Scepter("scepter", 0, AttackPlayStyle.MAGIC, WeaponRange.MELEE, false, 0.9F, 1F);

    WeaponTypes(String id, int weapon_offhand_stat_util, AttackPlayStyle style, WeaponRange range, boolean isProjectile, float statMulti, float atkPerSec) {
        this.id = id;
        this.style = style;
        this.range = range;
        this.isProjectile = isProjectile;
        this.statMulti = statMulti;
        this.weapon_offhand_stat_util = weapon_offhand_stat_util;
        this.atkPerSec = atkPerSec;
    }

    public int weapon_offhand_stat_util;
    public AttackPlayStyle style;
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
            .filter(x -> x != None)
            .collect(Collectors.toList());

    }

    public float getVanillaItemAttackSpeedModifier() {
        return -4 + atkPerSec; // 4 is the base atk speed
    }
}
