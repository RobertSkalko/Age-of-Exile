package com.robertx22.age_of_exile.database.data.spells.components.cast_actions;

import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.database.data.spells.components.BaseFieldNeeder;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.contexts.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;

import java.util.List;

public abstract class CastAction extends BaseFieldNeeder implements IGUID {

    public CastAction(List<MapField> requiredPieces) {
        super(requiredPieces);
    }

    public abstract void onCast(SpellCtx ctx, MapHolder data);
}



