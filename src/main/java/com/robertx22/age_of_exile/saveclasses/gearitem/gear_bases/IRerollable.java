package com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases;

import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;

public interface IRerollable {

    public abstract void RerollFully(GearItemData gear);

    public abstract void RerollNumbers(GearItemData gear);

}
