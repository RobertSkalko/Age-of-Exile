package com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders;

import com.robertx22.age_of_exile.aoe_data.base.DataGenKey;
import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.BaseGearBuilder;
import com.robertx22.age_of_exile.aoe_data.database.gear_slots.GearSlots;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.gear_types.bases.TagList;
import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.data.stats.types.defense.ArmorPenetration;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.HealPower;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.HashMap;

public class BaseGearWeapons implements ISlashRegistryInit {

    public static HashMap<LevelRange, DataGenKey<BaseGearType>> SWORD;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> AXE;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> WAND;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> SCEPTER;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> BOW;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> CROSSBOW;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> SCYTHE;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> MACE;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> HAMMER;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> SPEAR;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> GLOVE;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> DAGGER;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> STAFF;

    @Override
    public void registerAll() {

        BOW = BaseGearBuilder.weapon(GearSlots.BOW, WeaponTypes.Bow, ModRegistry.GEAR_ITEMS.BOWS)
            .req(new StatRequirement().setDex(0.5f))
            .tags(new TagList(SlotTag.ranger_casting_weapon, SlotTag.bow, SlotTag.weapon_family, SlotTag.ranged_weapon, SlotTag.dexterity))
            .addWarriorLevelRanges()
            .build();

        CROSSBOW = BaseGearBuilder.weapon(GearSlots.CROSBOW, WeaponTypes.CrossBow, ModRegistry.GEAR_ITEMS.CROSSBOWS)
            .req(new StatRequirement().setDex(0.4f)
                .setStr(0.2F))
            .tags(new TagList(SlotTag.crossbow, SlotTag.weapon_family, SlotTag.ranged_weapon, SlotTag.dexterity))
            .addWarriorLevelRanges()
            .build();

        SCYTHE = BaseGearBuilder.weapon(GearSlots.SCYTHE, WeaponTypes.Scythe, ModRegistry.GEAR_ITEMS.SCYTHES)
            .req(new StatRequirement().setInt(0.5f)
                .setDex(0.2F))
            .tags(new TagList(SlotTag.mage_weapon, SlotTag.scythe, SlotTag.weapon_family, SlotTag.melee_weapon, SlotTag.intelligence, SlotTag.dexterity))
            .addWarriorLevelRanges()
            .build();

        AXE = BaseGearBuilder.weapon(GearSlots.AXE, WeaponTypes.Axe, ModRegistry.GEAR_ITEMS.AXES)
            .req(new StatRequirement().setStr(0.5f))
            .tags(new TagList(SlotTag.warrior_casting_weapon, SlotTag.axe, SlotTag.weapon_family, SlotTag.melee_weapon, SlotTag.strength))
            .addWarriorLevelRanges()
            .build();

        SWORD = BaseGearBuilder.weapon(GearSlots.SWORD, WeaponTypes.Sword, ModRegistry.GEAR_ITEMS.SWORDS)
            .req(new StatRequirement().setStr(0.3F)
                .setDex(0.1F))
            .tags(new TagList(SlotTag.warrior_casting_weapon, SlotTag.sword, SlotTag.melee_weapon, SlotTag.weapon_family, SlotTag.strength, SlotTag.dexterity))
            .addWarriorLevelRanges()
            .build();

        WAND = BaseGearBuilder.weapon(GearSlots.WAND, WeaponTypes.Wand, ModRegistry.GEAR_ITEMS.WANDS)
            .req(new StatRequirement().setInt(0.5F))
            .tags(new TagList(SlotTag.mage_weapon, SlotTag.wand, SlotTag.weapon_family, SlotTag.melee_weapon, SlotTag.intelligence))
            .implicitStat(new StatModifier(3, 10, SpellDamage.getInstance(), ModType.FLAT))
            .addWarriorLevelRanges()
            .build();

        SCEPTER = BaseGearBuilder.weapon(GearSlots.SCEPTER, WeaponTypes.Scepter, ModRegistry.GEAR_ITEMS.SCEPTERS)
            .req(new StatRequirement().setInt(0.2F)
                .setWis(0.5F)
                .setStr(0.1F))
            .essenceItem(ModRegistry.GEAR_MATERIALS.LIFE)
            .tags(new TagList(SlotTag.mage_weapon, SlotTag.scepter, SlotTag.weapon_family, SlotTag.melee_weapon, SlotTag.intelligence))
            .implicitStat(new StatModifier(3, 10, HealPower.getInstance(), ModType.FLAT))
            .addWarriorLevelRanges()
            .build();

        MACE = BaseGearBuilder.weapon(GearSlots.MACE, WeaponTypes.Mace, ModRegistry.GEAR_ITEMS.MACES)
            .req(new StatRequirement().setInt(0.25F)
                .setVit(0.5F))
            .tags(new TagList(SlotTag.mage_weapon, SlotTag.mace, SlotTag.weapon_family, SlotTag.melee_weapon, SlotTag.strength, SlotTag.intelligence))
            .implicitStat(new StatModifier(3, 8, HealPower.getInstance(), ModType.FLAT))
            .addWarriorLevelRanges()
            .build();

        HAMMER = BaseGearBuilder.weapon(GearSlots.HAMMER, WeaponTypes.Hammer, ModRegistry.GEAR_ITEMS.HAMMERS)
            .req(new StatRequirement().setStr(0.5F)
                .setVit(0.5F))
            .tags(new TagList(SlotTag.warrior_casting_weapon, SlotTag.hammer, SlotTag.weapon_family, SlotTag.melee_weapon, SlotTag.strength))
            .implicitStat(new StatModifier(5, 15, ArmorPenetration.getInstance(), ModType.FLAT))
            .addWarriorLevelRanges()
            .build();

        SPEAR = BaseGearBuilder.weapon(GearSlots.SPEAR, WeaponTypes.Spear, ModRegistry.GEAR_ITEMS.SPEARS)
            .req(new StatRequirement().setStr(0.75F))
            .tags(new TagList(SlotTag.warrior_casting_weapon, SlotTag.spear, SlotTag.weapon_family, SlotTag.melee_weapon, SlotTag.strength))
            .addWarriorLevelRanges()
            .build();

        GLOVE = BaseGearBuilder.weapon(GearSlots.GLOVE, WeaponTypes.Glove, ModRegistry.GEAR_ITEMS.GLOVES)
            .req(new StatRequirement().setDex(0.6F)
                .setAgi(0.3F)
                .setStr(0.2F))
            .tags(new TagList(SlotTag.warrior_casting_weapon, SlotTag.glove, SlotTag.weapon_family, SlotTag.melee_weapon, SlotTag.dexterity, SlotTag.strength))
            .addWarriorLevelRanges()
            .build();

        DAGGER = BaseGearBuilder.weapon(GearSlots.DAGGER, WeaponTypes.Dagger, ModRegistry.GEAR_ITEMS.DAGGERS)
            .req(new StatRequirement().setDex(0.2F)
                .setAgi(0.5F))
            .tags(new TagList(SlotTag.warrior_casting_weapon, SlotTag.dagger, SlotTag.weapon_family, SlotTag.melee_weapon, SlotTag.dexterity))
            .addWarriorLevelRanges()
            .build();

        STAFF = BaseGearBuilder.weapon(GearSlots.STAFF, WeaponTypes.Staff, ModRegistry.GEAR_ITEMS.STAFFS)
            .req(new StatRequirement().setInt(0.5F)
                .setStr(0.2F))
            .tags(new TagList(SlotTag.mage_weapon, SlotTag.staff, SlotTag.weapon_family, SlotTag.melee_weapon, SlotTag.intelligence))
            .addWarriorLevelRanges()
            .build();

    }
}
