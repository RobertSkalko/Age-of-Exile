package com.robertx22.age_of_exile.database.data.spells.map_fields;

import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.saveclasses.spells.calc.ValueCalculationData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;

import java.util.HashMap;

public class MapField<T> implements IGUID {

    String id;

    public static HashMap<String, MapField> MAP = new HashMap<>();

    public static MapField<Double> RADIUS = make("radius");
    public static MapField<Double> TICK_RATE = make("tick_rate");
    public static MapField<Double> LIFESPAN_TICKS = make("lifespan_ticks");
    public static MapField<Double> DISTANCE = make("distance");
    public static MapField<Double> WIDTH = make("width");
    public static MapField<Double> CHANCE = make("chance");
    public static MapField<Double> PROJECTILE_SPEED = make("projectile_speed");
    public static MapField<Double> PROJECTILE_COUNT = make("projectile_count");
    public static MapField<String> PROJECTILE_ENTITY = make("projectile_entity");
    public static MapField<String> STATUS_EFFECT = make("status_effect");
    public static MapField<Elements> ELEMENT = make("element");
    public static MapField<EntityFinder.SelectionType> SELECTION_TYPE = make("selection_type");
    public static MapField<EntityFinder.EntityPredicate> ENTITY_PREDICATE = make("entity_predicate");

    public static MapField<ValueCalculationData> VALUE_CALCULATION = make("value_calculation");

    public MapField(String id) {
        this.id = id;
    }

    private static <T> MapField<T> make(String f) {
        MapField<T> field = new MapField<T>(f);
        MAP.put(field.GUID(), field);
        return field;
    }

    private static <T> MapField<T> make(MapField<T> field, String f) {
        MAP.put(field.GUID(), field);
        return field;
    }

    public boolean has(HashMap<String, Object> map) {
        return map.containsKey(GUID()) && map.get(GUID()) != null;
    }

    public T get(HashMap<String, Object> map) {
        return (T) map.get(GUID());
    }

    public T get(MapHolder data) {
        return data.get(this);
    }

    public boolean isThere(HashMap<String, Object> map) {
        return map.containsKey(GUID());
    }

    @Override
    public String GUID() {
        return id;
    }
}
