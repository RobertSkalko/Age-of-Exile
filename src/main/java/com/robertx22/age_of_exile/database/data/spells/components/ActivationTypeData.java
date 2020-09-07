package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.spells.components.activated_on.ActivatedOn;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;

import java.util.HashMap;

public class ActivationTypeData {

    private String activation;

    HashMap<String, Object> map = new HashMap<>();

    public ActivatedOn.Activation getActivation() {
        return ActivatedOn.Activation.valueOf(activation);
    }

    public static ActivationTypeData createOnHit() {
        ActivationTypeData d = new ActivationTypeData();
        d.activation = ActivatedOn.Activation.ON_HIT.name();
        return d;
    }

    public static ActivationTypeData createOnCast() {
        ActivationTypeData d = new ActivationTypeData();
        d.activation = ActivatedOn.Activation.ON_CAST.name();
        return d;
    }

    public static ActivationTypeData createOnTick(int ticks) {
        ActivationTypeData d = new ActivationTypeData();
        d.activation = ActivatedOn.Activation.ON_TICK.name();
        d.map.put(MapField.TICK_RATE.GUID(), ticks);
        return d;
    }

    @Override
    public int hashCode() {
        return this.activation.hashCode();
    }

    @Override
    public boolean equals(Object obj) { // otherwise hashmaps dont work
        if (obj instanceof ActivationTypeData) {
            ActivationTypeData pt = (ActivationTypeData) obj;
            return pt.activation == this.activation;
        }
        return super.equals(obj);
    }
}
