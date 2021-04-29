package com.robertx22.age_of_exile.uncommon.effectdatas.rework;

import java.util.HashMap;

public class EventData {

    public static String NUMBER = "number";
    public static String ORIGINAL_VALUE = "original_value";
    public static String CANCELED = "canceled";

    private HashMap<String, WrappedFloat> floats = new HashMap<>();
    private HashMap<String, Boolean> bools = new HashMap<>();

    // todo if this doesnt create new one, all getters just modify the empty, woops.
    public WrappedFloat getNumber(String id) {

        if (!floats.containsKey(id)) {
            floats.put(id, new WrappedFloat(0));
        }
        return floats.get(id);
    }

    public boolean getBoolean(String id) {
        return bools.getOrDefault(id, false);
    }

    public void setBoolean(String id, Boolean bool) {
        bools.put(id, bool);
    }

    public float getNumber() {
        return getNumber(NUMBER).number;
    }

    public boolean isCanceled() {
        return getBoolean(CANCELED);
    }
}

