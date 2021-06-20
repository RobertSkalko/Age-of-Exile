package com.robertx22.age_of_exile.aoe_data.base;

import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.library_of_exile.registry.IGUID;

public class DataGenKey<T> implements IGUID {

    String id;

    public DataGenKey(String id) {
        errorIfNotDevMode();
        this.id = id;
    }

    private void errorIfNotDevMode() {
        if (!MMORPG.RUN_DEV_TOOLS) {
            throw new RuntimeException("Do not use DataGenKeys outside of dev mode! These keys should only be used when generating data.");
        }
    }

    @Override
    public String GUID() {
        errorIfNotDevMode();
        return id;
    }
}
