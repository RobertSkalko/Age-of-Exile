package com.robertx22.age_of_exile.aoe_data.database.stats.base;

public abstract class AutoHashClass {

    @Override
    public abstract int hashCode();

    @Override
    public boolean equals(Object obj) {
        return obj.hashCode() == this.hashCode();
    }
}
