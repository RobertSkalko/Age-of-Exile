package com.robertx22.age_of_exile.uncommon.effectdatas.rework;

import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

import java.util.HashMap;

public class EventData {

    public static String NUMBER = "number";
    public static String ORIGINAL_VALUE = "original_value";
    public static String CANCELED = "canceled";
    public static String CRIT = "crit";
    public static String ELEMENT = "element";
    public static String ATTACK_TYPE = "attack_type";
    public static String ACCURACY = "accuracy";
    public static String ACCURACY_CRIT_FAILED = "accu_crit_roll_fail";
    public static String SPELL = "spell";

    private HashMap<String, WrappedFloat> floats = new HashMap<>();
    private HashMap<String, Boolean> bools = new HashMap<>();
    private HashMap<String, String> strings = new HashMap<>();

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

    public Elements getElement() {
        return Elements.valueOf(strings.getOrDefault(ELEMENT, Elements.Physical.name()));
    }

    public void setElement(Elements ele) {
        setString(ELEMENT, ele.name());
    }

    public String getString(String id) {
        return strings.getOrDefault(id, "");
    }

    public AttackType getAttackType() {
        return AttackType.valueOf(strings.getOrDefault(ATTACK_TYPE, AttackType.attack.name()));
    }

    public boolean isSpellEffect() {
        return Database.Spells()
            .isRegistered(getString(SPELL));
    }

    public void setString(String id, String str) {
        // careful about order here
        this.strings.put(id, str);
    }

}

