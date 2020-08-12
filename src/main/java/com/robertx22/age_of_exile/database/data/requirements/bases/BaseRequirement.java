package com.robertx22.age_of_exile.database.data.requirements.bases;

import com.robertx22.age_of_exile.datapacks.bases.ISerializablePart;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltipList;

public abstract class BaseRequirement<T> implements ISerializablePart<T>, ITooltipList {

    public abstract boolean meetsRequierment(GearRequestedFor requested);

}
