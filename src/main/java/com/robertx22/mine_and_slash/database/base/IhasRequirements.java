package com.robertx22.mine_and_slash.database.base;

import com.robertx22.mine_and_slash.database.data.requirements.Requirements;
import com.robertx22.mine_and_slash.database.data.requirements.bases.GearRequestedFor;

public interface IhasRequirements {

    public abstract Requirements requirements();

    public default boolean meetsRequirements(GearRequestedFor requested) {

        return requirements().satisfiesAllRequirements(requested);

    }

}
