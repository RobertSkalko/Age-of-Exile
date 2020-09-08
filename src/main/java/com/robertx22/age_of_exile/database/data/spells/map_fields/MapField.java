package com.robertx22.age_of_exile.database.data.spells.map_fields;

import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.saveclasses.spells.calc.ValueCalculationData;

import java.util.HashMap;

public class MapField<T> implements IGUID {

    String id;

    public static HashMap<String, MapField> MAP = new HashMap<>();

    // double
    public static MapField<Double> RADIUS = make("radius");
    public static MapField<Double> HEIGHT = make("height");
    public static MapField<Double> TICK_RATE = make("tick_rate");
    public static MapField<Double> PARTICLE_COUNT = make("particle_count");
    public static MapField<Double> Y_RANDOM = make("y_rand");
    public static MapField<Double> LIFESPAN_TICKS = make("life_ticks");
    public static MapField<Double> DISTANCE = make("distance");
    public static MapField<Double> WIDTH = make("width");
    public static MapField<Double> CHANCE = make("chance");
    public static MapField<Double> PROJECTILE_SPEED = make("proj_speed");
    public static MapField<Double> PROJECTILE_COUNT = make("proj_count");
    public static MapField<Double> PROJECTILES_APART = make("proj_apart");
    public static MapField<Double> SELECTION_CHANCE = make("selection_chance");

    public static MapField<Double> VOLUME = make("volume");
    public static MapField<Double> PITCH = make("pitch");

    // string
    public static MapField<String> PROJECTILE_ENTITY = make("proj_en");
    public static MapField<String> STATUS_EFFECT = make("status_effect");
    public static MapField<String> ITEM = make("item");
    public static MapField<String> ELEMENT = make(new MapField<String>("element"));
    public static MapField<String> SELECTION_TYPE = make("selection_type");
    public static MapField<String> ENTITY_PREDICATE = make("en_predicate");
    public static MapField<String> PARTICLE_TYPE = make("particle_type");
    public static MapField<String> SOUND = make("sound");
    public static MapField<String> PARTICLE_SHAPE = make("shape");

    // boolean
    public static MapField<Boolean> GRAVITY = make("gravity");

    public static MapField<ValueCalculationData> VALUE_CALCULATION = make("value_calculation");

    public MapField(String id) {
        this.id = id;
    }

    private static <T> MapField<T> make(String f) {
        MapField<T> field = new MapField<T>(f);
        MAP.put(field.GUID(), field);
        return field;
    }

    private static <T> MapField<T> make(MapField<T> field) {
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

    public static void init() {

    }
}
