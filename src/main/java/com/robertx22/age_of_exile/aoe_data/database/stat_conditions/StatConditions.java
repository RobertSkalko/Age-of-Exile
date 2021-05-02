package com.robertx22.age_of_exile.aoe_data.database.stat_conditions;

import com.robertx22.age_of_exile.aoe_data.DataHolder;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackType;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition.*;

import java.util.Arrays;

public class StatConditions implements ISlashRegistryInit {

    public static StatCondition IF_CRIT = new IsBooleanTrueCondition(EventData.CRIT);
    public static StatCondition IF_NOT_CRIT = new IsBooleanTrueCondition(EventData.CRIT).flipCondition();
    public static StatCondition IF_RANDOM_ROLL = new RandomRollCondition();
    public static StatCondition IS_SPELL = new IsSpellCondition();
    public static StatCondition ELEMENT_MATCH_STAT = new ElementMatchesStat();
    public static StatCondition IS_DAY = new IsDayCondition();
    public static StatCondition IS_NIGHT = new IsDayCondition().flipCondition();
    public static StatCondition CRIT_ROLL_DIDNT_FAIL = new IsBooleanTrueCondition(EventData.ACCURACY_CRIT_FAILED).flipCondition();

    public static DataHolder<AttackType, StatCondition> ATTACK_TYPE_MATCHES = new DataHolder<AttackType, StatCondition>(
        AttackType.values()
        , x -> new StringMatchesCondition(EventData.ATTACK_TYPE, x.name()));

    public static DataHolder<WeaponTypes, StatCondition> WEAPON_TYPE_MATCHES = new DataHolder<WeaponTypes, StatCondition>(
        WeaponTypes.values()
        , x -> new WeaponTypeMatches(x));

    public static StatCondition IS_ATTACK_OR_SPELL_ATTACK = new EitherIsTrueCondition(
        Arrays.asList(ATTACK_TYPE_MATCHES.get(AttackType.attack)
            .GUID(), ATTACK_TYPE_MATCHES.get(AttackType.spell)
            .GUID())
        , "is_attack_or_spell_attack"
    );

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

    }
}
