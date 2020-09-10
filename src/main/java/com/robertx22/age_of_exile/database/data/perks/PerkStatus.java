package com.robertx22.age_of_exile.database.data.perks;

public enum PerkStatus {

    CONNECTED(1), BLOCKED(2), LOCKED_UNDER_ACHIEV(3), POSSIBLE(4);

    public int order;

    PerkStatus(int order) {
        this.order = order;
    }

    public int getYOffset() {
        return order + 28 * (order - 1);
    }

}
