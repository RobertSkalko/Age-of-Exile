package com.robertx22.age_of_exile.aoe_data.base_gear_types;

import com.robertx22.age_of_exile.aoe_data.exile_effects.ExileEffectBuilder;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectType;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;

public class BaseGearBuilder {
    private BaseGearType effect = new BaseGearType();

    public static ExileEffectBuilder of(String id, String locname, EffectType type) {
        ExileEffectBuilder b = new ExileEffectBuilder();
        b.effect.type = type;
        b.effect.id = id;
        b.effect.locName = locname;
        return b;
    }
}
