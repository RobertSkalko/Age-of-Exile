package com.robertx22.age_of_exile.database.data.spells.components.conditions;

import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.database.data.spells.components.BaseFieldNeeder;
import com.robertx22.age_of_exile.database.data.spells.contexts.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;

import java.util.HashMap;
import java.util.List;

public abstract class EffectCondition extends BaseFieldNeeder implements IGUID {

    public EffectCondition(List<MapField> requiredPieces) {
        super(requiredPieces);
    }

    public abstract boolean canActivate(SpellCtx ctx, HashMap<String, Object> map);
}

