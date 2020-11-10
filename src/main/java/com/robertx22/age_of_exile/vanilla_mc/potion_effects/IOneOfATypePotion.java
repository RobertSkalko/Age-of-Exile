package com.robertx22.age_of_exile.vanilla_mc.potion_effects;

public interface IOneOfATypePotion {

    public enum Type {
        DIVINE_BUFF,
        FOOD_BUFF;
    }

    public Type getOneOfATypeType();
}
