package com.robertx22.age_of_exile.aoe_data.database.stats.base;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class AutoHashClass {

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return obj.hashCode() == this.hashCode();
    }
}
