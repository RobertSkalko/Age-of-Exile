package com.robertx22.age_of_exile.uncommon.effectdatas.rework.action;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

import java.util.HashMap;

public abstract class StatEffect implements ISerializedRegistryEntry<StatEffect>, IAutoGson<StatEffect> {

    public static StatEffect SERIALIZER = new SetBooleanEffect();
    public static HashMap<String, StatEffect> SERIALIZERS = new HashMap<>();

    static {

        addSer(new IncreaseNumberPerCurseOnTarget());
        addSer(new DoubleDamageAction());
        addSer(new RemoveExileEffectAction());
        addSer(new MultiplyNumberByPercentEffect());
        addSer(new ReflectDamageAction());
        addSer(new GiveExileStatusInRadius());
        addSer(new DecreaseNumberByPercentEffect());
        addSer(new AddToNumberEffect());
        addSer(new SetDataNumberAction());
        addSer(new SetBooleanEffect());
        addSer(new IncreaseNumberByPercentEffect());
        addSer(new RestoreResourceAction());
        addSer(new GiveExileStatusEffect());
    }

    static void addSer(StatEffect eff) {
        SERIALIZERS.put(eff.ser, eff);
    }

    public String id = "";
    public String ser = "";

    public StatEffect(String id, String ser) {
        this.id = id;
        this.ser = ser;
    }

    public abstract void activate(EffectEvent event, EffectSides statSource, StatData data, Stat stat);

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.STAT_EFFECT;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public final StatEffect fromJson(JsonObject json) {
        String ser = json.get("ser")
            .getAsString();

        StatEffect t = GSON.fromJson(json, SERIALIZERS.get(ser)
            .getSerClass());
        t.onLoadedFromJson();
        return t;
    }

    @Override
    public Class<StatEffect> getClassForSerialization() {
        return null;
    }

    public abstract Class<? extends StatEffect> getSerClass();
}

