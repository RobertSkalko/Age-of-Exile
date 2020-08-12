package com.robertx22.age_of_exile.uncommon.interfaces;

import com.robertx22.age_of_exile.database.data.IGUID;

import java.util.Arrays;
import java.util.List;

public interface IStatEffects extends IGUID {

    IStatEffect getEffect();

    default List<IStatEffect> getEffects() {
        return Arrays.asList(getEffect());
    }
}
