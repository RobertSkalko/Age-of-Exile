package com.robertx22.age_of_exile.uncommon.effectdatas.rework.action;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectData;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class SetDataNumberAction extends StatEffect {

    public String num_id = "";

    public SetDataNumberAction(String num_id) {
        super("set_data_num_" + num_id, "set_data_number");
        this.num_id = num_id;
    }

    public SetDataNumberAction() {
        super("", "set_data_number");
    }

    @Override
    public void activate(EffectData event, EffectSides statSource, StatData data, Stat stat) {
        event.data.getNumber(num_id).number = data.getAverageValue();
    }

    @Override
    public Class<? extends StatEffect> getSerClass() {
        return SetDataNumberAction.class;
    }
}
