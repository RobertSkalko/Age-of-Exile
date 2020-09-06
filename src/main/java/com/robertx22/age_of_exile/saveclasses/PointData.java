package com.robertx22.age_of_exile.saveclasses;

import info.loenwind.autosave.annotations.Factory;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

@Storable
public class PointData {
    @Store
    public int x;
    @Store
    public int y;

    @Factory
    protected PointData() {

    }

    public PointData(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        long bits = java.lang.Double.doubleToLongBits(x);
        bits ^= java.lang.Double.doubleToLongBits(y) * 31;
        return (((int) bits) ^ ((int) (bits >> 32)));
    }

    @Override
    public boolean equals(Object obj) { // otherwise hashmaps dont work
        if (obj instanceof PointData) {
            PointData pt = (PointData) obj;
            return (x == pt.x) && (y == pt.y);
        }
        return super.equals(obj);
    }
}
