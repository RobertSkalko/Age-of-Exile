package com.robertx22.age_of_exile.database.data.gear_types.bases;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.aoe_data.base.DataGenKey;
import com.robertx22.age_of_exile.aoe_data.datapacks.JsonUtils;
import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializable;
import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gear_types.weapons.mechanics.NormalWeaponMechanic;
import com.robertx22.age_of_exile.database.data.gear_types.weapons.mechanics.WeaponMechanic;
import com.robertx22.age_of_exile.database.data.groups.GearRarityGroup;
import com.robertx22.age_of_exile.database.data.groups.GearRarityGroups;
import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.data.stats.types.speed.AttackSpeed;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.GearMaterialRegister;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackPlayStyle;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.CraftEssenceItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public final class BaseGearType implements IAutoLocName, ISerializedRegistryEntry<BaseGearType>, ISerializable<BaseGearType> {

    public static BaseGearType SERIALIZER = new BaseGearType();

    public float attacksPerSecond = 1;
    protected String guid;
    protected LevelRange level_range;
    public String gear_slot = "";
    public String rar_group = GearRarityGroups.NON_UNIQUE_ID;
    public int weight = 1000;
    public int weapon_offhand_stat_util = 0;
    public AttackPlayStyle style = AttackPlayStyle.MELEE;

    public List<StatModifier> implicit_stats = new ArrayList<>();
    public List<StatModifier> base_stats = new ArrayList<>();

    public WeaponTypes weapon_type = WeaponTypes.None;
    public StatRequirement stat_reqs = new StatRequirement();
    public TagList tags = new TagList();

    public transient CraftEssenceItem essenceItem = null;
    protected transient String locname;

    public BaseGearType(DataGenKey<GearSlot> slot, String guid, LevelRange levelRange, String locname) {
        this.guid = guid;
        this.level_range = levelRange;
        this.locname = locname;
        this.gear_slot = slot.GUID();
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
        return Database.GearSlots()
            .get(gear_slot);
    }

    @Override
    public final String locNameForLangFile() {
        return locname;
    }

    public String item_id = "";

    public Item getItem() {
        return Registry.ITEM.get(new Identifier(item_id));
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
        if (isWeaponOrTool()) {
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
            .getCalculatedStat(AttackSpeed.getInstance()));
    }

    public final float getAttacksPerSecondCalculated(StatData stat) {

        float multi = stat
            .getMultiplier();

        float f = multi * attacksPerSecond;

        return f;
    }

    public final float getAttacksPerSecondForTooltip(GearItemData gear) {
        return attacksPerSecond;
    }

    public final boolean hasUniqueItemVersions() {

        List<UniqueGear> uniques = Database.UniqueGears()
            .getFilterWrapped(x -> {
                return x.getPossibleGearTypes()
                    .stream()
                    .anyMatch(e -> e.GUID()
                        .equals(GUID()));
            }).list;

        return !uniques.isEmpty();

    }

    public final boolean isWeaponOrTool() {
        return this.family()
            .equals(SlotFamily.Weapon) || family() == SlotFamily.Tool;
    }

    public final boolean isTool() {
        return family() == SlotFamily.Tool;
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
        fishing_rod(SlotFamily.Tool),
        pickaxe(SlotFamily.Tool),
        hoe(SlotFamily.Tool),

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
        wand(SlotFamily.Weapon),
        crossbow(SlotFamily.Weapon),

        boots(SlotFamily.Armor),
        helmet(SlotFamily.Armor),
        pants(SlotFamily.Armor),
        chest(SlotFamily.Armor),

        necklace(SlotFamily.Jewelry),
        ring(SlotFamily.Jewelry),
        shield(SlotFamily.OffHand),

        cloth(SlotFamily.NONE),
        plate(SlotFamily.NONE),
        leather(SlotFamily.NONE),

        mage_weapon(SlotFamily.NONE), melee_weapon(SlotFamily.NONE), ranged_weapon(SlotFamily.NONE),

        magic_shield_stat(SlotFamily.NONE),
        armor_stat(SlotFamily.NONE),
        dodge_stat(SlotFamily.NONE),

        warrior_casting_weapon(SlotFamily.Weapon),
        ranger_casting_weapon(SlotFamily.Weapon),

        weapon_family(SlotFamily.NONE),
        armor_family(SlotFamily.NONE),
        jewelry_family(SlotFamily.NONE),
        offhand_family(SlotFamily.NONE),
        tool_family(SlotFamily.NONE),

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
        Tool,
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

    private static HashMap<String, HashMap<Item, Boolean>> CACHED_GEAR_SLOTS = new HashMap<>();

    // has to use ugly stuff like this cus datapacks.
    public static boolean isGearOfThisType(BaseGearType slot, Item item) {

        if (item == Items.AIR) {
            return false;
        }

        String id = slot.getGearSlot()
            .GUID();

        if (!CACHED_GEAR_SLOTS.containsKey(id)) {
            CACHED_GEAR_SLOTS.put(id, new HashMap<>());
        }
        if (CACHED_GEAR_SLOTS.get(id)
            .containsKey(item)) {
            return CACHED_GEAR_SLOTS.get(id)
                .get(item);
        }

        boolean bool = false;

        try {
            if (item instanceof ArmorItem) {
                if (slot.getVanillaSlotType() != null) {
                    if (slot.getVanillaSlotType()
                        .equals(((ArmorItem) item).getSlotType())) {
                        bool = true;
                    }
                }
            } else if (slot.getTags()
                .contains(SlotTag.sword)) {
                bool = item instanceof SwordItem;
            } else if (slot.getTags()
                .contains(SlotTag.bow)) {
                bool = item instanceof BowItem;
            } else if (slot.getTags()
                .contains(SlotTag.axe)) {
                bool = item instanceof AxeItem;
            } else if (slot.getTags()
                .contains(SlotTag.shield)) {
                bool = item instanceof ShieldItem;
            } else if (slot.getTags()
                .contains(SlotTag.crossbow)) {
                bool = item instanceof CrossbowItem;
            }

            CACHED_GEAR_SLOTS.get(id)
                .put(item, bool);

            return bool;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    public static List<SlotTag> SLOT_TYPE_TAGS = Arrays.asList(
        SlotTag.sword, SlotTag.axe, SlotTag.wand,
        SlotTag.boots, SlotTag.chest, SlotTag.pants, SlotTag.helmet,
        SlotTag.bow, SlotTag.crossbow,
        SlotTag.ring, SlotTag.necklace
    );

    public SlotTag getSlotType() {

        List<SlotTag> list = new ArrayList<>();

        SLOT_TYPE_TAGS.stream()
            .forEach(x -> {
                if (getTags().contains(x)) {
                    list.add(x);
                }
            });

        if (list.isEmpty()) {
            System.out.println("Item has no slot type tag?!!");
        }
        if (list.size() > 1) {
            System.out.println("Item has more than 1 slot type tag?!");
        }

        return list.get(0);

    }

    public Item getMaterial() {

        TagList tags = getTags();

        GearMaterialRegister.TYPE type = GearMaterialRegister.TYPE.ORE;

        if (tags.contains(SlotTag.cloth)) {
            type = GearMaterialRegister.TYPE.CLOTH;

        }
        if (tags.contains(SlotTag.leather)) {
            type = GearMaterialRegister.TYPE.LEATHER;
        }

        return ModRegistry.GEAR_MATERIALS.MAP.get(type)
            .get(SkillItemTier.of(getLevelRange()).tier);

    }

    @Override
    public String getRarityRank() {
        return IRarity.COMMON_ID;
    }

    @Override
    public Rarity getRarity() {
        return Database.GearRarities()
            .get(getRarityRank());
    }

    public GearRarityGroup getRarityGroup() {
        return Database.GearRarityGroups()
            .get(rar_group);
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.GEAR_TYPE;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Gear_Slots;
    }

    @Override
    public String locNameLangFileGUID() {
        return Ref.MODID + ".gear_type." + formattedGUID();
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
        json.addProperty("item_id", Registry.ITEM.getId(getItem())
            .toString());
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
            o.style = AttackPlayStyle.valueOf(json.get("attack_style")
                .getAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        o.item_id = json.get("item_id")
            .getAsString();

        try {
            o.weapon_type = WeaponTypes.valueOf(json.get("weapon_type")
                .getAsString());
        } catch (Exception e) {
            o.weapon_type = WeaponTypes.None;
        }

        o.tags = new TagList().fromJson(json.getAsJsonObject("tag_list"));

        return o;
    }
}
