package com.robertx22.mine_and_slash.database.data.requirements.bases;

import com.robertx22.mine_and_slash.event_hooks.data_gen.ISerializablePart;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.ITooltipList;

public abstract class BaseRequirement<T> implements ISerializablePart<T>, ITooltipList {

    public abstract boolean meetsRequierment(GearRequestedFor requested);

}
