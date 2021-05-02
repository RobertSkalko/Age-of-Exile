package com.robertx22.age_of_exile.uncommon.effectdatas.rework.action;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectData;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class SetBooleanEffect extends StatEffect {

    String bool_id = "";
    Boolean bool = true;

    public SetBooleanEffect(String boolId) {
        super("set_bool_" + boolId, "set_bool");
        this.bool_id = boolId;
    }

    SetBooleanEffect() {
        super("", "set_bool");
    }

    @Override
    public void activate(EffectData event, EffectSides statSource, StatData data, Stat stat) {
        event.data.setBoolean(bool_id, bool);
    }

    @Override
    public Class<? extends StatEffect> getSerClass() {
        return SetBooleanEffect.class;
    }

}
