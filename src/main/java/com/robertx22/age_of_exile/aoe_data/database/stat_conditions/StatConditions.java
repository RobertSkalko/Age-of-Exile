package com.robertx22.age_of_exile.aoe_data.database.stat_conditions;

import com.robertx22.age_of_exile.aoe_data.DataHolder;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectTags;
import com.robertx22.age_of_exile.database.data.skill_gem.SpellTag;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.effectdatas.ThreatGenType;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.RestoreType;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition.*;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StatConditions implements ISlashRegistryInit {

    public static StatCondition IF_CRIT = new IsBooleanTrueCondition(EventData.CRIT);
    public static StatCondition IF_NOT_CRIT = new IsBooleanTrueCondition(EventData.CRIT).flipCondition();
    public static StatCondition IF_RANDOM_ROLL = new RandomRollCondition();
    public static StatCondition IS_SPELL = new IsSpellCondition();
    public static StatCondition ELEMENT_MATCH_STAT = new ElementMatchesStat();
    public static StatCondition IS_DAY = new IsDayCondition();
    public static StatCondition IS_NIGHT = new IsDayCondition().flipCondition();
    public static StatCondition IS_IN_COMBAT = new IsInCombatCondition();
    public static StatCondition IS_BASIC_ATTACK = new IsBooleanTrueCondition(EventData.IS_BASIC_ATTACK);
    public static StatCondition IS_TARGET_LOW_HP = new IsHealthBellowPercentCondition("is_target_low_hp", 30, EffectSides.Target);
    public static StatCondition IS_SOURCE_LOW_HP = new IsHealthBellowPercentCondition("is_source_low_hp", 30, EffectSides.Source);
    public static StatCondition IS_ELEMENTAL = new StringMatchesCondition(EventData.ELEMENT, Elements.Physical.name()).flipCondition();
    public static StatCondition IS_NON_MAGIC_STYLE = new StringMatchesCondition(EventData.STYLE, PlayStyle.magic.name()).flipCondition();

    public static DataHolder<EffectTags, StatCondition> EFFECT_HAS_TAG = new DataHolder<>(
        EffectTags.values()
        , x -> new EffectHasTagCondition(x));

    public static DataHolder<ResourceType, StatCondition> IS_RESOURCE = new DataHolder<>(
        ResourceType.values()
        , x -> new StringMatchesCondition(EventData.RESOURCE_TYPE, x.name()));

    public static DataHolder<ThreatGenType, StatCondition> IS_THREAT_GEN_TYPE = new DataHolder<>(
        ThreatGenType.values()
        , x -> new StringMatchesCondition(EventData.THREAT_GEN_TYPE, x.name()));

    public static DataHolder<SpellTag, StatCondition> SPELL_HAS_TAG = new DataHolder<>(
        SpellTag.values()
        , x -> new SpellHasTagCondition(x));

    public static DataHolder<PlayStyle, StatCondition> IS_STYLE = new DataHolder<>(
        PlayStyle.values()
        , x -> new StringMatchesCondition(EventData.STYLE, x.name()));

    public static DataHolder<RestoreType, StatCondition> IS_RESTORE_TYPE = new DataHolder<>(
        RestoreType.values()
        , x -> new StringMatchesCondition(EventData.RESTORE_TYPE, x.name()));

    public static StatCondition IS_MELEE_WEAPON = new EitherIsTrueCondition("is_melee_weapon",
        Arrays.stream(WeaponTypes.values())
            .filter(x -> x.isMelee())
            .map(x -> new WeaponTypeMatches(x).GUID())
            .collect(Collectors.toList())
    );

    public static StatCondition IS_MAGIC_WEAPON = new EitherIsTrueCondition("is_magic_weapon",
        Arrays.stream(WeaponTypes.values())
            .filter(x -> x.style == PlayStyle.magic)
            .map(x -> new WeaponTypeMatches(x).GUID())
            .collect(Collectors.toList())
    );
    public static StatCondition IS_RANGED_WEAPON = new EitherIsTrueCondition("is_ranged_weapon",
        Arrays.asList(
            new WeaponTypeMatches(WeaponTypes.crossbow).GUID(),
            new WeaponTypeMatches(WeaponTypes.bow).GUID()
        ));

    public static StatCondition IS_ANY_PROJECTILE = new EitherIsTrueCondition("is_projectile",
        Arrays.asList(
            SPELL_HAS_TAG.get(SpellTag.projectile)
                .GUID(),
            IS_RANGED_WEAPON.GUID()
        ));

    public static DataHolder<AttackType, StatCondition> ATTACK_TYPE_MATCHES = new DataHolder<>(
        AttackType.values()
        , x -> new StringMatchesCondition(EventData.ATTACK_TYPE, x.name()));

    public static DataHolder<WeaponTypes, StatCondition> WEAPON_TYPE_MATCHES = new DataHolder<>(
        WeaponTypes.values()
        , x -> new WeaponTypeMatches(x));

    public static StatCondition IS_ATTACK_OR_SPELL_ATTACK = new EitherIsTrueCondition(
        "is_attack_or_spell_attack",
        Arrays.asList(ATTACK_TYPE_MATCHES.get(AttackType.attack)
            .GUID(), ATTACK_TYPE_MATCHES.get(AttackType.spell)
            .GUID())
    );

    public static StatCondition CRIT_ROLL_DIDNT_FAIL = new IsBooleanTrueCondition(EventData.ACCURACY_CRIT_FAILED).flipCondition();

    public static void loadClass() {
    }

    @Override
    public void registerAll() {

        ATTACK_TYPE_MATCHES.addToSerializables();
        IF_CRIT.addToSerializables();
        IF_RANDOM_ROLL.addToSerializables();
        CRIT_ROLL_DIDNT_FAIL.addToSerializables();
        IS_SPELL.addToSerializables();
        WEAPON_TYPE_MATCHES.addToSerializables();
        IF_NOT_CRIT.addToSerializables();
        ELEMENT_MATCH_STAT.addToSerializables();
        IS_ATTACK_OR_SPELL_ATTACK.addToSerializables();
        IS_DAY.addToSerializables();
        IS_NIGHT.addToSerializables();
        IS_BASIC_ATTACK.addToSerializables();
        IS_RANGED_WEAPON.addToSerializables();
        IS_ANY_PROJECTILE.addToSerializables();
        IS_MAGIC_WEAPON.addToSerializables();
        IS_MELEE_WEAPON.addToSerializables();
        SPELL_HAS_TAG.addToSerializables();
        IS_STYLE.addToSerializables();
        IS_NON_MAGIC_STYLE.addToSerializables();
        IS_RESOURCE.addToSerializables();
        IS_IN_COMBAT.addToSerializables();
        IS_RESTORE_TYPE.addToSerializables();
        EFFECT_HAS_TAG.addToSerializables();
        IS_THREAT_GEN_TYPE.addToSerializables();
        IS_TARGET_LOW_HP.addToSerializables();
        IS_SOURCE_LOW_HP.addToSerializables();

    }
}
