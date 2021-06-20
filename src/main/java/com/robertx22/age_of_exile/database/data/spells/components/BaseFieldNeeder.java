package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.library_of_exile.registry.IGUID;

import java.util.List;

public abstract class BaseFieldNeeder implements IGUID {

    List<MapField> requiredPieces;

    public BaseFieldNeeder(List<MapField> requiredPieces) {
        this.requiredPieces = requiredPieces;
    }

    public void validate(MapHolder holder) {
        if (holder.type.equals(GUID())) {
            assert requiredPieces.stream()
                .allMatch(x -> holder.get(x) != null);
        }
    }

}
