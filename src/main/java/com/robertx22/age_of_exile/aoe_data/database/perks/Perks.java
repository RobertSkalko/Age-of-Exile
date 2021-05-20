package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.ResourceAndAttack;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectTags;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.*;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalFocus;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalPenetration;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.offense.DualWieldDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.data.stats.types.special.SpecialStats;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;

public class Perks implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        PerkBuilder.stat("int", new OptScaleExactStat(1, Intelligence.INSTANCE, ModType.FLAT));
        PerkBuilder.stat("dex", new OptScaleExactStat(1, Dexterity.INSTANCE, ModType.FLAT));
        PerkBuilder.stat("str", new OptScaleExactStat(1, Strength.INSTANCE, ModType.FLAT));
        PerkBuilder.stat("agi", new OptScaleExactStat(1, Agility.INSTANCE, ModType.FLAT));
        PerkBuilder.stat("wis", new OptScaleExactStat(1, Wisdom.INSTANCE, ModType.FLAT));
        PerkBuilder.stat("vit", new OptScaleExactStat(1, Vitality.INSTANCE, ModType.FLAT));

        PerkBuilder.stat("cast_speed", new OptScaleExactStat(3, Stats.CAST_SPEED.get(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(-2, Stats.MANA_COST.get(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(3, Stats.ATTACK_SPEED.get(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(2, Stats.PROJECTILE_DAMAGE.get(), ModType.FLAT));

        PerkBuilder.stat("inc_aura_effect", new OptScaleExactStat(2, Stats.INCREASED_EFFECT_OF_AURAS_GIVEN.get(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(2, Stats.CRIT_DAMAGE.get(), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(1, Stats.CRIT_CHANCE.get(), ModType.FLAT));
        PerkBuilder.stat("crit_damage", new OptScaleExactStat(2, Stats.CRIT_DAMAGE.get(), ModType.FLAT));
        PerkBuilder.stat("crit_chance", new OptScaleExactStat(1, Stats.CRIT_CHANCE.get(), ModType.FLAT));

        PerkBuilder.stat("physical_damage", new OptScaleExactStat(2, Stats.ELEMENTAL_DAMAGE.get(Elements.Physical), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(4, Stats.REDUCED_MANA_RESERVED.get(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(5, Stats.SPELL_ACCURACY.get(), ModType.LOCAL_INCREASE));
        PerkBuilder.stat(new OptScaleExactStat(5, Stats.ACCURACY.get(), ModType.LOCAL_INCREASE));

        PerkBuilder.stat(new OptScaleExactStat(2, Stats.LIFESTEAL.get(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(2, Stats.SPELL_CRIT_DAMAGE.get(), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(1, Stats.SPELL_CRIT_CHANCE.get(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(3, Stats.HEAL_CRIT_DAMAGE.get(), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(2, Stats.HEAL_CRIT_CHANCE.get(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(3, Stats.SHIELD_DURATION.get(), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(3, Stats.SHIELD_STRENGTH.get(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(3, SpellDamage.getInstance(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(4, Stats.HEAL_STRENGTH.get(), ModType.FLAT));
        PerkBuilder.stat("healing_strength", new OptScaleExactStat(4, Stats.HEAL_STRENGTH.get(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(3, Stats.DOT_DAMAGE.get(), ModType.FLAT));
        PerkBuilder.stat("dot_damage", new OptScaleExactStat(3, Stats.DOT_DAMAGE.get(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(5, ManaRegen.getInstance(), ModType.LOCAL_INCREASE));
        PerkBuilder.stat("small_positive_effect_increase",
            new OptScaleExactStat(3, Stats.EFFECT_OF_BUFFS_ON_YOU_PER_EFFECT_TAG.get(EffectTags.positive))
        );

        PerkBuilder.stat("cooldown_reduction", new OptScaleExactStat(3, Stats.COOLDOWN_REDUCTION.get()));
        PerkBuilder.stat(new OptScaleExactStat(3, Stats.COOLDOWN_REDUCTION.get()));

        PerkBuilder.stat(new OptScaleExactStat(5, HealthRegen.getInstance(), ModType.LOCAL_INCREASE));

        PerkBuilder.stat(new OptScaleExactStat(3, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.BURN), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(3, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.FROSTBURN), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(3, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.POISON), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(3, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.BLEED), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(3, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.BLIND), ModType.FLAT));

        PerkBuilder.stat("less_aggro", new OptScaleExactStat(-2, Stats.THREAT_GENERATED.get(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(3, DodgeRating.getInstance(), ModType.LOCAL_INCREASE));
        PerkBuilder.stat(new OptScaleExactStat(3, Armor.getInstance(), ModType.LOCAL_INCREASE));
        PerkBuilder.stat(new OptScaleExactStat(2, Health.getInstance(), ModType.LOCAL_INCREASE));
        PerkBuilder.stat(new OptScaleExactStat(3, Mana.getInstance(), ModType.LOCAL_INCREASE));

        PerkBuilder.stat(new OptScaleExactStat(3, Stats.PROJECTILE_SPEED.get()));

        PerkBuilder.stat(new OptScaleExactStat(1, Stats.STYLE_DAMAGE.get(PlayStyle.melee), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(1, Stats.STYLE_DAMAGE.get(PlayStyle.ranged), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(1, Stats.STYLE_DAMAGE.get(PlayStyle.magic), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(2, new ElementalFocus(Elements.Dark)));
        PerkBuilder.stat(new OptScaleExactStat(2, new ElementalFocus(Elements.Light)));

        PerkBuilder.stat("mana_on_hit", new OptScaleExactStat(3, Stats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.mana, AttackType.attack)), ModType.FLAT));
        PerkBuilder.stat("health_on_hit", new OptScaleExactStat(3, Stats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.health, AttackType.attack)), ModType.FLAT));

        PerkBuilder.stat("more_dualwield_dmg", new OptScaleExactStat(3, DualWieldDamage.getInstance()));
        PerkBuilder.stat(new OptScaleExactStat(3, SpecialStats.DAY_NIGHT_DMG));

        Stats.ELEMENTAL_SPELL_DAMAGE.getAll()
            .forEach(x -> {
                PerkBuilder.stat(x.GUID(), new OptScaleExactStat(3, x, ModType.FLAT));
                PerkBuilder.stat(x.GUID() + "_and_dot", new OptScaleExactStat(1, x, ModType.FLAT), new OptScaleExactStat(3, Stats.ELE_DOT_DAMAGE.get(x.getElement()), ModType.FLAT));
            });

        Stats.WEAPON_DAMAGE.getAll()
            .forEach(x -> {
                PerkBuilder.stat(x.GUID(), new OptScaleExactStat(2, x, ModType.FLAT));
            });

        Stats.RESOURCE_ON_KILL.getAll()
            .forEach(x -> {
                PerkBuilder.stat(x.GUID(), new OptScaleExactStat(10, x, ModType.FLAT));
            });

        Stats.ELEMENTAL_WEAPON_DAMAGE.getAll()
            .forEach(x -> {
                PerkBuilder.stat(x.GUID(), new OptScaleExactStat(3, x, ModType.FLAT));

            });

        Stats.ELEMENTAL_DAMAGE.getAll()
            .forEach(x -> {
                PerkBuilder.stat(x.GUID(), new OptScaleExactStat(2, x, ModType.FLAT));
            });

        new ElementalResist(Elements.Nature).generateAllPossibleStatVariations()
            .forEach(x -> {
                PerkBuilder.stat(x.GUID(), new OptScaleExactStat(4, x, ModType.FLAT));

            });

        new ElementalPenetration(Elements.Nature).generateAllPossibleStatVariations()
            .forEach(x -> {
                PerkBuilder.stat(x.GUID(), new OptScaleExactStat(5, x, ModType.FLAT));
            });

        new AttackDamage(Elements.Nature).generateAllPossibleStatVariations()
            .forEach(x -> {
                PerkBuilder.stat(x.GUID(), new OptScaleExactStat(2, x, ModType.LOCAL_INCREASE));

            });

    }
}
