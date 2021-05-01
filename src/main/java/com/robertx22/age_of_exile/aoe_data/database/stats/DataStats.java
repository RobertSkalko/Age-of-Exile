package com.robertx22.age_of_exile.aoe_data.database.stats;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.stat_conditions.StatConditions;
import com.robertx22.age_of_exile.aoe_data.database.stat_effects.StatEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.DatapackStatBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EmptyAccessor;
import com.robertx22.age_of_exile.database.data.stats.Stat.StatGroup;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.datapacks.test.DataPackStatAccessor;
import com.robertx22.age_of_exile.database.data.stats.types.special.SpecialStats;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackType;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.HealEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.MobKillByDamageEvent;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import net.minecraft.util.Formatting;

import java.util.Arrays;

public class DataStats implements ISlashRegistryInit {

    public DataPackStatAccessor<EffectCtx> CHANCE_TO_GIVE_EFFECT_ON_KILL = DatapackStatBuilder
        .<EffectCtx>of(x -> "chance_to_give_" + x.id + "_on_kill", x -> x.element)
        .addAllOfType(Arrays.asList(
            BeneficialEffects.BLOODLUST
        ))
        .worksWithEvent(MobKillByDamageEvent.ID)
        .setPriority(0)
        .setSide(IStatEffect.EffectSides.Source)
        .addCondition(StatConditions.IF_RANDOM_ROLL)
        .addEffect(e -> StatEffects.GIVE_SELF_EFFECT.get(e))
        .setLocName(x -> SpecialStats.format(
            "Your " + x.element.getIconNameFormat() + " Killing blows have " + SpecialStats.VAL1 + "% chance of giving you " + x.locname
        ))
        .setLocDesc(x -> "Chance to give effect")
        .modifyAfterDone(x -> {
            x.is_long = true;
            x.is_perc = true;
            x.scaling = StatScaling.NONE;
        })
        .build();

    public DataPackStatAccessor<EffectCtx> CHANCE_TO_GIVE_EFFECT_ON_SELF = DatapackStatBuilder
        .<EffectCtx>of(x -> "chance_to_give_" + x.id + "_to_self", x -> x.element)
        .addAllOfType(Arrays.asList(BeneficialEffects.BLOODLUST))
        .worksWithEvent(DamageEffect.ID)
        .setPriority(100)
        .setSide(IStatEffect.EffectSides.Source)
        .addCondition(StatConditions.IF_RANDOM_ROLL)
        .addCondition(StatConditions.IF_CRIT)
        .addCondition(StatConditions.ATTACK_TYPE_MATCHES.get(AttackType.attack))
        .addEffect(StatEffects.GIVE_SELF_EFFECT.get(BeneficialEffects.BLOODLUST))
        .setLocName(x -> SpecialStats.format(
            "Your " + x.element.getIconNameFormat() + " Attacks have " + SpecialStats.VAL1 + "% chance of giving " + x.locname
        ))
        .setLocDesc(x -> "Chance to give effect")
        .modifyAfterDone(x -> {
            x.is_long = true;
            x.is_perc = true;
            x.scaling = StatScaling.NONE;
        })
        .build();

    public static DataPackStatAccessor<EmptyAccessor> CRIT_CHANCE = DatapackStatBuilder
        .<EmptyAccessor>ofSingle("critical_hit", Elements.Physical)
        .worksWithEvent(DamageEffect.ID)
        .setPriority(0)
        .setSide(IStatEffect.EffectSides.Source)
        .addCondition(StatConditions.IF_RANDOM_ROLL)
        .addCondition(StatConditions.ATTACK_TYPE_MATCHES.get(AttackType.attack))
        .addEffect(StatEffects.SET_IS_CRIT)
        .setLocName(x -> "Crit Chance")
        .setLocDesc(x -> "Chance to multiply attack damage by critical damage")
        .modifyAfterDone(x -> {
            x.is_perc = true;
            x.base = 1;
            x.max = 100;
            x.min = 0;
            x.group = StatGroup.MAIN;
            x.icon = "\u2694";
            x.format = Formatting.YELLOW;
        })
        .build();

