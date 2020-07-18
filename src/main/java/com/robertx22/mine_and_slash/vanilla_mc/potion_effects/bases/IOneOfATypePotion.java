package com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases;

public interface IOneOfATypePotion {

    public enum Type {
        DIVINE_BUFF
    }

    public Type getOneOfATypeType();
}
