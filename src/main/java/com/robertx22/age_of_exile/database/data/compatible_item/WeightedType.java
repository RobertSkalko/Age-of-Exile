package com.robertx22.age_of_exile.database.data.compatible_item;

import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.GearItemEnum;
import com.robertx22.library_of_exile.registry.IWeighted;

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
