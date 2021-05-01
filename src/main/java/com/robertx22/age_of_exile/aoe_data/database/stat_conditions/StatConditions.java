package com.robertx22.age_of_exile.aoe_data.database.stat_conditions;

import com.robertx22.age_of_exile.aoe_data.DataHolder;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackType;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition.*;

public class StatConditions implements ISlashRegistryInit {

    public static DataHolder<AttackType, StatCondition> ATTACK_TYPE_MATCHES = new DataHolder<AttackType, StatCondition>(
        AttackType.values()
        , x -> new StringMatchesCondition(EventData.ATTACK_TYPE, x.name()));

    public static StatCondition IF_CRIT = new IsBooleanTrueCondition(EventData.CRIT);
    public static StatCondition IF_RANDOM_ROLL = new RandomRollCondition();
    public static StatCondition IS_SPELL = new IsSpellCondition();

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

    }
}
