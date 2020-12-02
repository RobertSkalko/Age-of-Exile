package com.robertx22.age_of_exile.uncommon.interfaces;

public interface IStatMultipleEffects extends IExtraStatEffect {

    default IStatEffect getEffect() {
        return null;
    }

}
