package com.robertx22.mine_and_slash.uncommon.enumclasses;

import java.util.Locale;

public enum ModType {

    FLAT("flat"),
    LOCAL_INCREASE("local_increase"),
    GLOBAL_INCREASE("global_increase");

    ModType(String id) {
        this.id = id;

    }

    public String id;

    public boolean isFlat() {
        return this == FLAT;
    }

    public boolean isLocalIncrease() {
        return this == LOCAL_INCREASE;
    }

    public boolean isGlobalIncrease() {
        return this == GLOBAL_INCREASE;
    }

    public static ModType fromString(String str) {

        for (ModType type : ModType.values()) {
            if (type.id.toLowerCase(Locale.ROOT)
                .equals(str.toLowerCase(Locale.ROOT))) {
                return type;
            }
        }

        return valueOf(str);

    }

}
