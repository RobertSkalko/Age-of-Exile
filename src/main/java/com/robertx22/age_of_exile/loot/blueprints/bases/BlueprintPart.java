package com.robertx22.age_of_exile.loot.blueprints.bases;

import com.robertx22.age_of_exile.loot.blueprints.ItemBlueprint;
import com.robertx22.age_of_exile.mmorpg.MMORPG;

public abstract class BlueprintPart<T, C extends ItemBlueprint> {

    private T part;

    C blueprint;

    public boolean canBeNull = false;

    public BlueprintPart(C blueprint) {
        this.blueprint = blueprint;
    }

    protected abstract T generateIfNull();

    public void set(T t) {

        if (part == null) {
            part = t;
        } else {
            MMORPG.devToolsErrorLog("Do not override an already set and created part!");
        }
    }

    public void override(T t) {
        part = t;
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
