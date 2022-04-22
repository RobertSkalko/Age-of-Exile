package com.robertx22.age_of_exile.database.all_keys.base;

import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.registry.ExileDB;

public class RunewordKey extends DataKey<RuneWord> {
    public RunewordKey(String id) {
        super(id);
    }

    @Override
    public RuneWord getFromRegistry() {
        return ExileDB.RuneWords()
            .get(id);
    }

    @Override
    public RuneWord getFromDataGen() {
        return null;
    }

    @Override
    public Class getDataClass() {
        return RuneWord.class;
    }
}
