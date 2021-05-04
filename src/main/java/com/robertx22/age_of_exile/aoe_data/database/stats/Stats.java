package com.robertx22.age_of_exile.aoe_data.database.stats;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.stat_conditions.StatConditions;
import com.robertx22.age_of_exile.aoe_data.database.stat_effects.StatEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.*;
import com.robertx22.age_of_exile.database.data.stats.Stat.StatGroup;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.datapacks.test.DataPackStatAccessor;
import com.robertx22.age_of_exile.database.data.stats.types.special.SpecialStats;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.OnMobKilledByDamageEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.RestoreResourceEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.RestoreType;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;
import net.minecraft.util.Formatting;

import java.util.Arrays;

public class Stats implements ISlashRegistryInit {

    public static DataPackStatAccessor<EffectCtx> CHANCE_TO_GIVE_EFFECT_ON_KILL = DatapackStatBuilder
        .<EffectCtx>of(x -> "chance_to_get_" + x.id + "_on_kill", x -> x.element)
        .addAllOfType(Arrays.asList(
            BeneficialEffects.BLOODLUST
        ))
        .worksWithEvent(OnMobKilledByDamageEvent.ID)
        .setPriority(0)
        .setSide(EffectSides.Source)
        .addCondition(StatConditions.IF_RANDOM_ROLL)
        .addCondition(StatConditions.ELEMENT_MATCH_STAT)
        .addEffect(e -> StatEffects.GIVE_SELF_EFFECT.get(e))
        .setLocName(x -> SpecialStats.format(
            "Your " + x.element.getIconNameFormat() + " Killing blows have " + SpecialStats.VAL1 + "% chance of giving you " + x.locname
        ))
        .setLocDesc(x -> "")
        .modifyAfterDone(x -> {
            x.min = 0;
            x.max = 100;
            x.is_long = true;
            x.is_perc = true;
            x.scaling = StatScaling.NONE;

        })
        .build();

    public static DataPackStatAccessor<LeechInfo> ELEMENT_LEECH_RESOURCE = DatapackStatBuilder
        .<LeechInfo>of(x -> x.element.guidName + "_" + x.resourceType.id + "_leech", x -> x.element)
        .addAllOfType(LeechInfo.allCombos())
        .worksWithEvent(DamageEvent.ID)
        .setPriority(100)
        .setSide(EffectSides.Source)
        .addCondition(StatConditions.ELEMENT_MATCH_STAT)
        .addEffect(e -> StatEffects.LEECH_RESTORE_RESOURCE_BASED_ON_STAT_DATA.get(e.resourceType))
        .setLocName(x ->
            SpecialStats.format(
                "Leech " + SpecialStats.VAL1 + "% of your " + x.element.getIconNameFormat() + " Damage as " + x.resourceType.locname
            )
        )
        .setLocDesc(x -> "")
        .modifyAfterDone(x -> {
            x.is_long = true;
            x.min = 0;
            x.is_perc = true;
            x.scaling = StatScaling.NONE;
        })
        .build();

    public static DataPackStatAccessor<ResourceType> RESOURCE_ON_KILL = DatapackStatBuilder
        .<ResourceType>of(x -> x.id + "_on_kill", x -> Elements.All)
        .addAllOfType(Arrays.asList(
            ResourceType.health,
            ResourceType.mana
        ))
        .worksWithEvent(OnMobKilledByDamageEvent.ID)
        .setPriority(0)
        .setSide(EffectSides.Source)
        .addEffect(e -> StatEffects.LEECH_RESTORE_RESOURCE_BASED_ON_STAT_DATA.get(e))
        .setLocName(x -> x.locname + " on Kill")
        .setLocDesc(x -> "")
        .modifyAfterDone(x -> {
            x.min = 0;
            x.is_perc = false;
            x.scaling = StatScaling.NORMAL;
        })
        .build();

    public static DataPackStatAccessor<ResourceAndAttack> RESOURCE_ON_HIT = DatapackStatBuilder
        .<ResourceAndAttack>of(x -> x.resource.id + "_on_" + x.attackType.id + "_hit", x -> Elements.All)
        .addAllOfType(ResourceAndAttack.allCombos())
        .worksWithEvent(DamageEvent.ID)
        .setPriority(100)
        .setSide(EffectSides.Source)
        .addEffect(e -> StatEffects.LEECH_RESTORE_RESOURCE_BASED_ON_STAT_DATA.get(e.resource))
        .setLocName(x -> x.resource.locname + " on " + x.attackType.locname + " Hit")
        .setLocDesc(x -> "")
        .modifyAfterDone(x -> {
            x.min = 0;
            x.is_perc = false;
            x.scaling = StatScaling.NORMAL;
        })
        .build();

