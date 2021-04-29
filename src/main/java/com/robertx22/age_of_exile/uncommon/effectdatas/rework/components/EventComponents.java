package com.robertx22.age_of_exile.uncommon.effectdatas.rework.components;

import java.util.HashMap;

public class EventComponents {

    public static String SPELL_ID = "spell";
    public static String ELEMENT_ID = "element";

    private HashMap<String, EventComponent> map = new HashMap<>();

    public void add(EventComponent comp) {
        map.put(comp.GUID(), comp);
    }

    public boolean hasSpellComponent() {
        return map.containsKey(SPELL_ID);
    }

    public SpellEventComponent getSpellComponent() {

        return (SpellEventComponent) map.get(SPELL_ID);
    }

    private void errorIfNull(String id) {
        if (!map.containsKey(id)) {
            throw new RuntimeException("Trying to get null component!");
        }
    }
}
