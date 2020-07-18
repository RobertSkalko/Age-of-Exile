package com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases;

import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;

public interface IRerollable {

    public abstract void RerollFully(GearItemData gear);

    public abstract void RerollNumbers(GearItemData gear);

}
