package com.robertx22.age_of_exile.uncommon.effectdatas.rework;

import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.effectdatas.GiveOrTake;
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
    public static String THREAT_GEN_TYPE = "threat_gen_type";
    public static String EXILE_EFFECT = "exile_effect";
    public static String GIVE_OR_TAKE = "give_or_take";
    public static String STACKS = "stacks";

    public static String IS_DODGED = "is_dodged";
    public static String IS_BLOCKED = "is_blocked";
    public static String DISABLE_KNOCKBACK = "disable_knockback";
    public static String PENETRATION = "penetration";
    public static String IGNORE_RESIST = "ignore_resist";
    public static String SECONDS = "seconds";

    public static String CAST_TICKS = "cast_ticks";
    public static String EFFECT_DURATION_TICKS = "effect_duration_ticks";
    public static String COOLDOWN_TICKS = "cd_ticks";
    public static String MANA_COST = "mana_cost";
    public static String AREA_MULTI = "area";
    public static String PIERCE = "pierce";
    public static String PROJECTILE_SPEED_MULTI = "proj_speed";

    private boolean isFrozen = false;

    private HashMap<String, WrappedFloat> floats = new HashMap<>();
    private HashMap<String, Boolean> bools = new HashMap<>();
    private HashMap<String, String> strings = new HashMap<>();

    private void tryFreezeErrorMessage() {
        if (isFrozen && MMORPG.RUN_DEV_TOOLS) {
            try {
                //throw new RuntimeException("Event data frozen but code tried to modify it.");
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    public void setupNumber(String id, float num) {
        tryFreezeErrorMessage();
        if (floats.containsKey(id)) {
            throw new RuntimeException("Number is already setup: " + id);
        }

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
        tryFreezeErrorMessage();

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

    public Elements getThreatGenType() {
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

    public ExileEffect getExileEffect() {
        return Database.ExileEffects()
            .get(getString(EXILE_EFFECT));
    }

    public boolean hasExileEffect() {
        return Database.ExileEffects()
            .isRegistered(getString(EXILE_EFFECT));
    }

    public GiveOrTake getGiveOrTake() {
        return GiveOrTake.valueOf(strings.getOrDefault(GIVE_OR_TAKE, GiveOrTake.give.name()));
    }

    public ResourceType getResourceType() {
        return ResourceType.valueOf(strings.getOrDefault(RESOURCE_TYPE, ResourceType.health.name()));
    }

    public RestoreType getRestoreType() {
        return RestoreType.valueOf(strings.getOrDefault(RESTORE_TYPE, RestoreType.heal.name()));
    }

    public boolean isSpellEffect() {
        return Database.Spells()
            .isRegistered(getString(SPELL));
    }

    public boolean isBasicAttack() {
        return getBoolean(IS_BASIC_ATTACK);
    }

    public boolean isCrit() {
        return getBoolean(CRIT);
    }

    public void setString(String id, String str) {
        tryFreezeErrorMessage();
        // careful about order here
        this.strings.put(id, str);
    }

    public void freeze() {
        this.isFrozen = true;
    }
}

