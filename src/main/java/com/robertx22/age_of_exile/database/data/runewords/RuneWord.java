package com.robertx22.age_of_exile.database.data.runewords;

import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;

import java.util.ArrayList;
import java.util.List;

public class RuneWord implements IAutoGson<RuneWord>, JsonExileRegistry<RuneWord> {
    public static RuneWord SERIALIZER = new RuneWord();

    public String id = "";
    public String uniq_id = "";
    public List<String> runes = new ArrayList<>();
    public List<String> slots = new ArrayList<>();

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.RUNEWORDS;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public Class<RuneWord> getClassForSerialization() {
        return RuneWord.class;
    }

    @Override
    public int Weight() {
        return 1000;
    }
}
