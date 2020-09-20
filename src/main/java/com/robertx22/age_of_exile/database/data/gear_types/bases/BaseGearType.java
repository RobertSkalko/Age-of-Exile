package com.robertx22.age_of_exile.database.data.gear_types.bases;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.aoe_data.base.DataGenKey;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.base.Rarities;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gear_types.weapons.mechanics.NormalWeaponMechanic;
import com.robertx22.age_of_exile.database.data.gear_types.weapons.mechanics.WeaponMechanic;
import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.data.stats.types.offense.AttackSpeed;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.registrators.GearSlots;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.datapacks.JsonUtils;
import com.robertx22.age_of_exile.datapacks.bases.ISerializable;
import com.robertx22.age_of_exile.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.datapacks.seriazables.SerializableBaseGearType;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.armor.cloth.ClothBootsItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.armor.cloth.ClothChestItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.armor.cloth.ClothHelmetItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.armor.cloth.ClothPantsItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.armor.leather.LeatherBootsItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.armor.leather.LeatherChestItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.armor.leather.LeatherHelmetItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.armor.leather.LeatherPantsItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.armor.plate.PlateBootsItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.armor.plate.PlateChestItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.armor.plate.PlateHelmetItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.armor.plate.PlatePantsItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.baubles.ItemNecklace;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.baubles.ItemRing;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons.ItemAxe;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons.ItemSword;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons.ItemWand;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.GearCraftEssenceItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public final class BaseGearType implements IAutoLocName, ISerializedRegistryEntry<BaseGearType>, ISerializable<BaseGearType> {

    public BaseGearType SERIALIZER = new BaseGearType();

    public float attacksPerSecond = 1;
    protected String guid;
    protected LevelRange level_range;
    public String gear_slot = "";
    public int weight = 1000;

    public List<StatModifier> implicit_stats = new ArrayList<>();
    public List<StatModifier> base_stats = new ArrayList<>();

    public WeaponTypes weapon_type = WeaponTypes.None;
    public StatRequirement stat_reqs = new StatRequirement();
    public TagList tags = new TagList();

    transient GearCraftEssenceItem essenceItem = null;
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
        return SlashRegistry.GearSlots()
            .get(gear_slot);
    }

    @Override
    public final String locNameForLangFile() {
        return locname;
    }

    public Item getItem() {
        return ModRegistry.GEAR_ITEMS.itemMap.get(GUID());
    }

    public LevelRange getLevelRange() {
        return level_range;
    }

    public final EquipmentSlot getVanillaSlotType() {

        if (getGearSlot().GUID()
            .equals(GearSlots.SHIELD.GUID())) {
            return EquipmentSlot.OFFHAND;
        }
        if (getGearSlot().GUID()
            .equals(GearSlots.BOOTS.GUID())) {
            return EquipmentSlot.FEET;
        }
        if (getGearSlot().GUID()
            .equals(GearSlots.CHEST.GUID())) {
            return EquipmentSlot.CHEST;
        }
        if (getGearSlot().GUID()
            .equals(GearSlots.PANTS.GUID())) {
            return EquipmentSlot.LEGS;
        }
        if (getGearSlot().GUID()
            .equals(GearSlots.HELMET.GUID())) {
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

    public BaseGearType setEssenceItem(GearCraftEssenceItem item) {
        this.essenceItem = item;
        return this;
    }

    public GearCraftEssenceItem getEssenceItem() {
        return this.essenceItem;
    }

    public static class Constants {
        public static float SWORD_ATK_SPEED = 0.75F;
        public static float WAND_ATK_SPEED = 1;
        public static float AXE_ATK_SPEED = 1.25F;
    }

    public final float getAttacksPerSecondCalculated(EntityCap.UnitData data) {
        return getAttacksPerSecondCalculated(data.getUnit()
            .peekAtStat(AttackSpeed.getInstance()));
    }

    public final float getAttacksPerSecondCalculated(StatData stat) {

        float multi = stat
            .getMultiplier();

        float f = multi * attacksPerSecond;

        return f;
    }

    public final float getAttacksPerSecondForTooltip(GearItemData gear) {
        return getAttacksPerSecondForTooltip(gear.GetAllStats(false, false));
    }

    public final float getAttacksPerSecondForTooltip(List<ExactStatData> stats) {
        // only show bonus atk speed from this item

        float speed = attacksPerSecond;

        for (ExactStatData x : stats) {
            if (x.getStat() instanceof AttackSpeed) {
                if (x.getType() == ModType.FLAT) {
                    speed *= 1F + x.getAverageValue() / 100F;
                }
            }
        }
        for (ExactStatData x : stats) {
            if (x.getStat() instanceof AttackSpeed) {
                if (x.getType() == ModType.LOCAL_INCREASE) {
                    speed *= 1F + x.getAverageValue() / 100F;
                }
            }
        }

        return speed;
    }

    public final boolean hasUniqueItemVersions() {

        String id = GUID();

        List<UniqueGear> list = SlashRegistry.UniqueGears()
            .getFilterWrapped(x -> {
                String otherid = x.getBaseGearType()
                    .GUID();

                return otherid.equals(id);
            }).list;

        return !list.isEmpty();

    }

    public final boolean isWeapon() {
        return this.family()
            .equals(SlotFamily.Weapon);
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
        axe(SlotFamily.Weapon),
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

        mage_casting_weapon(SlotFamily.Weapon),
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
        float max = getLevelRange().getEndPercent();

        if (max >= 1F) {
            if (tags.contains(SlotTag.cloth)) {
                return ModRegistry.GEAR_MATERIALS.CLOTH_4;
            }
            if (tags.contains(SlotTag.leather)) {
                return ModRegistry.GEAR_MATERIALS.LEATHER_4;
            } else {
                return ModRegistry.GEAR_MATERIALS.ORE_4;
            }
        }
        if (max >= 0.8F) {
            if (tags.contains(SlotTag.cloth)) {
                return ModRegistry.GEAR_MATERIALS.CLOTH_3;
            }
            if (tags.contains(SlotTag.leather)) {
                return ModRegistry.GEAR_MATERIALS.LEATHER_3;
            } else {
                return ModRegistry.GEAR_MATERIALS.ORE_3;
            }
        }

        if (max >= 0.6F) {
            if (tags.contains(SlotTag.cloth)) {
                return ModRegistry.GEAR_MATERIALS.CLOTH_2;
            }
            if (tags.contains(SlotTag.leather)) {
                return ModRegistry.GEAR_MATERIALS.LEATHER_2;
            } else {
                return ModRegistry.GEAR_MATERIALS.ORE_2;
            }
        }
        if (max >= 0.4F) {
            if (tags.contains(SlotTag.cloth)) {
                return ModRegistry.GEAR_MATERIALS.CLOTH_1;
            }
            if (tags.contains(SlotTag.leather)) {
                return ModRegistry.GEAR_MATERIALS.LEATHER_1;
            } else {
                return ModRegistry.GEAR_MATERIALS.ORE_1;
            }
        }

        if (tags.contains(SlotTag.cloth)) {
            return ModRegistry.GEAR_MATERIALS.CLOTH_0;
        }
        if (tags.contains(SlotTag.leather)) {
            return ModRegistry.GEAR_MATERIALS.LEATHER_0;
        } else {
            return ModRegistry.GEAR_MATERIALS.ORE_0;
        }

    }

    public String[] getRecipePattern() {

        TagList tags = getTags();

        if (tags.contains(SlotTag.sword)) {
            return new String[]{
                " M ",
                " M ",
                " S "
            };
        }
        if (tags.contains(SlotTag.axe)) {
            return new String[]{
                "MM ",
                " S ",
                " S "
            };
        }
        if (tags.contains(SlotTag.wand)) {
            return new String[]{
                "  M",
                " M ",
                "S  "
            };
        }
        if (tags.contains(SlotTag.bow)) {
            return new String[]{
                " MB",
                "M B",
                " MB"
            };
        }
        if (tags.contains(SlotTag.crossbow)) {
            return new String[]{
                "MSM",
                "S S",
                " S "
            };
        }

        if (tags.contains(SlotTag.chest)) {
            return new String[]{
                "M M",
                "MMM",
                "MMM"
            };
        }
        if (tags.contains(SlotTag.boots)) {
            return new String[]{
                "M M",
                "M M"
            };
        }
        if (tags.contains(SlotTag.pants)) {
            return new String[]{
                "MMM",
                "M M",
                "M M"
            };
        }
        if (tags.contains(SlotTag.helmet)) {
            return new String[]{
                "MMM",
                "M M"
            };
        }

        if (tags.contains(SlotTag.necklace)) {
            return new String[]{
                "MMM",
                "M M",
                " M "
            };
        }
        if (tags.contains(SlotTag.ring)) {
            return new String[]{
                " M ",
                "M M",
                " M "
            };
        }

        return null;
    }

    public final Item getItemForRegistration() {

        TagList tags = getTags();

        // TODO TODO TODO REPLACE WHEN FABRIC API GETS SUPPORT FOR BOWS AND SHIELDS ETC
        if (tags.contains(SlotTag.shield)) {
            return Items.SHIELD;
        }
        if (tags.contains(SlotTag.bow)) {
            return Items.BOW;
        }
        if (tags.contains(SlotTag.crossbow)) {
            return Items.CROSSBOW;
        }

        if (tags.contains(SlotTag.sword)) {
            return new ItemSword(locNameForLangFile());
        }
        if (tags.contains(SlotTag.axe)) {
            return new ItemAxe(locNameForLangFile());
        }
        if (tags.contains(SlotTag.wand)) {
            return new ItemWand(locNameForLangFile());
        }

        if (tags.contains(SlotTag.chest) && tags.contains(SlotTag.magic_shield_stat)) {
            return new ClothChestItem(locNameForLangFile(), false);
        }
        if (tags.contains(SlotTag.boots) && tags.contains(SlotTag.magic_shield_stat)) {
            return new ClothBootsItem(locNameForLangFile(), false);
        }
        if (tags.contains(SlotTag.pants) && tags.contains(SlotTag.magic_shield_stat)) {
            return new ClothPantsItem(locNameForLangFile(), false);
        }
        if (tags.contains(SlotTag.helmet) && tags.contains(SlotTag.magic_shield_stat)) {
            return new ClothHelmetItem(locNameForLangFile(), false);
        }

        if (tags.contains(SlotTag.chest) && tags.contains(SlotTag.armor_stat)) {
            return new PlateChestItem(locNameForLangFile(), false);
        }
        if (tags.contains(SlotTag.boots) && tags.contains(SlotTag.armor_stat)) {
            return new PlateBootsItem(locNameForLangFile(), false);
        }
        if (tags.contains(SlotTag.pants) && tags.contains(SlotTag.armor_stat)) {
            return new PlatePantsItem(locNameForLangFile(), false);
        }
        if (tags.contains(SlotTag.helmet) && tags.contains(SlotTag.armor_stat)) {
            return new PlateHelmetItem(locNameForLangFile(), false);
        }

        if (tags.contains(SlotTag.chest) && tags.contains(SlotTag.dodge_stat)) {
            return new LeatherChestItem(locNameForLangFile(), false);
        }
        if (tags.contains(SlotTag.boots) && tags.contains(SlotTag.dodge_stat)) {
            return new LeatherBootsItem(locNameForLangFile(), false);
        }
        if (tags.contains(SlotTag.pants) && tags.contains(SlotTag.dodge_stat)) {
            return new LeatherPantsItem(locNameForLangFile(), false);
        }
        if (tags.contains(SlotTag.helmet) && tags.contains(SlotTag.dodge_stat)) {
            return new LeatherHelmetItem(locNameForLangFile(), false);
        }

        if (tags.contains(SlotTag.necklace)) {
            return new ItemNecklace(locNameForLangFile());
        }
        if (tags.contains(SlotTag.ring)) {
            return new ItemRing(locNameForLangFile());
        }

        return null;

    }

    @Override
    public int getRarityRank() {
        return 0;
    }

    @Override
    public Rarity getRarity() {
        return Rarities.Gears.get(getRarityRank());
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
        json.addProperty("gear_slot", getGearSlot().GUID());
        json.addProperty("weapon_type", weaponType().toString());

        return json;
    }

    @Override
    public BaseGearType fromJson(JsonObject json) {

        SerializableBaseGearType o = new SerializableBaseGearType();

        o.guid = this.getGUIDFromJson(json);
        o.weight = this.getWeightFromJson(json);
        o.level_range = LevelRange.SERIALIZER.fromJson(json.getAsJsonObject("level_range"));
        o.stat_req = StatRequirement.EMPTY.fromJson(json.getAsJsonObject("stat_req"));
        o.base_stats = JsonUtils.getStats(json, "base_stats");
        o.implicit_stats = JsonUtils.getStats(json, "implicit_stats");
        o.gear_slot = json.get("gear_slot")
            .getAsString();
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
