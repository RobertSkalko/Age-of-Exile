package com.robertx22.age_of_exile.database.data.gear_types.bases;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.datapacks.JsonUtils;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gear_types.weapons.mechanics.NormalWeaponMechanic;
import com.robertx22.age_of_exile.database.data.gear_types.weapons.mechanics.WeaponMechanic;
import com.robertx22.age_of_exile.database.data.groups.GearRarityGroups;
import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.CraftEssenceItem;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.registry.serialization.ISerializable;
import net.minecraft.entity.EquipmentSlot;

import java.util.ArrayList;
import java.util.List;

public final class BaseGearType implements IAutoLocName, JsonExileRegistry<BaseGearType>, ISerializable<BaseGearType> {

    public static BaseGearType SERIALIZER = new BaseGearType();

    public float attacksPerSecond = 1;
    protected String guid;
    protected LevelRange level_range;
    public String gear_slot = "";
    public String rar_group = GearRarityGroups.NON_UNIQUE_ID;
    public int weight = 1000;
    public int weapon_offhand_stat_util = 0;
    public PlayStyle style = PlayStyle.melee;

    public List<StatModifier> implicit_stats = new ArrayList<>();
    public List<StatModifier> base_stats = new ArrayList<>();

    public WeaponTypes weapon_type = WeaponTypes.none;
    public StatRequirement stat_reqs = new StatRequirement();
    public TagList tags = new TagList();

    public transient CraftEssenceItem essenceItem = null;
    protected transient String locname;

    public BaseGearType(String slot, String guid, LevelRange levelRange, String locname) {
        this.guid = guid;
        this.level_range = levelRange;
        this.locname = locname;
        this.gear_slot = slot;
    }

    private BaseGearType() {

    }

    public List<StatModifier> implicitStats() {
        return implicit_stats;
    }

    public List<StatModifier> baseStats() {
        return base_stats;
    }

    public WeaponTypes weaponType() {
        return weapon_type;
    }

    public TagList getTags() {
        return tags;
    }

    public StatRequirement getStatRequirements() {
        return stat_reqs;
    }

    @Override
    public final String GUID() {
        return guid;
    }

    public GearSlot getGearSlot() {
        return ExileDB.GearSlots()
            .get(gear_slot);
    }

    @Override
    public final String locNameForLangFile() {
        return locname;
    }

    public LevelRange getLevelRange() {
        return level_range;
    }

    public final EquipmentSlot getVanillaSlotType() {

        if (tags.contains(SlotTag.shield)) {
            return EquipmentSlot.OFFHAND;
        }
        if (tags.contains(SlotTag.boots)) {
            return EquipmentSlot.FEET;
        }
        if (tags.contains(SlotTag.chest)) {
            return EquipmentSlot.CHEST;
        }
        if (tags.contains(SlotTag.pants)) {
            return EquipmentSlot.LEGS;
        }
        if (tags.contains(SlotTag.helmet)) {
            return EquipmentSlot.HEAD;
        }
        if (isWeapon()) {
            return EquipmentSlot.MAINHAND;
        }

        return null;
    }

    public int Weight() {
        return weight;
    }

    public CraftEssenceItem getEssenceItem() {
        return this.essenceItem;
    }

    public final float getAttacksPerSecondCalculated(EntityCap.UnitData data) {
        return getAttacksPerSecondCalculated(data.getUnit()
            .getCalculatedStat(Stats.ATTACK_SPEED.get()));
    }

    public final float getAttacksPerSecondCalculated(StatData stat) {

        float multi = stat
            .getMultiplier();

        float f = multi * attacksPerSecond;

        return f;
    }

    public final boolean isArmor() {
        return family() == SlotFamily.Armor;
    }

    public final boolean isJewelry() {
        return family() == SlotFamily.Jewelry;
    }

    public final boolean isWeapon() {
        return family() == SlotFamily.Weapon;
    }

    public final boolean isMeleeWeapon() {
        return this.getTags()
            .contains(SlotTag.melee_weapon);
    }

    public boolean isShield() {
        return getTags().contains(SlotTag.shield);
    }

    public enum SlotTag {