    public static DataPackStatAccessor<EffectCtx> CHANCE_TO_GIVE_EFFECT_ON_SELF = DatapackStatBuilder
        .<EffectCtx>of(x -> "chance_to_give_" + x.id + "_to_self", x -> x.element)
        .addAllOfType(Arrays.asList(BeneficialEffects.BLOODLUST))
        .worksWithEvent(DamageEvent.ID)
        .setPriority(100)
        .setSide(EffectSides.Source)
        .addCondition(StatConditions.IF_RANDOM_ROLL)
        .addCondition(StatConditions.ELEMENT_MATCH_STAT)
        .addCondition(StatConditions.IS_ATTACK_OR_SPELL_ATTACK)
        .addEffect(x -> StatEffects.GIVE_SELF_EFFECT.get(x))
        .setLocName(x -> SpecialStats.format(
            "Your " + x.element.getIconNameFormat() + " Attacks have " + SpecialStats.VAL1 + "% chance of giving " + x.locname
        ))
        .setLocDesc(x -> "Chance to give effect")
        .modifyAfterDone(x -> {
            x.min = 0;
            x.max = 100;
            x.is_long = true;
            x.is_perc = true;
            x.scaling = StatScaling.NONE;
        })
        .build();

    public static DataPackStatAccessor<EffectCtx> CHANCE_OF_APPLYING_EFFECT = DatapackStatBuilder
        .<EffectCtx>of(x -> "chance_of_" + x.id, x -> x.element)
        .addAllOfType(Arrays.asList(
            NegativeEffects.BURN,
            NegativeEffects.FROSTBURN,
            NegativeEffects.BLEED,
            NegativeEffects.TORMENT,
            NegativeEffects.BLIND,
            NegativeEffects.POISON
            )
        )
        .worksWithEvent(DamageEvent.ID)
        .setPriority(100)
        .setSide(EffectSides.Source)
        .addCondition(StatConditions.IF_RANDOM_ROLL)
        .addCondition(StatConditions.ELEMENT_MATCH_STAT)
        .addCondition(StatConditions.IS_ATTACK_OR_SPELL_ATTACK)
        .addEffect(x -> StatEffects.GIVE_EFFECT_TO_TARGET.get(x))
        .setLocName(x -> SpecialStats.format(
            "Your " + x.element.getIconNameFormat() + " Attacks have " + SpecialStats.VAL1 + "% chance of applying " + x.locname
        ))
        .setLocDesc(x -> "Chance to give effect")
        .modifyAfterDone(x -> {
            x.min = 0;
            x.max = 100;
            x.is_long = true;
            x.is_perc = true;
            x.scaling = StatScaling.NONE;
        })
        .build();

    public static DataPackStatAccessor<EmptyAccessor> CRIT_CHANCE = DatapackStatBuilder
        .<EmptyAccessor>ofSingle("critical_hit", Elements.Physical)
        .worksWithEvent(DamageEvent.ID)
        .setPriority(0)
        .setSide(EffectSides.Source)
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
        .worksWithEvent(DamageEvent.ID)
        .setPriority(0)
        .setSide(EffectSides.Source)
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
        .worksWithEvent(RestoreResourceEvent.ID)
        .setPriority(0)
        .setSide(EffectSides.Source)
        .addCondition(StatConditions.IF_RANDOM_ROLL)
        .addCondition(StatConditions.IS_SPELL)
        .addCondition(StatConditions.IS_RESOURCE.get(ResourceType.health))
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
        .worksWithEvent(DamageEvent.ID)
        .setPriority(100)
        .setSide(EffectSides.Source)
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
        .worksWithEvent(DamageEvent.ID)
        .setPriority(100)
        .setSide(EffectSides.Source)
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
        .worksWithEvent(RestoreResourceEvent.ID)
        .setPriority(100)
        .setSide(EffectSides.Source)
        .addCondition(StatConditions.IF_CRIT)
        .addCondition(StatConditions.IS_SPELL)
        .addCondition(StatConditions.IS_RESOURCE.get(ResourceType.health))
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
        .worksWithEvent(DamageEvent.ID)
        .setPriority(99)
        .setSide(EffectSides.Source)
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
        .worksWithEvent(DamageEvent.ID)
        .setPriority(0)
        .setSide(EffectSides.Source)
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
        .worksWithEvent(DamageEvent.ID)
        .setPriority(0)
        .setSide(EffectSides.Source)
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

