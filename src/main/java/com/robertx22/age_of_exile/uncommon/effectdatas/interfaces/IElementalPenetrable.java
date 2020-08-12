package com.robertx22.age_of_exile.uncommon.effectdatas.interfaces;

public interface IElementalPenetrable extends IElementalEffect, IPenetrable {

    public abstract void addElementalPenetration(int val);

    public abstract int GetElementalPenetration();

}
