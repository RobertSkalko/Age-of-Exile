package com.robertx22.age_of_exile.uncommon.effectdatas.rework;

import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;

import java.util.HashMap;

public class EventData {

    public static String NUMBER = "number";
    public static String CANCELED = "canceled";
    public static String CRIT = "crit";
    public static String ELEMENT = "element";
    public static String ATTACK_TYPE = "attack_type";
    public static String RESOURCE_TYPE = "resource_type";
    public static String RESTORE_TYPE = "restore_type";
    public static String ACCURACY = "accuracy";
    public static String ACCURACY_CRIT_FAILED = "accu_crit_roll_fail";
    public static String SPELL = "spell";
    public static String WEAPON_TYPE = "weapon_type";
    public static String IS_BASIC_ATTACK = "is_basic_atk";
    public static String STYLE = "style";

    public static String IS_DODGED = "is_dodged";
    public static String IS_BLOCKED = "is_blocked";
    public static String DISABLE_KNOCKBACK = "disable_knockback";
    public static String PENETRATION = "penetration";
    public static String IGNORE_RESIST = "ignore_resist";
    public static String SECONDS = "seconds";

    private HashMap<String, WrappedFloat> floats = new HashMap<>();
    private HashMap<String, Boolean> bools = new HashMap<>();
    private HashMap<String, String> strings = new HashMap<>();

    public void setupNumber(String id, float num) {
        this.getNumber(id).number = num;
        this.getOriginalNumber(id).number = num;
    }

    // todo if this doesnt create new one, all getters just modify the empty, woops.
    public WrappedFloat getNumber(String id) {
        if (!floats.containsKey(id)) {
            floats.put(id, new WrappedFloat(0));
        }
        return floats.get(id);
    }

    public WrappedFloat getOriginalNumber(String id) {
        return getNumber("original_" + id);
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

    public boolean isDodged() {
        return getBoolean(IS_DODGED);
    }

    public Elements getElement() {
        return Elements.valueOf(strings.getOrDefault(ELEMENT, Elements.Physical.name()));
    }

    public WeaponTypes getWeaponType() {
        return WeaponTypes.valueOf(strings.getOrDefault(WEAPON_TYPE, WeaponTypes.none.name()));
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

    public PlayStyle getStyle() {
        return PlayStyle.valueOf(strings.getOrDefault(STYLE, PlayStyle.melee.name()));
    }

    public ResourceType getResourceType() {
        return ResourceType.valueOf(strings.getOrDefault(RESOURCE_TYPE, ResourceType.health.name()));
    }

    public boolean isSpellEffect() {
        return Database.Spells()
            .isRegistered(getString(SPELL));
    }

    public boolean isBasicAttack() {
        return getBoolean(IS_BASIC_ATTACK);
    }

    public void setString(String id, String str) {
        // careful about order here
        this.strings.put(id, str);
    }

}

