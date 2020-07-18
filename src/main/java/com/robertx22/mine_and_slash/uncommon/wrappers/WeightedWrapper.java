package com.robertx22.mine_and_slash.uncommon.wrappers;

import com.robertx22.mine_and_slash.uncommon.interfaces.IWeighted;

public class WeightedWrapper<T> implements IWeighted {
    int weight;
    public T object;

    public WeightedWrapper(T object, int weight) {
        this.weight = weight;
        this.object = object;
    }

    @Override
    public int Weight() {
        return weight;
    }
}
