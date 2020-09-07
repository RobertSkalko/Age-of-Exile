package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.spells.components.activated_on.ActivatedOn;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;

import java.util.HashMap;

public class ActivationTypeData {

    ActivatedOn.ActivationType activationType;
    HashMap<String, Object> map;

    public static ActivationTypeData createOnHit() {
        ActivationTypeData d = new ActivationTypeData();
        d.activationType = ActivatedOn.ActivationType.ON_HIT;
        return d;
    }

    public static ActivationTypeData createOnCast() {
        ActivationTypeData d = new ActivationTypeData();
        d.activationType = ActivatedOn.ActivationType.ON_CAST;
        return d;
    }

    public static ActivationTypeData createOnTick(int ticks) {
        ActivationTypeData d = new ActivationTypeData();
        d.activationType = ActivatedOn.ActivationType.ON_TICK;
        d.map.put(MapField.TICK_RATE.GUID(), ticks);
        return d;
    }

    @Override
    public int hashCode() {
        return this.activationType.hashCode();
    }

    @Override
    public boolean equals(Object obj) { // otherwise hashmaps dont work
        if (obj instanceof ActivationTypeData) {
            ActivationTypeData pt = (ActivationTypeData) obj;
            return pt.activationType == this.activationType;
        }
        return super.equals(obj);
    }
}
