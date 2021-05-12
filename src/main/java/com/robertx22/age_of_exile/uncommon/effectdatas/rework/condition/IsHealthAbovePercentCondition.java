package com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;
import net.minecraft.entity.LivingEntity;

public class IsHealthAbovePercentCondition extends StatCondition {

    EffectSides side;
    int perc;

    public IsHealthAbovePercentCondition(String id, int percent, EffectSides side) {
        super(id, "is_hp_above");
        this.side = side;
        this.perc = percent;
    }

    IsHealthAbovePercentCondition() {
        super("", "is_hp_above");
    }

    @Override
    public boolean can(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        LivingEntity en = event.getSide(side);
        return perc < en.getHealth() / en.getMaxHealth() * 100;
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return IsHealthBellowPercentCondition.class;
    }

}
