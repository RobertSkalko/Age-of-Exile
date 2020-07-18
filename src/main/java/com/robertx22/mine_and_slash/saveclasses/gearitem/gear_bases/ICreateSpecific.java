package com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases;

import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;

public interface ICreateSpecific<T> {

    public void create(GearItemData gear, T t);

}
