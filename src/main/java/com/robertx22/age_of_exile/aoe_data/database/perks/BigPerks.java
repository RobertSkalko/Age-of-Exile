package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.aoe_data.database.stats.DatapackStatAdder;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Dexterity;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Intelligence;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Strength;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.defense.MaxElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.generated.*;
import com.robertx22.age_of_exile.database.data.stats.types.misc.DamageTakenToMana;
import com.robertx22.age_of_exile.database.data.stats.types.offense.*;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.SpellCriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.SpellCriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.HealPower;
import com.robertx22.age_of_exile.database.data.stats.types.resources.PlusResourceOnKill;
import com.robertx22.age_of_exile.database.data.stats.types.resources.RegeneratePercentStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.LifeOnHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Lifesteal;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaOnHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.data.stats.types.spell_calc.FasterCastRate;
import com.robertx22.age_of_exile.database.data.stats.types.spell_calc.ProjectileSpeedStat;
import com.robertx22.age_of_exile.database.data.stats.types.spell_calc.ReducedManaCost;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class BigPerks implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        PerkBuilder.bigStat("big_int", "Wisdom", new OptScaleExactStat(30, Intelligence.INSTANCE, ModType.FLAT));
        PerkBuilder.bigStat("big_dex", "Skill", new OptScaleExactStat(30, Dexterity.INSTANCE, ModType.FLAT));
        PerkBuilder.bigStat("big_str", "Power", new OptScaleExactStat(30, Strength.INSTANCE, ModType.FLAT));

        PerkBuilder.bigStat("big_divine_light", "Divine Light",
            new OptScaleExactStat(5, SpellDamage.getInstance(), ModType.FLAT),
            new OptScaleExactStat(10, SpellCriticalHit.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, Strength.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(10, Intelligence.INSTANCE, ModType.FLAT)
        );

        PerkBuilder.bigStat("big_spell_crit", "Destruction",
            new OptScaleExactStat(5, SpellCriticalHit.getInstance(), ModType.FLAT),
            new OptScaleExactStat(15, SpellCriticalDamage.getInstance(), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_wand", "Wand Master",
            new OptScaleExactStat(10, new SpecificWeaponDamage(WeaponTypes.Wand), ModType.FLAT),
            new OptScaleExactStat(5, CriticalHit.getInstance(), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_sadist", "Sadist",
            new OptScaleExactStat(2, DamageTakenToMana.getInstance(), ModType.FLAT),
            new OptScaleExactStat(20, Health.getInstance(), ModType.FLAT),
            new OptScaleExactStat(5, Mana.getInstance(), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_balistic", "Ballistics Expert",
            new OptScaleExactStat(5, ProjectileDamage.getInstance(), ModType.FLAT),
            new OptScaleExactStat(10, ProjectileSpeedStat.getInstance(), ModType.FLAT),
            new OptScaleExactStat(20, Dexterity.INSTANCE, ModType.FLAT)
        );

        PerkBuilder.bigStat("big_hunter", "Soul of the Hunter",
            new OptScaleExactStat(5, AttackStyleDamage.RANGED, ModType.FLAT),
            new OptScaleExactStat(200, DodgeRating.getInstance(), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_frog", "Aspect of the Frog",
            new OptScaleExactStat(5, AttackStyleDamage.MELEE, ModType.FLAT),
            new OptScaleExactStat(10, DodgeRating.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, Health.getInstance(), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_rat", "Aspect of the Rat",
            new OptScaleExactStat(5, new AttackDamage(Elements.Physical), ModType.FLAT),
            new OptScaleExactStat(10, CriticalHit.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, Dexterity.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(10, Intelligence.INSTANCE, ModType.FLAT)
        );

        PerkBuilder.bigStat("big_eagle", "Aspect of the Eagle",
            new OptScaleExactStat(5, AttackStyleDamage.RANGED, ModType.FLAT),
            new OptScaleExactStat(10, Accuracy.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, Health.getInstance(), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_effect_of_ele", "Elemental Destruction",
            new OptScaleExactStat(5, new ElementalSpellDamage(Elements.Elemental), ModType.FLAT),
            new OptScaleExactStat(10, ChanceToApplyEffect.POISON, ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, ChanceToApplyEffect.STATIC, ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, ChanceToApplyEffect.BURN, ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, ChanceToApplyEffect.CHILL, ModType.LOCAL_INCREASE)
        );

        PerkBuilder.bigStat("big_aspect_of_thunder", "Aspect of Thunder",
            new OptScaleExactStat(5, new ElementalSpellDamage(Elements.Thunder), ModType.FLAT),
            new OptScaleExactStat(5, CriticalHit.getInstance(), ModType.FLAT),
            new OptScaleExactStat(5, PlusResourceOnKill.MAGIC_SHIELD, ModType.FLAT)
        );

        PerkBuilder.bigStat("big_flow_of_mana", "Flow of Mana",
            new OptScaleExactStat(5, new ElementalSpellDamage(Elements.Water), ModType.FLAT),
            new OptScaleExactStat(10, ManaRegen.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(20, MagicShield.getInstance(), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_aspect_of_nature", "Aspect of Nature",
            new OptScaleExactStat(5, new ElementalSpellDamage(Elements.Nature), ModType.FLAT),
            new OptScaleExactStat(10, HealthRegen.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, HealPower.getInstance(), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_flame_of_life", "Flame of Life",
            new OptScaleExactStat(5, new ElementalSpellDamage(Elements.Fire), ModType.FLAT),
            new OptScaleExactStat(1, RegeneratePercentStat.HEALTH, ModType.FLAT),
            new OptScaleExactStat(3, FasterCastRate.getInstance(), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_will_of_mana", "Will of Mana",
            new OptScaleExactStat(15, Mana.getInstance(), ModType.FLAT),
            new OptScaleExactStat(2, ManaRegen.getInstance(), ModType.FLAT),
            new OptScaleExactStat(3, DatapackStatAdder.MANA_TO_MAGIC_SHIELD, ModType.FLAT)
        );

        PerkBuilder.bigStat("big_overflow_mana", "Overflowing Mana",
            new OptScaleExactStat(15, Mana.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, ManaRegen.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(2, ManaOnHit.getInstance(), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_ele_protection", "Elemental Protection",
            new OptScaleExactStat(1, new MaxElementalResist(Elements.Elemental), ModType.FLAT),
            new OptScaleExactStat(5, new ElementalResist(Elements.Elemental), ModType.FLAT),
            new OptScaleExactStat(5, MagicShield.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.bigStat("big_force_of_will", "Force of Will",
            new OptScaleExactStat(20, MagicShield.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(1, new MaxElementalResist(Elements.Elemental), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_blood_siphon", "Blood Stealing",
            new OptScaleExactStat(5, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(20, Strength.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(2, LifeOnHit.getInstance(), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_precision", "Precision",
            new OptScaleExactStat(10, Accuracy.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(20, Dexterity.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(3, AttackSpeed.getInstance(), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_iron_skin", "Iron Skin",
            new OptScaleExactStat(10, Armor.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, new ElementalResist(Elements.Elemental), ModType.FLAT),
            new OptScaleExactStat(10, Health.getInstance(), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_vampire", "Vampire",
            new OptScaleExactStat(8, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(2, Lifesteal.getInstance(), ModType.FLAT)
        );
        PerkBuilder.bigStat("big_warrior_heart", "Warrior's Heart",
            new OptScaleExactStat(10, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, Health.getInstance(), ModType.FLAT),
            new OptScaleExactStat(10, Strength.INSTANCE, ModType.FLAT)
        );

        PerkBuilder.bigStat("big_fighter", "Natural Fighter",
            new OptScaleExactStat(3, new AttackDamage(Elements.Physical), ModType.FLAT),
            new OptScaleExactStat(3, AttackSpeed.getInstance(), ModType.FLAT),
            new OptScaleExactStat(20, Accuracy.getInstance(), ModType.FLAT),
            new OptScaleExactStat(20, Strength.INSTANCE, ModType.FLAT)
        );

        PerkBuilder.bigStat("big_mana_and_health", "Equilibrium",
            new OptScaleExactStat(5, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, Mana.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.bigStat("big_lord_of_arcane", "Lord of Arcane",
            new OptScaleExactStat(20, Intelligence.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(5, SpellDamage.getInstance(), ModType.FLAT),
            new OptScaleExactStat(10, Mana.getInstance(), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_wisdom_of_the_elders", "Wisdom of the Elders",
            new OptScaleExactStat(20, Intelligence.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(20, MagicShield.getInstance(), ModType.FLAT),
            new OptScaleExactStat(20, Mana.getInstance(), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_armor_and_ms", "Magical Armor",
            new OptScaleExactStat(10, Health.getInstance(), ModType.FLAT),
            new OptScaleExactStat(10, Armor.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, MagicShield.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.bigStat("big_tenacious_combatant", "Tenacious Combatant",
            new OptScaleExactStat(10, Armor.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(1, RegeneratePercentStat.HEALTH, ModType.FLAT)
        );

        PerkBuilder.bigStat("big_health_mana_cost", "Righteous Indignation",
            new OptScaleExactStat(5, ReducedManaCost.getInstance(), ModType.FLAT),
            new OptScaleExactStat(10, Health.getInstance(), ModType.FLAT),
            new OptScaleExactStat(5, MagicShield.getInstance(), ModType.LOCAL_INCREASE)
        );

    }
}
