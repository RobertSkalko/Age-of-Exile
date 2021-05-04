package com.robertx22.age_of_exile.aoe_data.database.base_gear_types;

import com.google.common.base.Preconditions;
import com.robertx22.age_of_exile.aoe_data.base.DataGenKey;
import com.robertx22.age_of_exile.aoe_data.database.DataHelper;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.gear_types.bases.TagList;
import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.registrators.LevelRanges;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.CraftEssenceItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class BaseGearBuilder implements DataHelper {

    private PlayStyle style = PlayStyle.melee;
    private String locnamesuffix;
    private String idprefix;
    private DataGenKey<GearSlot> slot;
    private TagList tags;
    private List<LevelRange> lvls = new ArrayList<>();
    private List<StatModifier> basestats = new ArrayList<>();
    private List<StatModifier> implicitstats = new ArrayList<>();
    private StatRequirement req = new StatRequirement();
    private WeaponTypes wep = WeaponTypes.none;
    private int weapon_offhand_stat_util = 0;
    private HashMap<LevelRange, String> namePrefixes = new HashMap<>();
    private float atkspeed = 1F;
    private int weight = 1000;
    private HashMap<LevelRange, Item> itemMap;
    private CraftEssenceItem essenceItem;

    public static BaseGearBuilder of(DataGenKey<GearSlot> slot, String idprefix, String locnamesuffix, HashMap<LevelRange, Item> itemMap) {
        BaseGearBuilder b = new BaseGearBuilder();
        b.locnamesuffix = locnamesuffix;
        b.idprefix = idprefix;
        b.slot = slot;
        b.itemMap = itemMap;
        return b;
    }

    public static BaseGearBuilder weapon(DataGenKey<GearSlot> slot, WeaponTypes type, HashMap<LevelRange, Item> itemMap) {
        BaseGearBuilder b = new BaseGearBuilder();
        b.locnamesuffix = type.locName();
        b.idprefix = type.id;
        b.slot = slot;
        b.itemMap = itemMap;
        b.atkspeed = type.atkPerSec;
        b.weaponType(type);
        b.attackStyle(type.style);
        b.weapon_offhand_stat_util = type.weapon_offhand_stat_util;
        b.baseStat(b.getAttackDamageStat(type, DataHelper.Number.FULL, Elements.Physical));

        return b;
    }

    public BaseGearBuilder attackStyle(PlayStyle style) {
        this.style = style;
        return this;
    }

    public BaseGearBuilder addLvlRange(LevelRange range, String nameprefix) {
        this.lvls.add(range);
        this.namePrefixes.put(range, nameprefix);
        return this;
    }

    public BaseGearBuilder addWarriorLevelRanges() {
        this.addLvlRange(LevelRanges.STARTER, "Trainee");
        this.addLvlRange(LevelRanges.LOW, "Durable");
        this.addLvlRange(LevelRanges.MIDDLE, "Brutal");
        this.addLvlRange(LevelRanges.HIGH, "Soldier");
        this.addLvlRange(LevelRanges.ENDGAME, "Royal");
        return this;
    }

    public BaseGearBuilder addToolLevelRanges() {
        this.addLvlRange(LevelRanges.STARTER, "Old");
        this.addLvlRange(LevelRanges.LOW, "Durable");
        this.addLvlRange(LevelRanges.MIDDLE, "Sturdy");
        this.addLvlRange(LevelRanges.HIGH, "Gilded");
        this.addLvlRange(LevelRanges.ENDGAME, "Masterwork");
        return this;
    }

    public BaseGearBuilder addHunterLevelRanges() {
        this.addLvlRange(LevelRanges.STARTER, "Old");
        this.addLvlRange(LevelRanges.LOW, "Durable");
        this.addLvlRange(LevelRanges.MIDDLE, "Sturdy");
        this.addLvlRange(LevelRanges.HIGH, "Full");
        this.addLvlRange(LevelRanges.ENDGAME, "Hunter");
        return this;
    }

    public BaseGearBuilder addHunterWeaponLevelRanges() {
        this.addLvlRange(LevelRanges.STARTER, "Old");
        this.addLvlRange(LevelRanges.LOW, "Durable");
        this.addLvlRange(LevelRanges.MIDDLE, "Sturdy");
        this.addLvlRange(LevelRanges.HIGH, "Knight's");
        this.addLvlRange(LevelRanges.ENDGAME, "King's");
        return this;
    }

    public BaseGearBuilder addMageLevelRanges() {
        this.addLvlRange(LevelRanges.STARTER, "Novice");
        this.addLvlRange(LevelRanges.LOW, "Apprentice");
        this.addLvlRange(LevelRanges.MIDDLE, "Adept's");
        this.addLvlRange(LevelRanges.HIGH, "Wizard's");
        this.addLvlRange(LevelRanges.ENDGAME, "Archmage");
        return this;
    }

    public BaseGearBuilder addMageWeaponLevelRanges() {
        this.addLvlRange(LevelRanges.STARTER, "Old");
        this.addLvlRange(LevelRanges.LOW, "Ancient");
        this.addLvlRange(LevelRanges.MIDDLE, "Sage");
        this.addLvlRange(LevelRanges.HIGH, "Crystal");
        this.addLvlRange(LevelRanges.ENDGAME, "Ancestral");
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

    public BaseGearBuilder essenceItem(CraftEssenceItem item) {
        this.essenceItem = item;
        return this;
    }

    public BaseGearBuilder baseStat(StatModifier... mod) {
        this.basestats.addAll(Arrays.asList(mod));
        return this;
    }

    public BaseGearBuilder implicitStat(StatModifier mod) {
        this.implicitstats.add(mod);
        return this;
    }

    public BaseGearBuilder req(StatRequirement req) {
        this.req = req;
        return this;
    }

    public HashMap<LevelRange, DataGenKey<BaseGearType>> build() {
        HashMap<LevelRange, DataGenKey<BaseGearType>> map = new HashMap<>();

        lvls.forEach(x -> {
            Preconditions.checkArgument(itemMap.containsKey(x));

            String name = /*namePrefixes.get(x) + " " + */locnamesuffix;
            String id = idprefix + x.id_suffix;
            BaseGearType type = new BaseGearType(slot, id, x, name);
            type.stat_reqs = req;
            type.weapon_type = wep;
            type.tags = tags;
            type.implicit_stats = implicitstats;
            type.base_stats = basestats;
            type.attacksPerSecond = atkspeed;
            type.weight = weight;
            type.style = style;
            type.weapon_offhand_stat_util = weapon_offhand_stat_util;
            type.essenceItem = essenceItem;
            type.item_id = Registry.ITEM.getId(itemMap.get(x))
                .toString();
            map.put(x, new DataGenKey<>(type.GUID()));
            type.addToSerializables();
        });
        return map;

    }

}

