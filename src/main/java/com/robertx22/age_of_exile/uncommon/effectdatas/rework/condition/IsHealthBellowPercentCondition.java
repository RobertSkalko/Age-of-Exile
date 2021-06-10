package com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;
import net.minecraft.entity.LivingEntity;

public class IsHealthBellowPercentCondition extends StatCondition {

    EffectSides side;
    int perc;

    public IsHealthBellowPercentCondition(String id, int percent, EffectSides side) {
        super(id, "is_hp_under");
        this.side = side;
        this.perc = percent;
    }

    IsHealthBellowPercentCondition() {
        super("", "is_hp_under");
    }

    @Override
    public boolean can(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        LivingEntity en = event.getSide(side);
        return perc > en.getHealth() / en.getMaxHealth() * 100;
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return IsHealthBellowPercentCondition.class;
    }

}
