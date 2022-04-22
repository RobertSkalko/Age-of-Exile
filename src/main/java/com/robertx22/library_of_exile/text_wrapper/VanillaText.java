package com.robertx22.library_of_exile.text_wrapper;

import net.minecraft.util.text.ITextComponent;

public class VanillaText implements IHasText {

    public ITextComponent txt;

    public VanillaText(ITextComponent txt) {
        this.txt = txt;
    }

    @Override
    public ITextComponent getText() {
        return txt;
    }
}
