package com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders;

import com.robertx22.age_of_exile.aoe_data.base.DataGenKey;
import com.robertx22.age_of_exile.aoe_data.database.DataHelper;
import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.BaseGearBuilder;
import com.robertx22.age_of_exile.aoe_data.database.gear_slots.GearSlots;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.gear_types.bases.TagList;
import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.HealPower;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackPlayStyle;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.HashMap;

public class BaseGearWeapons implements ISlashRegistryInit {

    public static HashMap<LevelRange, DataGenKey<BaseGearType>> SWORD;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> AXE;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> WAND;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> SCEPTER;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> BOW;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> CROSSBOW;

    @Override
    public void registerAll() {

        BOW = BaseGearBuilder.of(GearSlots.BOW, "bow", "Bow", ModRegistry.GEAR_ITEMS.BOWS)
            .weaponType(WeaponTypes.Bow)
            .req(new StatRequirement().setDex(0.5f))
            .tags(new TagList(SlotTag.ranger_casting_weapon, SlotTag.bow, SlotTag.weapon_family, SlotTag.ranged_weapon, SlotTag.dexterity))
            .baseStat(getAttackDamageStat(WeaponTypes.Bow, DataHelper.Number.FULL, Elements.Physical))
            .attackStyle(AttackPlayStyle.RANGED)
            .addHunterLevelRanges()
            .build();

        CROSSBOW = BaseGearBuilder.of(GearSlots.CROSBOW, "crossbow", "Crossbow", ModRegistry.GEAR_ITEMS.CROSSBOWS)
            .weaponType(WeaponTypes.CrossBow)
            .req(new StatRequirement().setDex(0.5f))
            .tags(new TagList(SlotTag.crossbow, SlotTag.weapon_family, SlotTag.ranged_weapon, SlotTag.dexterity))
            .baseStat(getAttackDamageStat(WeaponTypes.CrossBow, DataHelper.Number.FULL, Elements.Physical))
            .attackStyle(AttackPlayStyle.RANGED)
            .addHunterLevelRanges()
            .build();

        AXE = BaseGearBuilder.of(GearSlots.AXE, "axe", "Axe", ModRegistry.GEAR_ITEMS.AXES)
            .attackSpeed(BaseGearType.Constants.AXE_ATK_SPEED)
            .weaponType(WeaponTypes.Axe)
            .req(new StatRequirement().setStr(0.5f))
            .tags(new TagList(SlotTag.warrior_casting_weapon, SlotTag.axe, SlotTag.weapon_family, SlotTag.melee_weapon, SlotTag.strength))
            .baseStat(getAttackDamageStat(WeaponTypes.Axe, DataHelper.Number.FULL, Elements.Physical))
            .attackStyle(AttackPlayStyle.MELEE)
            .addWarriorLevelRanges()

            .build();

        SWORD = BaseGearBuilder.of(GearSlots.SWORD, "sword", "Sword", ModRegistry.GEAR_ITEMS.SWORDS)
            .attackSpeed(BaseGearType.Constants.SWORD_ATK_SPEED)
            .weaponType(WeaponTypes.Sword)
            .req(new StatRequirement().setStr(0.3F)
                .setDex(0.1F))
            .tags(new TagList(SlotTag.warrior_casting_weapon, SlotTag.sword, SlotTag.melee_weapon, SlotTag.weapon_family, SlotTag.strength, SlotTag.dexterity))

            .baseStat(getAttackDamageStat(WeaponTypes.Sword, DataHelper.Number.FULL, Elements.Physical))

            .attackStyle(AttackPlayStyle.MELEE)
            .addWarriorLevelRanges()
            .build();

        WAND = BaseGearBuilder.of(GearSlots.WAND, "wand", "Wand", ModRegistry.GEAR_ITEMS.WANDS)
            .attackSpeed(BaseGearType.Constants.WAND_ATK_SPEED)
            .weaponType(WeaponTypes.Wand)
            .req(new StatRequirement().setInt(0.5F))
            .tags(new TagList(SlotTag.mage_weapon, SlotTag.wand, SlotTag.weapon_family, SlotTag.melee_weapon, SlotTag.intelligence))
            .baseStat(getAttackDamageStat(WeaponTypes.Wand, DataHelper.Number.FULL, Elements.Physical))
            .implicitStat(new StatModifier(3, 10, SpellDamage.getInstance(), ModType.FLAT))
            .attackStyle(AttackPlayStyle.MAGIC)
            .addMageWeaponLevelRanges()
            .build();

        SCEPTER = BaseGearBuilder.of(GearSlots.WAND, "scepter", "Scepter", ModRegistry.GEAR_ITEMS.SCEPTERS)
            .essenceItem(ModRegistry.GEAR_MATERIALS.LIFE)
            .attackSpeed(BaseGearType.Constants.WAND_ATK_SPEED)
            .weight(2000)
            .weaponType(WeaponTypes.Wand)
            .req(new StatRequirement().setInt(0.5F))
            .tags(new TagList(SlotTag.mage_weapon, SlotTag.wand, SlotTag.weapon_family, SlotTag.melee_weapon, SlotTag.intelligence))
            .baseStat(getAttackDamageStat(WeaponTypes.Wand, DataHelper.Number.FULL, Elements.Physical))
            .implicitStat(new StatModifier(3, 10, HealPower.getInstance(), ModType.FLAT))
            .attackStyle(AttackPlayStyle.MELEE)
            .addMageWeaponLevelRanges()
            .build();

    }
}
