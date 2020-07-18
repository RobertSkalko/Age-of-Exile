package com.robertx22.mine_and_slash.database.data.compatible_item;

import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.GearItemEnum;
import com.robertx22.mine_and_slash.uncommon.interfaces.IWeighted;

public class WeightedType implements IWeighted {

    public WeightedType(int weight, GearItemEnum type) {
        this.weight = weight;
        this.type = type;
    }

    public int weight;

    public GearItemEnum type;

    @Override
    public int Weight() {
        return weight;
    }

}
