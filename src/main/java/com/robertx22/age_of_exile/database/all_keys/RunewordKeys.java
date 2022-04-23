package com.robertx22.age_of_exile.database.all_keys;

import com.robertx22.age_of_exile.database.all_keys.base.RunewordKey;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneType;

import java.util.Arrays;

public interface RunewordKeys {

    RunewordKey ARIA_OF_DISASTER = new RunewordKey("air_disaster", Arrays.asList(RuneType.ANO, RuneType.ORU, RuneType.DOS));

    public static void init() {

    }
}
