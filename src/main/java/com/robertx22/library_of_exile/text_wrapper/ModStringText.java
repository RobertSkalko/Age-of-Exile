package com.robertx22.library_of_exile.text_wrapper;

import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class ModStringText implements IHasText {

    StringTextComponent text;

    public ModStringText(String str) {
        this.text = new StringTextComponent(str);
    }

    @Override
    public IFormattableTextComponent getText() {
        return text;
    }

    public ModStringText format(TextFormatting format) {
        text = (StringTextComponent) text.withStyle(format);
        return this;
    }
}