    public static DataPackStatAccessor<EmptyAccessor> PROJECTILE_DAMAGE = DatapackStatBuilder
        .ofSingle("projectile_damage", Elements.Physical)
        .worksWithEvent(DamageEvent.ID)
        .setPriority(0)
        .setSide(EffectSides.Source)
        .addCondition(StatConditions.IS_ANY_PROJECTILE)
        .addEffect(StatEffects.INCREASE_VALUE)
        .setLocName(x -> "Projectile Damage")
        .setLocDesc(x -> "Affects projectile damage, includes projectile spells like fireballs, and ranged basic attacks.")
        .modifyAfterDone(x -> {
            x.is_perc = true;
            x.base = 0;
            x.icon = "\u27B9";
            x.format = Formatting.RED;
        })
        .build();

    public static DataPackStatAccessor<EmptyAccessor> AREA_DAMAGE = DatapackStatBuilder
        .ofSingle("area_dmg", Elements.All)
        .worksWithEvent(DamageEvent.ID)
        .setPriority(0)
        .setSide(EffectSides.Source)
        .addCondition(StatConditions.IS_AREA_SPELL)
        .addEffect(StatEffects.INCREASE_VALUE)
        .setLocName(x -> "Area Damage")
        .setLocDesc(x -> "Affects dmg done by area of effect abilities. Think meteor or other large aoe spells")
        .modifyAfterDone(x -> {
            x.is_perc = true;
            x.base = 0;
            x.format = Formatting.BLUE;
        })
        .build();

    public static DataPackStatAccessor<EmptyAccessor> NIGHT_DAMAGE = DatapackStatBuilder
        .ofSingle("night_dmg", Elements.All)
        .worksWithEvent(DamageEvent.ID)
        .setPriority(0)
        .setSide(EffectSides.Source)
        .addCondition(StatConditions.IS_NIGHT)
        .addEffect(StatEffects.INCREASE_VALUE)
        .setLocName(x -> "Night Damage")
        .setLocDesc(x -> "Increases dmg at night.")
        .modifyAfterDone(x -> {
            x.is_perc = true;
            x.base = 0;
            x.format = Formatting.DARK_PURPLE;
        })
        .build();

    public static DataPackStatAccessor<EmptyAccessor> DAY_DAMAGE = DatapackStatBuilder
        .ofSingle("day_dmg", Elements.All)
        .worksWithEvent(DamageEvent.ID)
        .setPriority(0)
        .setSide(EffectSides.Source)
        .addCondition(StatConditions.IS_NIGHT)
        .addEffect(StatEffects.INCREASE_VALUE)
        .setLocName(x -> "Day Damage")
        .setLocDesc(x -> "Increases dmg at daytime.")
        .modifyAfterDone(x -> {
            x.is_perc = true;
            x.base = 0;
            x.format = Formatting.YELLOW;
        })
        .build();

    public static DataPackStatAccessor<EmptyAccessor> DOT_DAMAGE = DatapackStatBuilder
        .ofSingle("dot_dmg", Elements.All)
        .worksWithEvent(DamageEvent.ID)
        .setPriority(0)
        .setSide(EffectSides.Source)
        .addCondition(StatConditions.ATTACK_TYPE_MATCHES.get(AttackType.dot))
        .addEffect(StatEffects.INCREASE_VALUE)
        .setLocName(x -> "Damage over Time")
        .setLocDesc(x -> "Increases dmg of effects that do damage over time, like burn")
        .modifyAfterDone(x -> {
            x.is_perc = true;
            x.base = 0;
            x.format = Formatting.RED;
        })
        .build();

    public static DataPackStatAccessor<EmptyAccessor> TOTAL_DAMAGE = DatapackStatBuilder
        .ofSingle("total_damage", Elements.All)
        .worksWithEvent(DamageEvent.ID)
        .setPriority(0)
        .setSide(EffectSides.Source)
        .addEffect(StatEffects.INCREASE_VALUE)
        .setLocName(x -> "Total Damage")
        .setLocDesc(x -> "Increases all your damage.")
        .modifyAfterDone(x -> {
            x.is_perc = true;
            x.base = 0;
            x.format = Formatting.RED;
        })
        .build();

