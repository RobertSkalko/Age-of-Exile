package com.robertx22.age_of_exile.uncommon.effectdatas.interfaces;

import com.robertx22.age_of_exile.uncommon.effectdatas.AttackPlayStyle;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum WeaponTypes {
    None("none", AttackPlayStyle.MELEE, WeaponRange.MELEE, false, 0F, 0),
    Axe("axe", AttackPlayStyle.MELEE, WeaponRange.MELEE, false, 1.25F, 1.25F),
    Hammer("hammer", AttackPlayStyle.MELEE, WeaponRange.MELEE, false, 1.5F, 0.7F),
    Mace("mace", AttackPlayStyle.MELEE, WeaponRange.MELEE, false, 1.2F, 0.8F),
    Scythe("scythe", AttackPlayStyle.MAGIC, WeaponRange.MELEE, false, 1F, 0.75F),
    Spear("spear", AttackPlayStyle.MELEE, WeaponRange.MELEE, false, 1.25F, 0.75F),
    Trident("trident", AttackPlayStyle.MELEE, WeaponRange.OPTIONALLY_RANGED, false, 1.2F, 1F),
    Sword("sword", AttackPlayStyle.MELEE, WeaponRange.MELEE, false, 0.9F, 1F),
    Bow("bow", AttackPlayStyle.RANGED, WeaponRange.RANGED, true, 0.8F, 1F),
    CrossBow("crossbow", AttackPlayStyle.RANGED, WeaponRange.RANGED, true, 0.9F, 1F),
    Wand("wand", AttackPlayStyle.MAGIC, WeaponRange.MELEE, false, 0.9F, 1F);

    WeaponTypes(String id, AttackPlayStyle style, WeaponRange range, boolean isProjectile, float statMulti, float atkPerSec) {
        this.id = id;
        this.style = style;
        this.range = range;
        this.isProjectile = isProjectile;
        this.statMulti = statMulti;
        this.atkPerSec = atkPerSec;
    }

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
