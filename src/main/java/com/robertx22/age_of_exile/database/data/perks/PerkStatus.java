package com.robertx22.age_of_exile.database.data.perks;

public enum PerkStatus {

    CONNECTED(1, 1), BLOCKED(2, 39), LOCKED_UNDER_ACHIEV(3, 76), POSSIBLE(4, 113);

    public int order;

    private int yoff;

    PerkStatus(int order, int yoff) {
        this.order = order;
        this.yoff = yoff;
    }

    public int getYOffset() {
        return yoff;
    }

}
