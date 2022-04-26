package com.robertx22.age_of_exile.aoe_data.database.base_gear_types;

import com.robertx22.age_of_exile.aoe_data.database.GearDataHelper;
import com.robertx22.age_of_exile.database.all_keys.base.BaseGearKey;
import com.robertx22.age_of_exile.database.all_keys.base.GearSlotKey;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.gear_types.bases.TagList;
import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.registrators.LevelRanges;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.library_of_exile.registry.DataGenKey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseGearBuilder implements GearDataHelper {

    private PlayStyle style = PlayStyle.melee;
    private String locnamesuffix;
    private String id;
    private String slot;
    private TagList tags;
    private List<StatModifier> basestats = new ArrayList<>();
    private WeaponTypes wep = WeaponTypes.none;
    private int weapon_offhand_stat_util = 0;
    private float atkspeed = 1F;
    private int weight = 1000;

    public static BaseGearBuilder of(BaseGearKey id, GearSlotKey slot, String locnamesuffix) {
        BaseGearBuilder b = new BaseGearBuilder();
        b.locnamesuffix = locnamesuffix;
        b.id = id.GUID();
        b.slot = slot.id;
        return b;
    }

    public static BaseGearBuilder weapon(BaseGearKey id, GearSlotKey slot, WeaponTypes type) {
        BaseGearBuilder b = new BaseGearBuilder();
        b.locnamesuffix = type.locName();
        b.id = id.GUID();
        b.slot = slot.id;
        b.atkspeed = type.atkPerSec;
        b.weaponType(type);
        b.attackStyle(type.style);
        b.weapon_offhand_stat_util = type.weapon_offhand_stat_util;
        b.baseStat(b.getAttackDamageStat(type, GearDataHelper.Number.FULL, Elements.Physical));

        return b;
    }

    public BaseGearBuilder attackStyle(PlayStyle style) {
        this.style = style;
        return this;
    }

    public BaseGearBuilder tags(TagList tags) {
        this.tags = tags;
        return this;
    }

    public BaseGearBuilder weight(int weight) {
        this.weight = weight;
        return this;
    }

    public BaseGearBuilder weaponType(WeaponTypes wep) {
        this.wep = wep;
        return this;
    }

    public BaseGearBuilder attackSpeed(float speed) {
        this.atkspeed = speed;
        return this;
    }

    public BaseGearBuilder baseStat(StatModifier... mod) {
        this.basestats.addAll(Arrays.asList(mod));
        return this;
    }

    public DataGenKey<BaseGearType> build() {

        LevelRange x = LevelRanges.FULL;

        String name = /*namePrefixes.get(x) + " " + */locnamesuffix;
        String id = this.id;
        BaseGearType type = new BaseGearType(slot, id, x, name);
        type.weapon_type = wep;
        type.tags = tags;
        type.base_stats = basestats;
        type.attacksPerSecond = atkspeed;
        type.weight = weight;
        type.style = style;
        type.weapon_offhand_stat_util = weapon_offhand_stat_util;
        type.addToSerializables();

        return new DataGenKey<>(type.GUID());

    }

}

