package com.robertx22.age_of_exile.database.data.spells.activated_on;

import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.database.data.spells.components.BaseFieldNeeder;
import com.robertx22.age_of_exile.database.data.spells.contexts.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;

import java.util.HashMap;
import java.util.List;

public abstract class ActivatedOn extends BaseFieldNeeder implements IGUID {

    public enum ActivationType {
        ON_CAST, ON_TICK, ON_HIT,
    }

    public ActivatedOn(List<MapField> requiredPieces) {
        super(requiredPieces);
    }

    public abstract boolean canActivate(SpellCtx ctx, HashMap<String, Object> map);
}

