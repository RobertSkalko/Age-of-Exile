package com.robertx22.age_of_exile.database.data.transc_affix;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TranscendentAffix implements IAutoLocName, JsonExileRegistry<TranscendentAffix>, IAutoGson<TranscendentAffix> {
    public static TranscendentAffix SERIALIZER = new TranscendentAffix();

    public transient String locname = "";
    public String id = "";
    public int weight = 1000;
    public List<StatModifier> stats = new ArrayList<>();
    public boolean can_on_wep = false;

    public static class Builder {

        public static void of(String id, String name, int weight, boolean canwep, StatModifier... stats) {

            TranscendentAffix affix = new TranscendentAffix();
            affix.id = id;
            affix.locname = name;
            affix.weight = weight;
            affix.can_on_wep = canwep;
            affix.stats = Arrays.asList(stats);

            affix.addToSerializables();
        }

    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.TranscendantAffixes;
    }

    @Override
    public String locNameLangFileGUID() {
        return "transcendent_affix." + id;
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }

    @Override
    public Class<TranscendentAffix> getClassForSerialization() {
        return TranscendentAffix.class;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.TRANSC_AFFIXES;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public int Weight() {
        return weight;
    }
}
