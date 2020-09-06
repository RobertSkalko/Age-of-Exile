package com.robertx22.age_of_exile.database.data.spells.map_fields;

import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.saveclasses.spells.calc.ScalingStatCalc;

import java.util.HashMap;

public class MapField<T> implements IGUID {

    String id;

    public static HashMap<String, MapField> MAP = new HashMap<>();

    public static MapField<Float> RADIUS = new MapField<>("radius");
    public static MapField<Float> CHANCE = new MapField<>("chance");
    public static MapField<Float> PROJECTILE_SPEED = new MapField<>("projectile_speed");
    public static MapField<Float> PROJECTILE_COUNT = new MapField<>("projectile_count");
    public static MapField<String> STATUS_EFFECT = new MapField<>("status_effect");
    public static MapField<ScalingStatCalc> VALUE_CALCULATION = new MapField<>("value_calculation");

    public MapField(String id) {
        this.id = id;
    }

    private static MapField make(MapField field) {
        MAP.put(field.GUID(), field);
        return field;
    }

    public T get(HashMap<String, Object> map) {
        return (T) map.get(GUID());
    }

    public boolean isThere(HashMap<String, Object> map) {
        return map.containsKey(GUID());
    }

    @Override
    public String GUID() {
        return id;
    }
}
