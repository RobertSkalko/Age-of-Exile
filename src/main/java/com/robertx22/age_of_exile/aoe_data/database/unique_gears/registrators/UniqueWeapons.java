package com.robertx22.age_of_exile.aoe_data.database.unique_gears.registrators;

import com.robertx22.age_of_exile.aoe_data.database.DataHelper;
import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BaseGearWeapons;
import com.robertx22.age_of_exile.aoe_data.database.stats.DatapackStatAdder;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.registrators.weapons.UniqueSwords;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.bonus_dmg_to_status_affected.BonusDmgToStatusAffected;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Strength;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ChanceToApplyEffect;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalPenetration;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalSpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.HealPower;
import com.robertx22.age_of_exile.database.data.stats.types.resources.PlusResourceOnKill;
import com.robertx22.age_of_exile.database.data.stats.types.resources.ResourceOnHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaBurn;
import com.robertx22.age_of_exile.database.data.stats.types.special.SpecialStats;
import com.robertx22.age_of_exile.database.data.stats.types.spell_calc.CooldownReduction;
import com.robertx22.age_of_exile.database.data.stats.types.spell_calc.ManaCost;
import com.robertx22.age_of_exile.database.data.stats.types.spell_calc.ProjectileSpeed;
import com.robertx22.age_of_exile.database.registrators.LevelRanges;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackType;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class UniqueWeapons implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        new UniqueSwords().registerAll();

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.JUDGEMENT_AXE,
            "judgement",
            "Judgement",
            "Are you worthy, mortal?",
            BaseGearWeapons.AXE.get(LevelRanges.HIGH))
            .baseStats(Arrays.asList(
                getAttackDamageStat(WeaponTypes.Axe, DataHelper.Number.HALF, Elements.Light),
                getAttackDamageStat(WeaponTypes.Axe, DataHelper.Number.HALF, Elements.Fire)
            ))
            .stats(Arrays.asList(
                new StatModifier(-30, 30, HealPower.getInstance(), ModType.FLAT),
                new StatModifier(-5, 5, ManaBurn.getInstance(), ModType.FLAT)
            ))
            .req(new StatRequirement().setStr(0.5F)
                .setWis(0.75F))
            .build();

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.OBSI_MIGHT_AXE,
            "obsi_might",
            "Obsidian's Might",
            "",
            BaseGearWeapons.AXE.get(LevelRanges.ENDGAME))
            .baseStats(Arrays.asList(
                getAttackDamageStat(WeaponTypes.Axe, DataHelper.Number.FULL, Elements.Fire))
            )
            .stats(Arrays.asList(
                new StatModifier(15, 50, CriticalHit.getInstance(), ModType.LOCAL_INCREASE),
                new StatModifier(1, 3, PlusResourceOnKill.HEALTH, ModType.FLAT),
                new StatModifier(15, 30, BonusDmgToStatusAffected.BURN, ModType.FLAT)
            ))
            .req(new StatRequirement().setVit(0.5F)
                .setStr(0.75F))
            .build();

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.INCA_THUNDER_SWORD,
            "inca_thunder",
            "Incarnation of Thunder",
            "",
            BaseGearWeapons.SWORD.get(LevelRanges.ENDGAME))
            .baseStats(Arrays.asList(
                getAttackDamageStat(WeaponTypes.Sword, DataHelper.Number.FULL, Elements.Light)))
            .stats(Arrays.asList(
                new StatModifier(5, 15, ChanceToApplyEffect.BURN, ModType.FLAT),
                new StatModifier(-20, 20, CriticalDamage.getInstance(), ModType.FLAT)
            ))
            .req(new StatRequirement().setStr(0.5F)
                .setAgi(0.75F))
            .build();

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.WATER_ELE_SWORD,
            "water_ele_sword",
            "Water Elemental",
            "",
            BaseGearWeapons.SWORD.get(LevelRanges.HIGH))
            .baseStats(Arrays.asList(
                getAttackDamageStat(WeaponTypes.Sword, DataHelper.Number.FULL, Elements.Water)))
            .stats(Arrays.asList(
                new StatModifier(15, 30, new AttackDamage(Elements.Water), ModType.LOCAL_INCREASE),
                new StatModifier(5, 10, SpecialStats.CRIT_WATER_DMG_MANA, ModType.FLAT),
                new StatModifier(15, 30, BonusDmgToStatusAffected.FROST, ModType.FLAT)
            ))
            .req(new StatRequirement().setInt(0.5F)
                .setAgi(0.75F))
            .build();

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.EZE_OF_ZEGRATH_WAND,
            "eye_of_zegrath",
            "Eye of Zegrath",
            "A cultist once so powerful, even his remnant eye inflicts ills on others.",
            BaseGearWeapons.WAND.get(LevelRanges.STARTER))

            .stats(Arrays.asList(
                new StatModifier(10, 30, new AttackDamage(Elements.Physical), ModType.LOCAL_INCREASE),
                new StatModifier(5, 10, ChanceToApplyEffect.BURN, ModType.FLAT),
                new StatModifier(5, 10, ChanceToApplyEffect.FROSTBURN, ModType.FLAT),
                new StatModifier(5, 10, ChanceToApplyEffect.POISON, ModType.FLAT)
            ))
            .req(new StatRequirement().setAgi(0.5F)
                .setInt(0.75F))
            .build();

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.DIVINE_MIGHT_WAND,
            "divine_might",
            "Divine Might",
            "God might prefer peaceful means, but sometimes peace is not an option.",
            BaseGearWeapons.SCEPTER.get(LevelRanges.ENDGAME))

            .baseStats(Arrays.asList(
                getAttackDamageStat(WeaponTypes.Wand, DataHelper.Number.FULL, Elements.Light)))
            .stats(Arrays.asList(
                new StatModifier(5, 25, HealPower.getInstance(), ModType.FLAT),
                new StatModifier(-100, -100, CriticalHit.getInstance(), ModType.FLAT),
                new StatModifier(10, 25, DatapackStatAdder.HEAL_TO_SPELL_DMG, ModType.FLAT)
            ))
            .req(new StatRequirement().setInt(0.5F)
                .setWis(0.75F))
            .build();

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.WILL_OF_FLORA_WAND,
            "will_of_flora",
            "Will of Flora",
            "Everything must be exactly as required.",
            BaseGearWeapons.SCEPTER.get(LevelRanges.HIGH))
            .baseStats(Arrays.asList(
                getAttackDamageStat(WeaponTypes.Wand, DataHelper.Number.FULL, Elements.Nature)))
            .stats(Arrays.asList(
                new StatModifier(10, 30, HealPower.getInstance(), ModType.FLAT),
                new StatModifier(2, 10, new ElementalPenetration(Elements.Nature), ModType.FLAT),
                new StatModifier(-4, -10, ManaCost.getInstance(), ModType.FLAT)
            ))
            .req(new StatRequirement().setInt(0.5F)
                .setWis(0.75F))
            .build();

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.ROT_PHYS,
            "rot_phys",
            "Smell of Rot",
            "Strange things you find yourself liking. Like a stick, with a piece of rotten flesh on it.",
            BaseGearWeapons.WAND.get(LevelRanges.LOW))
            .stats(Arrays.asList(
                new StatModifier(50, 100, new AttackDamage(Elements.Physical), ModType.LOCAL_INCREASE)
            ))
            .req(new StatRequirement().setInt(0.5F)
                .setStr(0.75F))
            .build();

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.CRYSTAL_WAND,
            "crystal_wand",
            "Origin of Arcane",
            "There is always the first.",
            BaseGearWeapons.WAND.get(LevelRanges.HIGH))
            .stats(Arrays.asList(
                new StatModifier(20, 45, SpellDamage.getInstance(), ModType.FLAT),
                new StatModifier(20, 40, ManaCost.getInstance(), ModType.FLAT),
                new StatModifier(10, 30, CooldownReduction.getInstance(), ModType.FLAT)
            ))
            .req(new StatRequirement().setWis(0.5F)
                .setInt(0.75F))
            .build();

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.FRIGID_STAFF,
            "frigid_staff",
            "Gem of The North",
            "Found in a deep pit of ice. Many wands were shattered in attempts to imbue it.",
            BaseGearWeapons.WAND.get(LevelRanges.ENDGAME))
            .baseStats(Arrays.asList(
                getAttackDamageStat(WeaponTypes.Wand, DataHelper.Number.FULL, Elements.Water)))
            .stats(Arrays.asList(
                new StatModifier(10, 20, ProjectileSpeed.getInstance(), ModType.FLAT),
                new StatModifier(5, 10, ChanceToApplyEffect.FROSTBURN, ModType.FLAT),
                new StatModifier(10, 20, CriticalHit.getInstance(), ModType.FLAT),
                new StatModifier(1, 3, new ResourceOnHit(new ResourceOnHit.Info(ResourceType.MANA, AttackType.ATTACK)), ModType.FLAT)
            ))
            .req(new StatRequirement().setWis(0.5F)
                .setInt(0.75F))
            .build();

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.WORLDBEARER_STAFF,
            "worldbearer",
            "Worldbearer",
            "The ability to carry any burden is a heavy one.",
            BaseGearWeapons.WAND.get(LevelRanges.ENDGAME))
            .baseStats(Arrays.asList(
                getAttackDamageStat(WeaponTypes.Wand, DataHelper.Number.FULL, Elements.Nature)))
            .stats(Arrays.asList(
                new StatModifier(15, 30, new ElementalSpellDamage(Elements.Nature), ModType.FLAT),
                new StatModifier(-100, -100, CriticalHit.getInstance(), ModType.FLAT),
                new StatModifier(15, 30, Strength.INSTANCE, ModType.LOCAL_INCREASE)
            ))
            .req(new StatRequirement().setVit(0.5F)
                .setInt(0.75F))
            .build();

    }
}
