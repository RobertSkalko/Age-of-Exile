package com.robertx22.age_of_exile.aoe_data.base_gear_types;

import com.robertx22.age_of_exile.aoe_data.base.DataGenKey;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.gear_types.bases.TagList;
import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.HealPower;
import com.robertx22.age_of_exile.database.registrators.GearSlots;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.HashMap;

public class BaseGearWeapons implements ISlashRegistryInit {

    public static HashMap<LevelRange, DataGenKey<BaseGearType>> SWORD;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> AXE;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> WAND;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> SCEPTER;

    @Override
    public void registerAll() {

        AXE = BaseGearBuilder.of(GearSlots.AXE, "axe", "Axe", ModRegistry.GEAR_ITEMS.AXES)
            .attackSpeed(BaseGearType.Constants.AXE_ATK_SPEED)
            .weaponType(WeaponTypes.Axe)
            .req(new StatRequirement().strength(0.5f))
            .tags(new TagList(SlotTag.warrior_casting_weapon, SlotTag.axe, SlotTag.weapon_family, SlotTag.melee_weapon, SlotTag.strength))
            .baseStat(new StatModifier(2, 3, 4, 9, new WeaponDamage(Elements.Physical), ModType.FLAT))
            .baseStat(new StatModifier(4, 15, CriticalHit.getInstance(), ModType.FLAT))
            .addWarriorLevelRanges()
            .build();

        SWORD = BaseGearBuilder.of(GearSlots.SWORD, "sword", "Sword", ModRegistry.GEAR_ITEMS.SWORDS)
            .attackSpeed(BaseGearType.Constants.SWORD_ATK_SPEED)
            .weaponType(WeaponTypes.Sword)
            .req(new StatRequirement().strength(0.3F)
                .dexterity(0.1F))
            .tags(new TagList(SlotTag.warrior_casting_weapon, SlotTag.sword, SlotTag.melee_weapon, SlotTag.weapon_family, SlotTag.strength, SlotTag.dexterity))
            .baseStat(new StatModifier(2, 3, 2, 4, new WeaponDamage(Elements.Physical), ModType.FLAT))
            .baseStat(new StatModifier(2, 6, CriticalHit.getInstance(), ModType.FLAT))
            .addWarriorLevelRanges()
            .build();

        WAND = BaseGearBuilder.of(GearSlots.WAND, "wand", "Wand", ModRegistry.GEAR_ITEMS.WANDS)
            .attackSpeed(BaseGearType.Constants.WAND_ATK_SPEED)
            .weaponType(WeaponTypes.Wand)
            .req(new StatRequirement().intelligence(0.5F))
            .tags(new TagList(SlotTag.mage_weapon, SlotTag.wand, SlotTag.weapon_family, SlotTag.melee_weapon, SlotTag.intelligence))
            .baseStat(new StatModifier(2, 3, 3, 6, new WeaponDamage(Elements.Physical), ModType.FLAT))
            .baseStat(new StatModifier(3, 10, CriticalHit.getInstance(), ModType.FLAT))
            .implicitStat(new StatModifier(3, 10, SpellDamage.getInstance(), ModType.FLAT))
            .addMageWeaponLevelRanges()
            .build();

        SCEPTER = BaseGearBuilder.of(GearSlots.WAND, "scepter", "Scepter", ModRegistry.GEAR_ITEMS.SCEPTERS)
            .attackSpeed(BaseGearType.Constants.WAND_ATK_SPEED)
            .weight(2000)
            .weaponType(WeaponTypes.Wand)
            .req(new StatRequirement().intelligence(0.5F))
            .tags(new TagList(SlotTag.mage_weapon, SlotTag.wand, SlotTag.weapon_family, SlotTag.melee_weapon, SlotTag.intelligence))
            .baseStat(new StatModifier(2, 4, 4, 6, new WeaponDamage(Elements.Physical), ModType.FLAT))
            .baseStat(new StatModifier(2, 5, CriticalHit.getInstance(), ModType.FLAT))
            .implicitStat(new StatModifier(3, 10, HealPower.getInstance(), ModType.FLAT))
            .addMageWeaponLevelRanges()
            .build();

    }
}
