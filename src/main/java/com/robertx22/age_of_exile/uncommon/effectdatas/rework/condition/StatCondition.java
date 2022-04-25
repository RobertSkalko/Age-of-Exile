package com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;

import java.util.HashMap;

public abstract class StatCondition implements JsonExileRegistry<StatCondition>, IAutoGson<StatCondition> {

    public static StatCondition SERIALIZER = new RandomRollCondition();
    public static HashMap<String, StatCondition> SERIALIZERS = new HashMap<>();

    static {
        addSer(new RequireChargedAttack());
        addSer(new IsHealthAbovePercentCondition());
        addSer(new IsUndeadCondition());
        addSer(new IsHealthBellowPercentCondition());
        addSer(new EffectHasTagCondition());
        addSer(new IsInCombatCondition());
        addSer(new IsNotOnCooldownCondition());
        addSer(new IsUnderExileEffect());
        addSer(new EitherIsTrueCondition());
        addSer(new IsDayCondition());
        addSer(new IsSpellCondition());
        addSer(new SpellHasTagCondition());
        addSer(new IsBooleanTrueCondition());
        addSer(new RandomRollCondition());
        addSer(new ElementMatchesStat());
        addSer(new StringMatchesCondition());
        addSer(new WeaponTypeMatches());
        addSer(new LightLevelCondition());
    }

    static void addSer(StatCondition eff) {
        SERIALIZERS.put(eff.ser, eff);
    }

    public String id = "";
    public String ser = "";
    public Boolean is = null;

    public boolean getConditionBoolean() {
        return is == null ? true : is;
    }

    public StatCondition(String id, String ser) {
        this.ser = ser;
        this.id = id;
    }

    public StatCondition flipCondition() {
        this.is = false;
        this.id += "_is_false";
        return this;
    }

    public abstract boolean can(EffectEvent event, EffectSides statSource, StatData data, Stat stat);

    @Override
    public final StatCondition fromJson(JsonObject json) {
        String ser = json.get("ser")
            .getAsString();

        StatCondition t = null;
        try {
            t = GSON.fromJson(json, SERIALIZERS.get(ser)
                .getSerClass());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        t.onLoadedFromJson();
        return t;
    }

    @Override
    public int Weight() {
        return 1000;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.STAT_CONDITION;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public Class getClassForSerialization() {
        return null;
    }

    public abstract Class<? extends StatCondition> getSerClass();
}
