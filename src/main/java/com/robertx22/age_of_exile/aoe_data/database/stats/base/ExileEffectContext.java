package com.robertx22.age_of_exile.aoe_data.database.stats.base;

import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class ExileEffectContext {

    public String effectId;
    public String id;
    public Elements element;
    public String locname;

    public ExileEffectContext(String id, String effectId, Elements element, String locname) {
        this.id = id;
        this.effectId = effectId;
        this.element = element;
        this.locname = locname;
    }
}
