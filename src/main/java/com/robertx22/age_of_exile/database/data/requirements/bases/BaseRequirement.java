package com.robertx22.age_of_exile.database.data.requirements.bases;

import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.library_of_exile.registry.serialization.ISerializablePart;

public abstract class BaseRequirement<T> implements ISerializablePart<T>, ITooltipList {

    public abstract boolean meetsRequierment(GearRequestedFor requested);

}
