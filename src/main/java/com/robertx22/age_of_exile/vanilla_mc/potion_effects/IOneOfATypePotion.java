package com.robertx22.age_of_exile.vanilla_mc.potion_effects;

public interface IOneOfATypePotion {

    default boolean isOneOfAKind() {
        return !getOneOfATypeType().isEmpty();
    }

    public String getOneOfATypeType();
}
