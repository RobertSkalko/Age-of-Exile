package com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases;

public interface IOneOfATypePotion {

    public enum Type {
        DIVINE_BUFF
    }

    public Type getOneOfATypeType();
}
