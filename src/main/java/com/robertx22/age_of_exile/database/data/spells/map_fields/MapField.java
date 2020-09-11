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
    public static MapField<Double> COUNT = make("count");
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
    public static MapField<Double> PUSH_STRENGTH = make("push_str");
    public static MapField<Double> POTION_DURATION = make("potion_dur");
    public static MapField<Double> POTION_STRENGTH = make("potion_str");

    public static MapField<Double> VOLUME = make("volume");
    public static MapField<Double> PITCH = make("pitch");

    // string
    public static MapField<String> PROJECTILE_ENTITY = make("proj_en");
    public static MapField<String> ITEM = make("item");
    public static MapField<String> ELEMENT = make(new MapField<String>("element"));
    public static MapField<String> SELECTION_TYPE = make("selection_type");
    public static MapField<String> ENTITY_PREDICATE = make("en_predicate");
    public static MapField<String> PARTICLE_TYPE = make("particle_type");
    public static MapField<String> SOUND = make("sound");
    public static MapField<String> PARTICLE_SHAPE = make("shape");
    public static MapField<String> EXILE_POTION_ID = make("exile_potion_id");
    public static MapField<String> POTION_ID = make("potion_id");
    public static MapField<String> PERK = make("perk");
    public static MapField<String> POTION_ACTION = make("potion_action");
    public static MapField<String> BLOCK = make("block");
    public static MapField<String> ENTITY_NAME = make("entity_name");
    public static MapField<String> PUSH_WAY = make("push_way");
    public static MapField<String> POS_SOURCE = make("pos_source");
    public static MapField<String> SHOOT_DIRECTION = make("shoot_way");
    public static MapField<String> SPELL_MODIFIER = make("spell_mod");

    // boolean
    public static MapField<Boolean> GRAVITY = make("gravity");
    public static MapField<Boolean> EXPIRE_ON_HIT = make("expire_on_hit");

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
