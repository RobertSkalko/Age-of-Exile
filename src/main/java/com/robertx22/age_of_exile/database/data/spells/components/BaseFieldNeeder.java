package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;

import java.util.List;

public class BaseFieldNeeder {

    List<MapField> requiredPieces;

    public BaseFieldNeeder(List<MapField> requiredPieces) {
        this.requiredPieces = requiredPieces;
    }

}
