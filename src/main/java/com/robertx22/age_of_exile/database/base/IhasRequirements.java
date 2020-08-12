package com.robertx22.age_of_exile.database.base;

import com.robertx22.age_of_exile.database.data.requirements.Requirements;
import com.robertx22.age_of_exile.database.data.requirements.bases.GearRequestedFor;

public interface IhasRequirements {

    public abstract Requirements requirements();

    public default boolean meetsRequirements(GearRequestedFor requested) {

        return requirements().satisfiesAllRequirements(requested);

    }

}