    public static DataPackStatAccessor<EmptyAccessor> SPELL_CRIT_CHANCE = DatapackStatBuilder
        .<EmptyAccessor>ofSingle("spell_critical_hit", Elements.Physical)
        .worksWithEvent(DamageEffect.ID)
        .setPriority(0)
        .setSide(IStatEffect.EffectSides.Source)
        .addCondition(StatConditions.IF_RANDOM_ROLL)
        .addCondition(StatConditions.IS_SPELL)
        .addCondition(StatConditions.ATTACK_TYPE_MATCHES.get(AttackType.spell))
        .addEffect(StatEffects.SET_IS_CRIT)
        .setLocName(x -> "Spell Crit Chance")
        .setLocDesc(x -> "Chance to multiply attack damage by critical damage")
        .modifyAfterDone(x -> {
            x.is_perc = true;
            x.base = 1;
            x.max = 100;
            x.min = 0;
            x.group = StatGroup.MAIN;
            x.icon = "\u2739";
            x.format = Formatting.LIGHT_PURPLE;
        })
        .build();

    public static DataPackStatAccessor<EmptyAccessor> HEAL_CRIT_CHANCE = DatapackStatBuilder
        .<EmptyAccessor>ofSingle("crit_heal_chance", Elements.Physical)
        .worksWithEvent(HealEffect.ID)
        .setPriority(0)
        .setSide(IStatEffect.EffectSides.Source)
        .addCondition(StatConditions.IF_RANDOM_ROLL)
        .addCondition(StatConditions.IS_SPELL)
        .addEffect(StatEffects.SET_IS_CRIT)
        .setLocName(x -> "Spell Crit Chance")
        .setLocDesc(x -> "Chance to multiply attack damage by critical damage")
        .modifyAfterDone(x -> {
            x.is_perc = true;
            x.base = 0;
            x.min = 0;
            x.max = 100;
            x.group = StatGroup.MAIN;

            x.icon = "\u2694";
            x.format = Formatting.YELLOW;
        })
        .build();

    public static DataPackStatAccessor<EmptyAccessor> CRIT_DAMAGE = DatapackStatBuilder
        .ofSingle("critical_damage", Elements.Physical)
        .worksWithEvent(DamageEffect.ID)
        .setPriority(100)
        .setSide(IStatEffect.EffectSides.Source)
        .addCondition(StatConditions.IF_CRIT)
        .addCondition(StatConditions.CRIT_ROLL_DIDNT_FAIL)
        .addCondition(StatConditions.ATTACK_TYPE_MATCHES.get(AttackType.attack))
        .addEffect(StatEffects.INCREASE_VALUE)
        .setLocName(x -> "Crit Damage")
        .setLocDesc(x -> "If Critical, multiply by x")
        .modifyAfterDone(x -> {
            x.is_perc = true;
            x.base = 50;
            x.min = 0;
            x.max = 500;
            x.group = StatGroup.MAIN;

            x.icon = "\u2694";
            x.format = Formatting.RED;

        })
        .build();

    public static DataPackStatAccessor<EmptyAccessor> SPELL_CRIT_DAMAGE = DatapackStatBuilder
        .ofSingle("spell_critical_damage", Elements.Physical)
        .worksWithEvent(DamageEffect.ID)
        .setPriority(100)
        .setSide(IStatEffect.EffectSides.Source)
        .addCondition(StatConditions.IF_CRIT)
        .addCondition(StatConditions.CRIT_ROLL_DIDNT_FAIL)
        .addCondition(StatConditions.ATTACK_TYPE_MATCHES.get(AttackType.spell))
        .addEffect(StatEffects.INCREASE_VALUE)
        .setLocName(x -> "Spell Crit Damage")
        .setLocDesc(x -> "If Critical, multiply by x")
        .modifyAfterDone(x -> {
            x.is_perc = true;
            x.base = 50;
            x.min = 0;
            x.max = 500;
            x.group = StatGroup.MAIN;
            x.icon = "\u2739";
            x.format = Formatting.DARK_PURPLE;

        })
        .build();

