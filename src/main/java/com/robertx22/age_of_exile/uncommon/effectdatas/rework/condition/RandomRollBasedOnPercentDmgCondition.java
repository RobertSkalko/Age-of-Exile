package com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;
import com.robertx22.age_of_exile.uncommon.utilityclasses.HealthUtils;
import com.robertx22.library_of_exile.utils.RandomUtils;

public class RandomRollBasedOnPercentDmgCondition extends StatCondition {

    public RandomRollBasedOnPercentDmgCondition() {
        super("random_roll_based_on_percent_damage_done", "random_roll_based_on_percent_damage_done");
    }

    @Override
    public boolean can(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        float chance = data.getValue(); // base chance

        float maxHp = HealthUtils.getMaxHealth(event.target);

        float multi = event.data.getNumber() / maxHp;

        chance *= multi;

        boolean can = RandomUtils.roll(chance);

        return can;
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return RandomRollBasedOnPercentDmgCondition.class;
    }

}