        sword(SlotFamily.Weapon),
        scepter(SlotFamily.Weapon),
        dagger(SlotFamily.Weapon),
        staff(SlotFamily.Weapon),
        glove(SlotFamily.Weapon),
        hammer(SlotFamily.Weapon),
        mace(SlotFamily.Weapon),
        spear(SlotFamily.Weapon),
        axe(SlotFamily.Weapon),
        scythe(SlotFamily.Weapon),
        bow(SlotFamily.Weapon),
        crossbow(SlotFamily.Weapon),

        boots(SlotFamily.Armor),
        helmet(SlotFamily.Armor),
        pants(SlotFamily.Armor),
        chest(SlotFamily.Armor),

        necklace(SlotFamily.Jewelry),
        ring(SlotFamily.Jewelry),
        shield(SlotFamily.OffHand),

        mage_weapon(SlotFamily.NONE), melee_weapon(SlotFamily.NONE), ranged_weapon(SlotFamily.NONE),

        armor_stat(SlotFamily.NONE),

        warrior_casting_weapon(SlotFamily.Weapon),
        ranger_casting_weapon(SlotFamily.Weapon),

        weapon_family(SlotFamily.NONE),
        armor_family(SlotFamily.NONE),
        jewelry_family(SlotFamily.NONE),
        offhand_family(SlotFamily.NONE),

        intelligence(SlotFamily.NONE),
        dexterity(SlotFamily.NONE),
        strength(SlotFamily.NONE);

        public SlotFamily family = SlotFamily.NONE;

        SlotTag(SlotFamily family) {
            this.family = family;
        }
    }

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

    public final SlotFamily family() {
        return getTags().getFamily();
    }

    public final WeaponMechanic getWeaponMechanic() {
        return new NormalWeaponMechanic();
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.GEAR_TYPE;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Gear_Slots;
    }

    @Override
    public String locNameLangFileGUID() {
        return Ref.MODID + ".gear_type." + GUID();
    }

    public final boolean isMageWeapon() {
        return getTags().contains(SlotTag.mage_weapon);
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = getDefaultJson();

        JsonUtils.addStats(implicitStats(), json, "implicit_stats");
        JsonUtils.addStats(baseStats(), json, "base_stats");

        json.add("level_range", getLevelRange().toJson());
        json.add("tag_list", getTags().toJson());
        json.add("stat_req", getStatRequirements().toJson());
        json.addProperty("gear_slot", this.gear_slot);
        json.addProperty("weapon_offhand_stat_util", this.weapon_offhand_stat_util);
        json.addProperty("rar_group", this.rar_group);
        json.addProperty("weapon_type", weaponType().toString());
        json.addProperty("attack_style", style.name());
        json.addProperty("atk_speed", attacksPerSecond);

        return json;
    }

    @Override
    public BaseGearType fromJson(JsonObject json) {

        BaseGearType o = new BaseGearType();

        o.guid = this.getGUIDFromJson(json);
        o.weight = this.getWeightFromJson(json);
        if (json.has("atk_speed")) {
            o.attacksPerSecond = json.get("atk_speed")
                .getAsFloat();
        }
        o.level_range = LevelRange.SERIALIZER.fromJson(json.getAsJsonObject("level_range"));
        o.stat_reqs = StatRequirement.EMPTY.fromJson(json.getAsJsonObject("stat_req"));
        o.base_stats = JsonUtils.getStats(json, "base_stats");
        o.implicit_stats = JsonUtils.getStats(json, "implicit_stats");
        o.gear_slot = json.get("gear_slot")
            .getAsString();
        if (json.has("rar_group")) {
            o.rar_group = json.get("rar_group")
                .getAsString();
        }
        if (json.has("weapon_offhand_stat_util")) {
            o.weapon_offhand_stat_util = json.get("weapon_offhand_stat_util")
                .getAsInt();
        }

        try {
            o.style = PlayStyle.valueOf(json.get("attack_style")
                .getAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            o.weapon_type = WeaponTypes.valueOf(json.get("weapon_type")
                .getAsString());
        } catch (Exception e) {
            o.weapon_type = WeaponTypes.none;
        }

        o.tags = new TagList().fromJson(json.getAsJsonObject("tag_list"));

        return o;
    }
}
