package com.robertx22.age_of_exile.database.data.requirements.bases;

public abstract class UniqueItemRequirement<T> extends BaseRequirement<T> {

    @Override
    public boolean meetsRequierment(GearRequestedFor requested) {

        if (requested.gearData.isUnique() == false) {
            return false;
        }

        return true;

    }

}
