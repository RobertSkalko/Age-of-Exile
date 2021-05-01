package com.robertx22.age_of_exile.aoe_data.database.stats.base;

import com.robertx22.age_of_exile.database.data.exile_effects.EffectType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class EffectCtx {

    public EffectType type;
    public String effectId;
    public String id;
    public Elements element;
    public String locname;

    public EffectCtx(String id, String locname, int num, Elements element, EffectType type) {
        this.id = id;
        this.effectId = type.name() + "/" + num;
        this.element = element;
        this.locname = locname;
        this.type = type;
    }
}
