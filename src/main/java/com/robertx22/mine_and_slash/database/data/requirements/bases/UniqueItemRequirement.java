package com.robertx22.mine_and_slash.database.data.requirements.bases;

public abstract class UniqueItemRequirement<T> extends BaseRequirement<T> {

    @Override
    public boolean meetsRequierment(GearRequestedFor requested) {

        if (requested.gearData.isUnique() == false) {
            return false;
        }

        return true;

    }

}
