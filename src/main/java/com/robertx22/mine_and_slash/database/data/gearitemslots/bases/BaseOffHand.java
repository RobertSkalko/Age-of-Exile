package com.robertx22.mine_and_slash.database.data.gearitemslots.bases;

public abstract class BaseOffHand extends BaseGearType {
    @Override
    public final int Weight() {
        return super.Weight() / 2;
    }

}
