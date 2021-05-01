package com.robertx22.age_of_exile.aoe_data.database.stat_conditions;

import com.robertx22.age_of_exile.aoe_data.DataHolder;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackType;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition.IsBooleanTrueCondition;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition.RandomRollCondition;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition.StatCondition;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition.StringMatchesCondition;

import java.util.stream.Collectors;

public class StatConditions implements ISlashRegistryInit {

    public static DataHolder<String, StatCondition> ATTACK_TYPE_MATCHES = new DataHolder<String, StatCondition>(
        AttackType.getAllUsed()
            .stream()
            .map(x -> x.id)
            .collect(Collectors.toList())
        , x -> new StringMatchesCondition(EventData.ATTACK_TYPE, x));

    public static StatCondition IF_CRIT = (StatCondition) new IsBooleanTrueCondition(EventData.CRIT).addToSerReturn();
    public static StatCondition IF_RANDOM_ROLL = (StatCondition) new RandomRollCondition().addToSerReturn();

    @Override
    public void registerAll() {

    }
}