    public static DataPackStatAccessor<EmptyAccessor> HEAL_CRIT_DAMAGE = DatapackStatBuilder
        .ofSingle("heal_crit_dmg", Elements.Physical)
        .worksWithEvent(HealEffect.ID)
        .setPriority(100)
        .setSide(IStatEffect.EffectSides.Source)
        .addCondition(StatConditions.IF_CRIT)
        .addCondition(StatConditions.IS_SPELL)
        .addEffect(StatEffects.INCREASE_VALUE)
        .setLocName(x -> "Crit Heal Damage")
        .setLocDesc(x -> "If Critical, multiply by x")
        .modifyAfterDone(x -> {
            x.is_perc = true;
            x.base = 50;
            x.min = 0;
            x.max = 500;
            x.group = StatGroup.MAIN;
            x.icon = "\u2694";
            x.format = Formatting.GOLD;
        })
        .build();

    public static DataPackStatAccessor<EmptyAccessor> NON_CRIT_DAMAGE = DatapackStatBuilder
        .ofSingle("non_crit_damage", Elements.Physical)
        .worksWithEvent(DamageEffect.ID)
        .setPriority(99)
        .setSide(IStatEffect.EffectSides.Source)
        .addCondition(StatConditions.IF_NOT_CRIT)
        .addEffect(StatEffects.INCREASE_VALUE)
        .setLocName(x -> "Non Critical Damage")
        .setLocDesc(x -> "If not a Critical, multiply by x")
        .modifyAfterDone(x -> {
            x.is_perc = true;
            x.min = -100;
            x.max = 500;
            x.group = StatGroup.MAIN;
            x.icon = "\u2694";
            x.format = Formatting.RED;
        })
        .build();

    public static DataPackStatAccessor<EmptyAccessor> ACCURACY = DatapackStatBuilder
        .ofSingle("accuracy", Elements.Physical)
        .worksWithEvent(DamageEffect.ID)
        .setPriority(0)
        .setSide(IStatEffect.EffectSides.Source)
        .addCondition(StatConditions.ATTACK_TYPE_MATCHES.get(AttackType.attack))
        .addEffect(StatEffects.SET_ACCURACY)
        .setLocName(x -> "Accuracy")
        .setLocDesc(x -> "Increases your chance to hit, low accuracy also causes crits to fail. Specifically it decreases opponent's chance to dodge")
        .modifyAfterDone(x -> {
            x.base = 0;
            x.min = 0;
            x.scaling = StatScaling.NORMAL;
            x.group = StatGroup.MAIN;
        })
        .build();

    public static DataPackStatAccessor<EmptyAccessor> SPELL_ACCURACY = DatapackStatBuilder
        .ofSingle("spell_accuracy", Elements.Physical)
        .worksWithEvent(DamageEffect.ID)
        .setPriority(0)
        .setSide(IStatEffect.EffectSides.Source)
        .addCondition(StatConditions.ATTACK_TYPE_MATCHES.get(AttackType.spell))
        .addEffect(StatEffects.SET_ACCURACY)
        .setLocName(x -> "Spell Accuracy")
        .setLocDesc(x -> "Increases your chance to hit, low accuracy also causes crits to fail. Specifically it decreases opponent's chance to dodge")
        .modifyAfterDone(x -> {
            x.base = 0;
            x.min = 0;
            x.scaling = StatScaling.NORMAL;
            x.group = StatGroup.MAIN;
        })
        .build();

    @Override
    public void registerAll() {
        DatapackStatBuilder.STATS_TO_ADD_TO_SERIALIZATION.forEach(x -> x.addToSerializables());
    }
}
