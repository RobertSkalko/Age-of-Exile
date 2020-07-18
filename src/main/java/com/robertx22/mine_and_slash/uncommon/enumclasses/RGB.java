package com.robertx22.mine_and_slash.uncommon.enumclasses;

import info.loenwind.autosave.annotations.Factory;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

@Storable
public class RGB {
    public RGB(int R, int G, int B) {
        this.R = R;
        this.G = G;
        this.B = B;
    }

    @Factory
    public RGB() {

    }

    @Store
    private int R;
    @Store
    private int G;
    @Store
    private int B;

    public float getR() {
        return R / 255.0F; // you need to divide by 255 for the color to work as intended..
    }

    public float getG() {
        return G / 255.0F;
    }

    public float getB() {
        return B / 255.0F;
    }

    public int getIntR() {
        return R;
    }

    public int getIntG() {
        return G;
    }

    public int getIntB() {
        return B;
    }
}
