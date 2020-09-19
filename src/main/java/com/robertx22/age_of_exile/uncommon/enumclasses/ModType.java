package com.robertx22.age_of_exile.uncommon.enumclasses;

import net.minecraft.entity.attribute.EntityAttributeModifier;

import java.util.Locale;

public enum ModType {

    FLAT("flat", EntityAttributeModifier.Operation.ADDITION),
    LOCAL_INCREASE("local_increase", EntityAttributeModifier.Operation.MULTIPLY_BASE),
    GLOBAL_INCREASE("global_increase", EntityAttributeModifier.Operation.MULTIPLY_TOTAL);

    ModType(String id, EntityAttributeModifier.Operation op) {
        this.id = id;
        this.operation = op;
    }


    public EntityAttributeModifier.Operation operation;
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