    public static DataPackStatAccessor<Elements> ELE_DOT_DAMAGE = DatapackStatBuilder
        .<Elements>of(x -> x.guidName + "_dot_damage", x -> x)
        .addAllOfType(Elements.getAllSingleElementals())
        .worksWithEvent(DamageEvent.ID)
        .setPriority(0)
        .setSide(EffectSides.Source)
        .addCondition(StatConditions.ELEMENT_MATCH_STAT)
        .addCondition(StatConditions.ATTACK_TYPE_MATCHES.get(AttackType.dot))
        .addEffect(StatEffects.INCREASE_VALUE)
        .setLocName(x -> x.dmgName + " Damage Over Time")
        .setLocDesc(x -> "Fire damage over time increases damage of burn, for example.")
        .modifyAfterDone(x -> {
            x.is_perc = true;
            x.base = 0;
            x.format = Formatting.RED;
            x.group = StatGroup.Misc;
        })
        .build();

    public static DataPackStatAccessor<EmptyAccessor> HEAL_STRENGTH = DatapackStatBuilder
        .ofSingle("increase_healing", Elements.All)
        .worksWithEvent(RestoreResourceEvent.ID)
        .setPriority(100)
        .setSide(EffectSides.Source)
        .addCondition(StatConditions.IS_SPELL)
        .addCondition(StatConditions.IS_RESOURCE.get(ResourceType.health))
        .addEffect(StatEffects.INCREASE_VALUE)
        .setLocName(x -> "Heal Strength")
        .setLocDesc(x -> "Increases spell related heals.")
        .modifyAfterDone(x -> {
            x.is_perc = true;
            x.base = 0;
            x.format = Formatting.YELLOW;
            x.group = StatGroup.RESTORATION;
        })
        .build();

    public static DataPackStatAccessor<EmptyAccessor> HEALING_RECEIVED = DatapackStatBuilder
        .ofSingle("heal_effect_on_self", Elements.All)
        .worksWithEvent(RestoreResourceEvent.ID)
        .setPriority(100)
        .setSide(EffectSides.Target)
        .addCondition(StatConditions.IS_SPELL)
        .addCondition(StatConditions.IS_RESOURCE.get(ResourceType.health))
        .addEffect(StatEffects.INCREASE_VALUE)
        .setLocName(x -> "Healing Received")
        .setLocDesc(x -> "Increases spell related heals on you.")
        .modifyAfterDone(x -> {
            x.is_perc = true;
            x.base = 0;
            x.min = -100;
            x.format = Formatting.YELLOW;
            x.group = StatGroup.RESTORATION;
        })
        .build();

    public static DataPackStatAccessor<EmptyAccessor> LIFESTEAL = DatapackStatBuilder
        .ofSingle("lifesteal", Elements.All)
        .worksWithEvent(DamageEvent.ID)
        .setPriority(100)
        .setSide(EffectSides.Source)
        .addCondition(StatConditions.ATTACK_TYPE_MATCHES.get(AttackType.attack))
        .addEffect(StatEffects.LEECH_PERCENT_OF_DAMAGE_AS_RESOURCE.get(ResourceType.health))
        .setLocName(x -> "Lifesteal")
        .setLocDesc(x -> "Restore % of damage as health.")
        .modifyAfterDone(x -> {
            x.is_perc = true;
            x.base = 0;
            x.min = 0;
            x.scaling = StatScaling.NONE;
            x.format = Formatting.RED;
            x.group = StatGroup.RESTORATION;
        })
        .build();

    public static DataPackStatAccessor<EmptyAccessor> INCREASED_LEECH = DatapackStatBuilder
        .ofSingle("inc_leech", Elements.All)
        .worksWithEvent(RestoreResourceEvent.ID)
        .setPriority(100)
        .setSide(EffectSides.Source)
        .addCondition(StatConditions.IS_RESTORE_TYPE.get(RestoreType.leech))
        .addEffect(StatEffects.INCREASE_VALUE)
        .setLocName(x -> "Increased Leech Effects")
        .setLocDesc(x -> "Affects all resource leech stats like: onhit, leech dmg, on kill restore etc")
        .modifyAfterDone(x -> {
            x.is_perc = true;
            x.base = 0;
            x.min = 0;
            x.scaling = StatScaling.NONE;
            x.format = Formatting.RED;
            x.group = StatGroup.RESTORATION;
        })
        .build();

    @Override
    public void registerAll() {
        DatapackStatBuilder.STATS_TO_ADD_TO_SERIALIZATION.forEach(x -> x.addToSerializables());
    }
}
