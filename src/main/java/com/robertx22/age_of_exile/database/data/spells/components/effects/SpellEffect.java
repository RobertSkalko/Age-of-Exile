package com.robertx22.age_of_exile.database.data.spells.components.effects;

import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.database.data.spells.components.BaseFieldNeeder;
import com.robertx22.age_of_exile.database.data.spells.contexts.BaseSpellCtx;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;

import java.util.HashMap;
import java.util.List;

public abstract class SpellEffect extends BaseFieldNeeder implements IGUID {

    public SpellEffect(List<MapField> requiredPieces) {
        super(requiredPieces);
    }

    public abstract void tryActivate(BaseSpellCtx ctx, HashMap<String, Object> map);
}
