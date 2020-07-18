package com.robertx22.mine_and_slash.uncommon.interfaces;

import com.robertx22.mine_and_slash.database.data.IGUID;

import java.util.Arrays;
import java.util.List;

public interface IStatEffects extends IGUID {

    IStatEffect getEffect();

    default List<IStatEffect> getEffects() {
        return Arrays.asList(getEffect());
    }
}
