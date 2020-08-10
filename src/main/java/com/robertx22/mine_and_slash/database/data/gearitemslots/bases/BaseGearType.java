package com.robertx22.mine_and_slash.database.data.gearitemslots.bases;

import com.google.gson.JsonObject;
import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.mechanics.NormalWeaponMechanic;
import com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.mechanics.WeaponMechanic;
import com.robertx22.mine_and_slash.database.data.level_ranges.LevelRange;
import com.robertx22.mine_and_slash.database.data.stats.types.offense.AttackSpeed;
import com.robertx22.mine_and_slash.database.registry.SlashRegistry;
import com.robertx22.mine_and_slash.database.registry.SlashRegistryType;
import com.robertx22.mine_and_slash.datapacks.JsonUtils;
import com.robertx22.mine_and_slash.datapacks.bases.ISerializable;
import com.robertx22.mine_and_slash.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.mine_and_slash.datapacks.seriazables.SerializableBaseGearType;
import com.robertx22.mine_and_slash.mmorpg.ModRegistry;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.armor.cloth.ClothBootsItem;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.armor.cloth.ClothChestItem;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.armor.cloth.ClothHelmetItem;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.armor.cloth.ClothPantsItem;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.armor.leather.LeatherBootsItem;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.armor.leather.LeatherChestItem;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.armor.leather.LeatherHelmetItem;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.armor.leather.LeatherPantsItem;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.armor.plate.PlateBootsItem;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.armor.plate.PlateChestItem;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.armor.plate.PlateHelmetItem;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.armor.plate.PlatePantsItem;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.baubles.ItemNecklace;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.baubles.ItemRing;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.weapons.ItemAxe;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.weapons.ItemSword;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.weapons.ItemWand;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class BaseGearType implements IAutoLocName, ISerializedRegistryEntry<BaseGearType>, ISerializable<BaseGearType> {

    public float attacksPerSecond = 1;

    public BaseGearType(String guid, LevelRange levelRange, String locname) {
        this.guid = guid;
        this.levelRange = levelRange;
        this.locname = locname;
    }

    private BaseGearType() {

    }

    public abstract List<StatModifier> implicitStats();

    public abstract List<StatModifier> baseStats();

    public WeaponTypes weaponType() {
        return WeaponTypes.None;
    }

    public abstract TagList getTags();

    public abstract StatRequirement getStatRequirements();

    protected String guid;
    protected LevelRange levelRange;
    protected String locname;

    @Override
    public final String GUID() {
        return guid;
    }

    @Override
    public final String locNameForLangFile() {
        return locname;
    }

    public Item getItem() {
        return ModRegistry.GEAR_ITEMS.itemMap.get(GUID());
    }

    public LevelRange getLevelRange() {
        return levelRange;
    }

    public final EquipmentSlot getVanillaSlotType() {

        if (getTags().contains(SlotTag.shield)) {
            return EquipmentSlot.OFFHAND;
        }
        if (getTags().contains(SlotTag.boots)) {
            return EquipmentSlot.FEET;
        }
        if (getTags().contains(SlotTag.chest)) {
            return EquipmentSlot.CHEST;
        }
        if (getTags().contains(SlotTag.pants)) {
            return EquipmentSlot.LEGS;
        }
        if (getTags().contains(SlotTag.helmet)) {
            return EquipmentSlot.HEAD;
        }
        if (isWeapon()) {
            return EquipmentSlot.MAINHAND;
        }

        return null;
    }

    public int Weight() {
        return 1000;
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
        return !SlashRegistry.UniqueGears()
            .getFilterWrapped(x -> x.getBaseGearType()
                .GUID()
                .equals(GUID())).list.isEmpty();
    }

    public enum PlayStyle {
        INT, DEX, STR, NONE;

        public boolean isINT() {
            return this == INT;
        }

        public boolean isDEX() {
            return this == DEX;
        }

        public boolean isSTR() {
            return this == STR;
        }

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

    private static HashMap<String, HashMap<Item, Boolean>> CACHED = new HashMap<>();

    // has to use ugly stuff like this cus datapacks.
    public static boolean isGearOfThisType(BaseGearType slot, Item item) {

        String id = slot.GUID();

        if (!CACHED.containsKey(id)) {
            CACHED.put(id, new HashMap<>());
        }
        if (CACHED.get(id)
            .containsKey(item)) {
            return CACHED.get(id)
                .get(item);
        }

        boolean bool = false;

        try {
            if (item instanceof ArmorItem) {
                if (slot.getVanillaSlotType() != null) {
                    if (slot.getVanillaSlotType()
                        .equals(EquipmentSlot.FEET)) {

                        bool = ((ArmorItem) item).getSlotType()
                            .equals(EquipmentSlot.FEET);

                    } else if (slot.getVanillaSlotType()
                        .equals(EquipmentSlot.CHEST)) {

                        bool = ((ArmorItem) item).getSlotType()
                            .equals(EquipmentSlot.CHEST);

                    } else if (slot.getVanillaSlotType()
                        .equals(EquipmentSlot.HEAD)) {

                        bool = ((ArmorItem) item).getSlotType()
                            .equals(EquipmentSlot.HEAD);

                    } else if (slot.getVanillaSlotType()
                        .equals(EquipmentSlot.LEGS)) {

                        bool = ((ArmorItem) item).getSlotType()
                            .equals(EquipmentSlot.LEGS);
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

            CACHED.get(slot.GUID())
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
                "M M",
                "  "
            };
        }

        if (tags.contains(SlotTag.necklace)) {
            return new String[]{
                "MSM",
                "M M",
                "MMM"
            };
        }
        if (tags.contains(SlotTag.ring)) {
            return new String[]{
                "MMM",
                "M M",
                "MMM"
            };
        }

        return null;
    }

    public final Item getItemForRegistration() {

        TagList tags = getTags();

        if (tags.contains(SlotTag.sword)) {
            return new ItemSword(locNameForLangFile());
        }
        if (tags.contains(SlotTag.axe)) {
            return new ItemAxe(locNameForLangFile());
        }
        if (tags.contains(SlotTag.wand)) {
            return new ItemWand(locNameForLangFile());
        }
        if (tags.contains(SlotTag.bow)) {
            return Items.BOW;
        }
        if (tags.contains(SlotTag.crossbow)) {
            return Items.CROSSBOW;
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
        json.addProperty("weapon_type", weaponType().toString());

        return json;
    }

    @Override
    public BaseGearType fromJson(JsonObject json) {

        SerializableBaseGearType o = new SerializableBaseGearType();

        o.guid = this.getGUIDFromJson(json);
        o.weight = this.getWeightFromJson(json);
        o.lang_name_id = this.getLangNameStringFromJson(json);
        o.levelRange = LevelRange.SERIALIZER.fromJson(json.getAsJsonObject("level_range"));
        o.stat_req = StatRequirement.EMPTY.fromJson(json.getAsJsonObject("stat_req"));
        o.base_stats = JsonUtils.getStats(json, "base_stats");
        o.implicit_stats = JsonUtils.getStats(json, "implicit_stats");
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
