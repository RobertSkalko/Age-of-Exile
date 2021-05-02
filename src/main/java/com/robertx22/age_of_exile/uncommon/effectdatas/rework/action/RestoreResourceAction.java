package com.robertx22.age_of_exile.uncommon.effectdatas.rework.action;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.saveclasses.unit.ResourcesData;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectData;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class RestoreResourceAction extends StatEffect {

    String val_calc = "";
    ResourceType type = ResourceType.HEALTH;
    EffectSides side = EffectSides.Source;

    public RestoreResourceAction(String val_calc, ResourceType type) {
        super("restore_" + type.name() + "_" + val_calc, "restore_resource");
        this.val_calc = val_calc;
        this.type = type;
    }

    RestoreResourceAction() {
        super("", "restore_resource");
    }

    @Override
    public void activate(EffectData event, EffectSides statSource, StatData data, Stat stat) {

        int val = Database.ValueCalculations()
            .get(val_calc)
            .getCalculatedValue(event.getSide(statSource), Load.Unit(event.getSide(statSource))
                .getLevel());

        ResourcesData.Context ctx = new ResourcesData.Context(
            event.source,
            event.getSide(side),
            type,
            val,
            ResourcesData.Use.RESTORE
        );

        if (event.data.isSpellEffect()) {
            ctx.setSpell(event.data.getString(EventData.SPELL));
        }
        event.sourceData.modifyResource(ctx);
    }

    @Override
    public Class<? extends StatEffect> getSerClass() {
        return RestoreResourceAction.class;
    }

}
