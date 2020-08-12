package com.robertx22.age_of_exile.uncommon.interfaces;

public interface IStatMultipleEffects extends IStatEffects {

    default IStatEffect getEffect() {
        return null;
    }

}
