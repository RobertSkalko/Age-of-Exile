package com.robertx22.mine_and_slash.loot.blueprints.bases;

import com.robertx22.mine_and_slash.loot.blueprints.ItemBlueprint;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;

public abstract class BlueprintPart<T> {

    private T part;

    ItemBlueprint blueprint;

    public boolean canBeNull = false;

    public BlueprintPart(ItemBlueprint blueprint) {
        this.blueprint = blueprint;
    }

    protected abstract T generateIfNull();

    public void forceSet(T t) {
        part = t;
    }

    public void trySet(T t) {
        if (part == null) {
            part = t;
        }
    }

    public void set(T t) {

        if (part == null) {
            part = t;
        } else {
            MMORPG.devToolsErrorLog("Do not override an already set and created part!");
        }
    }

    public boolean isGenerated() {
        return part != null;
    }

    public T get() {

        if (part == null) {
            part = generateIfNull();
        }

        if (!canBeNull) {
            if (part == null) {
                MMORPG.devToolsErrorLog("Item is null even after being generated!");
            }
        }

        return part;
    }

}
