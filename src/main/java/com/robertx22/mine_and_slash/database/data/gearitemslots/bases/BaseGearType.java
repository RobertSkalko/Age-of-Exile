package com.robertx22.mine_and_slash.database.data.gearitemslots.bases;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.exiled_lib.registry.SlashRegistryType;
import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.data_generation.JsonUtils;
import com.robertx22.mine_and_slash.data_generation.base_gear_types.SerializableBaseGearType;
import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.mechanics.NormalWeaponMechanic;
import com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.mechanics.WeaponMechanic;
import com.robertx22.mine_and_slash.database.data.stats.types.offense.AttackSpeed;
import com.robertx22.mine_and_slash.event_hooks.data_gen.ISerializable;
import com.robertx22.mine_and_slash.event_hooks.data_gen.ISerializedRegistryEntry;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import top.theillusivec4.curios.api.CuriosAPI;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class BaseGearType implements IAutoLocName, ISerializedRegistryEntry<BaseGearType>, ISerializable<BaseGearType> {

    public float attacksPerSecond = 1;

    public abstract List<StatModifier> implicitStats();

    public abstract List<StatModifier> baseStats();

    public WeaponTypes weaponType() {
        return WeaponTypes.None;
    }

    public abstract List<SlotTag> getTags();

    public abstract Item getItem();

    public abstract StatRequirement getStatRequirements();

    public final EquipmentSlot getVanillaSlotType() {

        if (getTags().contains(SlotTag.Shield)) {
            return EquipmentSlot.OFFHAND;
        }
        if (getTags().contains(SlotTag.Boots)) {
            return EquipmentSlot.FEET;
        }
        if (getTags().contains(SlotTag.Chest)) {
            return EquipmentSlot.CHEST;
        }
        if (getTags().contains(SlotTag.Pants)) {
            return EquipmentSlot.LEGS;
        }
        if (getTags().contains(SlotTag.Helmet)) {
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
            .contains(SlotTag.MeleeWeapon);
    }

    public boolean isShield() {
        return getTags().contains(SlotTag.Shield);
    }

    public enum SlotTag {
        Sword(SlotFamily.Weapon),
        Axe(SlotFamily.Weapon),
        Bow(SlotFamily.Weapon),
        Wand(SlotFamily.Weapon),
        Crossbow(SlotFamily.Weapon),

        Boots(SlotFamily.Armor),
        Helmet(SlotFamily.Armor),
        Pants(SlotFamily.Armor),
        Chest(SlotFamily.Armor),

        Necklace(SlotFamily.Jewelry),
        Ring(SlotFamily.Jewelry),
        Shield(SlotFamily.OffHand),

        Cloth(SlotFamily.NONE),
        Plate(SlotFamily.NONE),
        Leather(SlotFamily.NONE),

        MageWeapon(SlotFamily.NONE), MeleeWeapon(SlotFamily.NONE), RangedWeapon(SlotFamily.NONE);

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
        Optional<SlotTag> opt = getTags().stream()
            .filter(x -> x.family != SlotFamily.NONE)
            .findFirst();

        if (!opt.isPresent()) {
            System.out.println(GUID() + " doesn't have a slot family tag.");
            return null;
        } else {
            return opt.get().family;
        }
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
                .contains(SlotTag.Sword)) {
                bool = item instanceof SwordItem;
            } else if (slot.getTags()
                .contains(SlotTag.Bow)) {
                bool = item instanceof BowItem;
            } else if (slot.getTags()
                .contains(SlotTag.Axe)) {
                bool = item instanceof AxeItem;
            } else if (slot.getTags()
                .contains(SlotTag.Shield)) {
                bool = item instanceof ShieldItem;
            } else if (slot.getTags()
                .contains(SlotTag.Crossbow)) {
                bool = item instanceof CrossbowItem;
            } else if (slot.getTags()
                .contains(SlotTag.Ring)) {
                bool = CuriosAPI.getCurioTags(item)
                    .stream()
                    .anyMatch(x -> x.toString()
                        .contains("ring"));
            } else if (slot.getTags()
                .contains(SlotTag.Necklace)) {
                bool = CuriosAPI.getCurioTags(item)
                    .stream()
                    .anyMatch(x -> x.toString()
                        .contains("necklace"));
            }

            CACHED.get(slot.GUID())
                .put(item, bool);

            return bool;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

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
        return getTags().contains(SlotTag.MageWeapon);
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = getDefaultJson();

        JsonUtils.addStats(implicitStats(), json, "implicit_stats");
        JsonUtils.addStats(baseStats(), json, "base_stats");

        JsonArray tagArray = JsonUtils.stringListToJsonArray(getTags().stream()
            .map(x -> x.name())
            .collect(Collectors.toList()));

        json.add("tags", tagArray);
        json.add("stat_req", getStatRequirements().toJson());
        json.addProperty("item_id", getItem().getRegistryName()
            .toString());

        return json;
    }

    @Override
    public BaseGearType fromJson(JsonObject json) {

        SerializableBaseGearType o = new SerializableBaseGearType();

        o.identifier = this.getGUIDFromJson(json);
        o.weight = this.getWeightFromJson(json);
        o.lang_name_id = this.getLangNameStringFromJson(json);

        o.stat_req = StatRequirement.EMPTY.fromJson(json.getAsJsonObject("stat_req"));
        o.base_stats = JsonUtils.getStats(json, "base_stats");
        o.implicit_stats = JsonUtils.getStats(json, "implicit_stats");
        o.item_id = json.get("item_id")
            .getAsString();

        o.tags = JsonUtils.jsonArrayToStringList(json.getAsJsonArray("tags"))
            .stream()
            .map(x -> SlotTag.valueOf(x))
            .collect(Collectors.toList());

        return o;
    }
}
