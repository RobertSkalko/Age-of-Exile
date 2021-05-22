package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.ResourceAndAttack;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectTags;
import com.robertx22.age_of_exile.database.data.skill_gem.SpellTag;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.*;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.defense.MaxElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalPenetration;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.misc.DamageTakenToMana;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.GlobalCriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.RegeneratePercentStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.data.stats.types.special.SpecialStats;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.enumclasses.*;

public class BigPerks implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        PerkBuilder.bigStat("big_int", "Intelligent", new OptScaleExactStat(5, Intelligence.INSTANCE, ModType.FLAT));
        PerkBuilder.bigStat("big_dex", "Dexterous", new OptScaleExactStat(5, Dexterity.INSTANCE, ModType.FLAT));
        PerkBuilder.bigStat("big_str", "Strong", new OptScaleExactStat(5, Strength.INSTANCE, ModType.FLAT));
        PerkBuilder.bigStat("big_wis", "Wise", new OptScaleExactStat(5, Wisdom.INSTANCE, ModType.FLAT));
        PerkBuilder.bigStat("big_agi", "Agile", new OptScaleExactStat(5, Agility.INSTANCE, ModType.FLAT));
        PerkBuilder.bigStat("big_vit", "Virile", new OptScaleExactStat(5, Vitality.INSTANCE, ModType.FLAT));

        PerkBuilder.bigStat("huge_int", "Superior Wisdom", new OptScaleExactStat(10, Intelligence.INSTANCE, ModType.FLAT));
        PerkBuilder.bigStat("huge_dex", "Superior Skill", new OptScaleExactStat(10, Dexterity.INSTANCE, ModType.FLAT));
        PerkBuilder.bigStat("huge_str", "Superior Power", new OptScaleExactStat(10, Strength.INSTANCE, ModType.FLAT));
        PerkBuilder.bigStat("huge_wis", "Superior Wisdom", new OptScaleExactStat(10, Wisdom.INSTANCE, ModType.FLAT));
        PerkBuilder.bigStat("huge_agi", "Superior Agility", new OptScaleExactStat(10, Agility.INSTANCE, ModType.FLAT));
        PerkBuilder.bigStat("huge_vit", "Superior Vitality", new OptScaleExactStat(10, Vitality.INSTANCE, ModType.FLAT));

        PerkBuilder.bigStat("big_hp_res", "Hard Life",
            new OptScaleExactStat(10, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(1, new MaxElementalResist(Elements.Elemental), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_health", "Healthy",
            new OptScaleExactStat(15, Health.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.bigStat("big_all_light_damage", "Sun",
            new OptScaleExactStat(10, Stats.ELEMENTAL_DAMAGE.get(Elements.Light))
        );

        PerkBuilder.bigStat("big_eviscerator", "Eviscerator",
            new OptScaleExactStat(3, Stats.STYLE_DAMAGE.get(PlayStyle.melee), ModType.FLAT),
            new OptScaleExactStat(10, Stats.CRIT_CHANCE.get(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, Stats.CRIT_DAMAGE.get(), ModType.FLAT)
        );

        PerkBuilder.bigStat("divine_light", "Divine Light",
            new OptScaleExactStat(5, SpellDamage.getInstance(), ModType.FLAT),
            new OptScaleExactStat(3, Stats.SPELL_CRIT_CHANCE.get(), ModType.FLAT),
            new OptScaleExactStat(5, Strength.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(5, Intelligence.INSTANCE, ModType.FLAT)
        );

        PerkBuilder.bigStat("big_spell_crit", "Destruction",
            new OptScaleExactStat(3, Stats.SPELL_CRIT_CHANCE.get(), ModType.FLAT),
            new OptScaleExactStat(10, Stats.SPELL_CRIT_DAMAGE.get(), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_wand", "Wand Master",
            new OptScaleExactStat(10, Stats.WEAPON_DAMAGE.get(WeaponTypes.wand), ModType.FLAT),
            new OptScaleExactStat(5, Stats.CRIT_CHANCE.get(), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_sadist", "Sadist",
            new OptScaleExactStat(2, DamageTakenToMana.getInstance(), ModType.FLAT),
            new OptScaleExactStat(20, Health.getInstance(), ModType.FLAT),
            new OptScaleExactStat(5, Mana.getInstance(), ModType.FLAT)
        );

        PerkBuilder.bigStat("ballistic", "Ballistics Expert",
            new OptScaleExactStat(5, Stats.PROJECTILE_DAMAGE.get(), ModType.FLAT),
            new OptScaleExactStat(10, Stats.PROJECTILE_SPEED.get(), ModType.FLAT),
            new OptScaleExactStat(10, Dexterity.INSTANCE, ModType.FLAT)
        );

        PerkBuilder.bigStat("big_hunter", "Soul of the Hunter",
            new OptScaleExactStat(5, Stats.STYLE_DAMAGE.get(PlayStyle.ranged), ModType.FLAT),
            new OptScaleExactStat(200, DodgeRating.getInstance(), ModType.FLAT)
        );

        PerkBuilder.bigStat("fury_bolt", "Furious Bolts",
            new OptScaleExactStat(5, Stats.PROJECTILE_DAMAGE.get(), ModType.FLAT),
            new OptScaleExactStat(5, Stats.PROJECTILE_SPEED.get(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, Strength.INSTANCE, ModType.FLAT)
        );
        PerkBuilder.bigStat("big_fury", "Fury",
            new OptScaleExactStat(5, Stats.ATTACK_SPEED.get(), ModType.FLAT),
            new OptScaleExactStat(5, Stats.STYLE_DAMAGE.get(PlayStyle.melee)),
            new OptScaleExactStat(50, Stats.ACCURACY.get()),
            new OptScaleExactStat(10, Strength.INSTANCE, ModType.FLAT)
        );

        PerkBuilder.bigStat("big_blood_thirst", "Blood thirst",
            new OptScaleExactStat(10, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(3, Stats.LIFESTEAL.get(), ModType.FLAT),
            new OptScaleExactStat(2, new AttackDamage(Elements.Physical), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.bigStat("big_bravery", "Soul of Bravery",
            new OptScaleExactStat(20, Health.getInstance(), ModType.FLAT),
            new OptScaleExactStat(10, Armor.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, DodgeRating.getInstance(), ModType.LOCAL_INCREASE)
        );
        PerkBuilder.bigStat("shadowskin", "Shadowskin",
            new OptScaleExactStat(10, Stats.INCREASED_EFFECT_OF_AURAS_RECEIVED.get()),
            new OptScaleExactStat(5, Stats.REDUCED_MANA_RESERVED.get()),
            new OptScaleExactStat(5, new ElementalResist(Elements.Dark))
        );
        PerkBuilder.bigStat("eyes_wide_open", "Eyes Wide Open",
            new OptScaleExactStat(10, Stats.EFFECT_OF_BUFFS_ON_YOU_PER_EFFECT_TAG.get(EffectTags.positive)),
            new OptScaleExactStat(5, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, Vitality.INSTANCE)
        );
        PerkBuilder.bigStat("big_arena", "Master of the Arena",
            new OptScaleExactStat(5, new AttackDamage(Elements.Physical), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(1, RegeneratePercentStat.HEALTH, ModType.FLAT),
            new OptScaleExactStat(10, Strength.INSTANCE, ModType.FLAT)
        );
        PerkBuilder.bigStat("rune_arrows", "Rune Arrows",
            new OptScaleExactStat(1, DatapackStats.PROJ_DMG_PER_STR),
            new OptScaleExactStat(3, Stats.STYLE_DAMAGE.get(PlayStyle.ranged)),
            new OptScaleExactStat(5, Stats.STYLE_DAMAGE_RECEIVED.get(PlayStyle.melee)),
            new OptScaleExactStat(-5, Intelligence.INSTANCE, ModType.FLAT)
        );
        PerkBuilder.bigStat("big_gladiator", "Master Gladiator",
            new OptScaleExactStat(10, Stats.ACCURACY.get(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, Stats.ATTACK_SPEED.get(), ModType.FLAT),
            new OptScaleExactStat(10, Dexterity.INSTANCE, ModType.FLAT)
        );

        PerkBuilder.bigStat("smokescreen", "Smoke Screen",
            new OptScaleExactStat(-10, Stats.THREAT_GENERATED.get()),
            new OptScaleExactStat(10, Stats.DAMAGE_PER_SPELL_TAG.get(SpellTag.technique))
        );

        PerkBuilder.bigStat("big_frog", "Aspect of the Frog",
            new OptScaleExactStat(10, HealthRegen.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, DodgeRating.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, Health.getInstance(), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_rat", "Aspect of the Rat",
            new OptScaleExactStat(5, new AttackDamage(Elements.Physical), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, Stats.CRIT_CHANCE.get(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, Dexterity.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(5, Intelligence.INSTANCE, ModType.FLAT)
        );

        PerkBuilder.bigStat("big_eagle", "Aspect of the Eagle",
            new OptScaleExactStat(5, Stats.STYLE_DAMAGE.get(PlayStyle.ranged), ModType.FLAT),
            new OptScaleExactStat(10, Stats.ACCURACY.get(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(20, Health.getInstance(), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_effect_of_ele", "Elemental Destruction",
            new OptScaleExactStat(5, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Elemental), ModType.FLAT),
            new OptScaleExactStat(10, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.POISON), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.FROSTBURN), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.BURN), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.bigStat("big_aspect_of_thunder", "Aspect of Thunder",
            new OptScaleExactStat(5, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Light), ModType.FLAT),
            new OptScaleExactStat(5, Stats.CRIT_CHANCE.get(), ModType.FLAT),
            new OptScaleExactStat(5, Stats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.health, AttackType.attack)), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_flow_of_mana", "Flow of Mana",
            new OptScaleExactStat(5, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Water), ModType.FLAT),
            new OptScaleExactStat(10, ManaRegen.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(20, Health.getInstance(), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_aspect_of_nature", "Aspect of Nature",
            new OptScaleExactStat(5, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Nature), ModType.FLAT),
            new OptScaleExactStat(10, HealthRegen.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, Stats.HEAL_STRENGTH.get(), ModType.FLAT)
        );

        PerkBuilder.bigStat("soothing_touch", "Soothing Touch",
            new OptScaleExactStat(5, Stats.HEAL_STRENGTH.get()),
            new OptScaleExactStat(10, SpecialStats.HEAL_CLEANSE)
        );

        PerkBuilder.bigStat("big_flame_of_life", "Flame of Life",
            new OptScaleExactStat(5, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Fire), ModType.FLAT),
            new OptScaleExactStat(1, RegeneratePercentStat.HEALTH, ModType.FLAT),
            new OptScaleExactStat(3, Stats.CAST_SPEED.get(), ModType.FLAT)
        );
        PerkBuilder.bigStat("big_will_of_mana", "Will of Mana",
            new OptScaleExactStat(25, Mana.getInstance(), ModType.FLAT),
            new OptScaleExactStat(3, ManaRegen.getInstance(), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_leviatan", "Leviathan",
            new OptScaleExactStat(10, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, Armor.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, DodgeRating.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.bigStat("big_overflow_mana", "Overflowing Mana",
            new OptScaleExactStat(15, Mana.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, ManaRegen.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(2, Stats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.mana, AttackType.attack)), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_ele_protection", "Elemental Protection",
            new OptScaleExactStat(1, new MaxElementalResist(Elements.Elemental), ModType.FLAT),
            new OptScaleExactStat(3, new ElementalResist(Elements.Elemental), ModType.FLAT),
            new OptScaleExactStat(5, Health.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.bigStat("big_force_of_will", "Force of Will",
            new OptScaleExactStat(20, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(1, new MaxElementalResist(Elements.Elemental), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_blood_siphon", "Blood Stealing",
            new OptScaleExactStat(5, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, Strength.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(2, Stats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.health, AttackType.attack)), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_precision", "Precision",
            new OptScaleExactStat(10, Stats.ACCURACY.get(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, Dexterity.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(3, Stats.ATTACK_SPEED.get(), ModType.FLAT)
        );

        PerkBuilder.bigStat("iron_skin", "Iron Skin",
            new OptScaleExactStat(10, Armor.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(2, new ElementalResist(Elements.Elemental), ModType.FLAT),
            new OptScaleExactStat(10, Health.getInstance(), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_vampire", "Vampire",
            new OptScaleExactStat(8, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(2, Stats.LIFESTEAL.get(), ModType.FLAT)
        );
        PerkBuilder.bigStat("big_warrior_heart", "Warrior's Heart",
            new OptScaleExactStat(10, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, Health.getInstance(), ModType.FLAT),
            new OptScaleExactStat(10, Strength.INSTANCE, ModType.FLAT)
        );

        PerkBuilder.bigStat("big_fighter", "Natural Fighter",
            new OptScaleExactStat(3, new AttackDamage(Elements.Physical), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(3, Stats.ATTACK_SPEED.get(), ModType.FLAT),
            new OptScaleExactStat(20, Stats.ACCURACY.get(), ModType.FLAT),
            new OptScaleExactStat(10, Strength.INSTANCE, ModType.FLAT)
        );
        PerkBuilder.bigStat("last_man_standing", "Last Man Standing",
            new OptScaleExactStat(15, Stats.DAMAGE_WHEN_LOW_HP.get())
        );
        PerkBuilder.bigStat("ravenger", "Ravenger",
            new OptScaleExactStat(5, Stats.DAMAGE_TO_LIVING.get()),
            new OptScaleExactStat(10, Stats.THREAT_GENERATED.get())
        );
        PerkBuilder.bigStat("big_mana_and_health", "Equilibrium",
            new OptScaleExactStat(5, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, Mana.getInstance(), ModType.LOCAL_INCREASE)
        );
        PerkBuilder.bigStat("big_armor", "Armored",
            new OptScaleExactStat(10, Armor.getInstance(), ModType.LOCAL_INCREASE)
        );
        PerkBuilder.bigStat("big_mana", "Mana Pool",
            new OptScaleExactStat(10, Mana.getInstance(), ModType.LOCAL_INCREASE)
        );
        PerkBuilder.bigStat("big_manareg", "Astral Permanence",
            new OptScaleExactStat(10, ManaRegen.getInstance(), ModType.LOCAL_INCREASE)
        );
        PerkBuilder.bigStat("big_lord_of_arcane", "Lord of Arcane",
            new OptScaleExactStat(10, Intelligence.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(5, SpellDamage.getInstance(), ModType.FLAT),
            new OptScaleExactStat(10, Mana.getInstance(), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_wisdom_of_the_elders", "Wisdom of the Elders",
            new OptScaleExactStat(10, Intelligence.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(20, Health.getInstance(), ModType.FLAT),
            new OptScaleExactStat(20, Mana.getInstance(), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_armor_and_ms", "Magical Armor",
            new OptScaleExactStat(10, Health.getInstance(), ModType.FLAT),
            new OptScaleExactStat(10, Armor.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, Mana.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.bigStat("tenacious_combatant", "Tenacious Combatant",
            new OptScaleExactStat(10, Armor.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(1, RegeneratePercentStat.HEALTH, ModType.FLAT)
        );

        PerkBuilder.bigStat("big_health_ms_mana_cost", "Righteous Indignation",
            new OptScaleExactStat(-5, Stats.MANA_COST.get(), ModType.FLAT),
            new OptScaleExactStat(10, Health.getInstance(), ModType.FLAT),
            new OptScaleExactStat(5, Mana.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.bigStat("big_health_and_mana", "Pools of Duality",
            new OptScaleExactStat(10, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, Mana.getInstance(), ModType.LOCAL_INCREASE)
        );
        PerkBuilder.bigStat("big_health_mana_cost", "Righteous Indignation",
            new OptScaleExactStat(-5, Stats.MANA_COST.get(), ModType.FLAT),
            new OptScaleExactStat(10, Health.getInstance(), ModType.LOCAL_INCREASE)
        );
        PerkBuilder.bigStat("faith_armor", "Armor of Faith",
            new OptScaleExactStat(20, Armor.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, Mana.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(2, new ElementalResist(Elements.Elemental), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_aura_eff", "Determination",
            new OptScaleExactStat(20, Stats.INCREASED_EFFECT_OF_AURAS_RECEIVED.get(), ModType.FLAT),
            new OptScaleExactStat(5, Stats.REDUCED_MANA_RESERVED.get(), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_poe", "Principle of Essence",
            new OptScaleExactStat(20, Mana.getInstance(), ModType.FLAT),
            new OptScaleExactStat(10, DodgeRating.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, Stats.ELEMENTAL_DAMAGE.get(Elements.Elemental), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_lacerator", "Lacerator",
            new OptScaleExactStat(5, Stats.ATTACK_SPEED.get(), ModType.FLAT),
            new OptScaleExactStat(5, Stats.CAST_SPEED.get(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, new AttackDamage(Elements.Physical), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.bigStat("big_mana_steal", "Mana Thief",
            new OptScaleExactStat(5, Intelligence.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(3, Stats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.mana, AttackType.attack)), ModType.FLAT),
            new OptScaleExactStat(20, Mana.getInstance(), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_spell_critical_hit", "Destruction",
            new OptScaleExactStat(5, Stats.SPELL_CRIT_CHANCE.get())
        );

        PerkBuilder.bigStat("big_fire_water_spell_crit_dmg", "Frostfire Essence",
            new OptScaleExactStat(3, Stats.SPELL_CRIT_CHANCE.get()),
            new OptScaleExactStat(3, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Fire), ModType.FLAT),
            new OptScaleExactStat(3, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Water), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_primal", "Nomadic Spirit",
            new OptScaleExactStat(20, Mana.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, DodgeRating.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, Strength.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(5, Intelligence.INSTANCE, ModType.FLAT)
        );

        PerkBuilder.bigStat("inner_sense", "Inner Sense",
            new OptScaleExactStat(10, Stats.ACCURACY.get(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, new AttackDamage(Elements.Physical), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, Dexterity.INSTANCE, ModType.FLAT)
        );

        PerkBuilder.bigStat("big_gambler", "Gambler",
            new OptScaleExactStat(15, GlobalCriticalDamage.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, Dexterity.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(5, Intelligence.INSTANCE, ModType.FLAT)
        );

        PerkBuilder.bigStat("big_poison_alchemy", "Dangerous Alchemy",
            new OptScaleExactStat(10, ManaRegen.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, Stats.CRIT_DAMAGE.get(), ModType.FLAT),
            new OptScaleExactStat(5, Intelligence.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(5, Dexterity.INSTANCE, ModType.FLAT)
        );

        PerkBuilder.bigStat("big_ele_unity", "Elemental Unity",
            new OptScaleExactStat(15, Stats.ELEMENTAL_DAMAGE.get(Elements.Elemental), ModType.FLAT),
            new OptScaleExactStat(5, Stats.CRIT_CHANCE.get(), ModType.FLAT),
            new OptScaleExactStat(5, Stats.SPELL_CRIT_CHANCE.get(), ModType.FLAT),
            new OptScaleExactStat(5, Intelligence.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(5, Dexterity.INSTANCE, ModType.FLAT)
        );

        PerkBuilder.bigStat("big_inc_area_spell_dmg", "Blast Radius",
            new OptScaleExactStat(15, Stats.INCREASED_AREA.get(), ModType.FLAT),
            new OptScaleExactStat(5, SpellDamage.getInstance(), ModType.FLAT),
            new OptScaleExactStat(20, Mana.getInstance(), ModType.FLAT)
        );

        PerkBuilder.bigStat("big_area_dmg_mana", "Destruction Zone",
            new OptScaleExactStat(10, Stats.AREA_DAMAGE.get(), ModType.FLAT),
            new OptScaleExactStat(10, Mana.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, Intelligence.INSTANCE, ModType.FLAT)
        );

        PerkBuilder.bigStat("self_reflection", "Self Reflection",
            new OptScaleExactStat(10, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, Mana.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, Intelligence.INSTANCE, ModType.FLAT)
        );
        PerkBuilder.bigStat("big_warrior_path", "Warrior's Path",
            new OptScaleExactStat(100, Armor.getInstance(), ModType.FLAT),
            new OptScaleExactStat(5, Stats.STYLE_DAMAGE.get(PlayStyle.melee), ModType.FLAT),
            new OptScaleExactStat(10, Strength.INSTANCE, ModType.FLAT)
        );
        PerkBuilder.bigStat("big_hunters_path", "Hunters's Path",
            new OptScaleExactStat(100, Stats.ACCURACY.get(), ModType.FLAT),
            new OptScaleExactStat(5, Stats.PROJECTILE_DAMAGE.get(), ModType.FLAT),
            new OptScaleExactStat(10, Dexterity.INSTANCE, ModType.FLAT)
        );
        PerkBuilder.bigStat("big_mages_path", "Mage's Path",
            new OptScaleExactStat(20, Mana.getInstance(), ModType.FLAT),
            new OptScaleExactStat(5, SpellDamage.getInstance(), ModType.FLAT),
            new OptScaleExactStat(10, Intelligence.INSTANCE, ModType.FLAT)
        );

        PerkBuilder.bigStat("big_endurance", "Endurance",
            new OptScaleExactStat(20, Health.getInstance(), ModType.FLAT),
            new OptScaleExactStat(10, Health.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.bigStat("big_willpower", "Willpower",
            new OptScaleExactStat(20, Health.getInstance(), ModType.FLAT),
            new OptScaleExactStat(10, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, ManaRegen.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.bigStat("big_agility", "Agility",
            new OptScaleExactStat(100, Stats.ACCURACY.get(), ModType.FLAT),
            new OptScaleExactStat(10, Stats.ACCURACY.get(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(100, DodgeRating.getInstance(), ModType.FLAT),
            new OptScaleExactStat(10, DodgeRating.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.bigStat("big_heal_int", "Deceiver",
            new OptScaleExactStat(5, Stats.HEAL_STRENGTH.get(), ModType.FLAT),
            new OptScaleExactStat(3, SpellDamage.getInstance(), ModType.FLAT),
            new OptScaleExactStat(3, Intelligence.INSTANCE, ModType.FLAT)
        );

        PerkBuilder.bigStat("big_heal_wis", "Learner",
            new OptScaleExactStat(5, Stats.HEAL_STRENGTH.get(), ModType.FLAT),
            new OptScaleExactStat(5, Mana.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(3, Intelligence.INSTANCE, ModType.FLAT)
        );

        PerkBuilder.bigStat("big_steal_str", "Ravager",
            new OptScaleExactStat(3, Stats.LIFESTEAL.get(), ModType.FLAT),
            new OptScaleExactStat(5, new AttackDamage(Elements.Physical), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(3, Strength.INSTANCE, ModType.FLAT)
        );

        PerkBuilder.bigStat("big_steal_vit", "Destroyer",
            new OptScaleExactStat(5, Stats.LIFESTEAL.get(), ModType.FLAT),
            new OptScaleExactStat(5, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(3, Wisdom.INSTANCE, ModType.FLAT)
        );
        PerkBuilder.bigStat("bone_grinder", "Bone Grinder",
            new OptScaleExactStat(10, Stats.RESOURCE_ON_KILL.get(ResourceType.health)),
            new OptScaleExactStat(10, Stats.RESOURCE_ON_KILL.get(ResourceType.mana)),
            new OptScaleExactStat(10, Stats.CHANCE_TO_GIVE_EFFECT_ON_KILL.get(BeneficialEffects.MARK))
        );

        PerkBuilder.bigStat("big_self_heal", "Good recipient",
            new OptScaleExactStat(5, Stats.HEALING_RECEIVED.get()),
            new OptScaleExactStat(5, HealthRegen.getInstance(), ModType.LOCAL_INCREASE)
        );
        PerkBuilder.bigStat("big_heal_strength", "Powerful Heals",
            new OptScaleExactStat(10, Stats.HEAL_STRENGTH.get())
        );
        PerkBuilder.bigStat("good_argument", "Good Argument",
            new OptScaleExactStat(10, Stats.THREAT_GENERATED.get()),
            new OptScaleExactStat(3, Stats.TOTAL_DAMAGE.get())
        );

        PerkBuilder.bigStat("effervescence", "Effervescence",
            new OptScaleExactStat(5, DatapackStats.HEAL_TO_SPELL_DMG),
            new OptScaleExactStat(3, HealthRegen.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(3, ManaRegen.getInstance(), ModType.LOCAL_INCREASE)
        );
        PerkBuilder.bigStat("big_mana_on_hit", "Astral Thief",
            new OptScaleExactStat(10, Stats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.mana, AttackType.attack)))
        );

        PerkBuilder.bigStat("elemental_penetration_damage_shield", "Shield of Elements",
            new OptScaleExactStat(3, new ElementalPenetration(Elements.Elemental)),
            new OptScaleExactStat(3, new ElementalResist(Elements.Elemental))
        );

        PerkBuilder.bigStat("big_dot_damage", "Slow Killer",
            new OptScaleExactStat(10, Stats.DOT_DAMAGE.get())
        );
        PerkBuilder.bigStat("redemtion", "Redemption",
            new OptScaleExactStat(10, Stats.CHANCE_TO_GIVE_EFFECT_ON_KILL.get(BeneficialEffects.BLESSING))
        );

        PerkBuilder.bigStat("missing_page", "Missing Page",
            new OptScaleExactStat(10, Stats.SHIELD_STRENGTH.get()),
            new OptScaleExactStat(10, Stats.DOT_DAMAGE.get())
        );
        PerkBuilder.bigStat("riftwalk", "Riftwalk",
            new OptScaleExactStat(5, Stats.DAMAGE_WHEN_TARGET_IS_FULL_HP.get()),
            new OptScaleExactStat(5, Stats.DAMAGE_WHEN_TARGET_IS_LOW_HP.get())
        );
        PerkBuilder.bigStat("avenger", "Avenger",
            new OptScaleExactStat(10, Stats.SHIELD_STRENGTH.get()),
            new OptScaleExactStat(5, Stats.CHANCE_TO_GIVE_EFFECT_ON_KILL.get(BeneficialEffects.BLESSING))
        );
        PerkBuilder.bigStat("big_elemental_penetration", "Element Piercer",
            new OptScaleExactStat(10, new ElementalPenetration(Elements.Elemental))
        );

        PerkBuilder.bigStat("big_spell_critical_damage", "Devastator",
            new OptScaleExactStat(15, Stats.SPELL_CRIT_DAMAGE.get())
        );

        PerkBuilder.bigStat("fungus_cape", "Fungus Cape",
            new OptScaleExactStat(5, Stats.CHANCE_TO_APPLY_EFFECT_WHEN_HIT.get(NegativeEffects.POISON))
        );

    }
}


