package com.robertx22.mine_and_slash.uncommon.interfaces;

public interface IStatMultipleEffects extends IStatEffects {

    default IStatEffect getEffect() {
        return null;
    }

}
