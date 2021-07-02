package com.robertx22.age_of_exile.aoe_data.database.stats.base;

import com.robertx22.age_of_exile.database.data.exile_effects.EffectType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.library_of_exile.registry.IGUID;

import java.util.Objects;

public class EffectCtx extends AutoHashClass implements IGUID {

    public EffectType type;
    public String effectId;
    public String id;
    public Elements element;
    public String locname;

    @Override
    public int hashCode() {
        return Objects.hash(effectId, id);
    }

    public EffectCtx(String id, String locname, int num, Elements element, EffectType type) {
        this.id = id;
        this.effectId = type.name() + "/" + num;
        this.element = element;
        this.locname = locname;
        this.type = type;
    }

    @Override
    public String GUID() {
        return effectId;
    }
}
