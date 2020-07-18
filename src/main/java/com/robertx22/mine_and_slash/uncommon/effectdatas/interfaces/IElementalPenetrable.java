package com.robertx22.mine_and_slash.uncommon.effectdatas.interfaces;

public interface IElementalPenetrable extends IElementalEffect, IPenetrable {

    public abstract void addElementalPenetration(int val);

    public abstract int GetElementalPenetration();

}
