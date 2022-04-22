package com.robertx22.library_of_exile.text_wrapper;

import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;

public class TextBuilder {

    private List<IHasText> list = new ArrayList<>();

    public static TextBuilder of() {
        return new TextBuilder();
    }

    public static TextBuilder space() {
        return new TextBuilder().append(" ");
    }

    public static TextBuilder empty() {
        return new TextBuilder().append("");
    }

    public TextBuilder append(IHasText txt) {
        list.add(txt);
        return this;
    }

    public TextBuilder append(String txt) {
        list.add(new ModStringText(txt));
        return this;
    }

    public TextBuilder append(ITextComponent txt) {
        list.add(new VanillaText(txt));
        return this;
    }

    public TextBuilder translated(String key) {
        list.add(new ModTranslatedText(key));
        return this;
    }

    public IFormattableTextComponent build() {
        IFormattableTextComponent txt = new StringTextComponent("");
        for (IHasText has : list) {
            ITextComponent part = has.getText();
            txt = txt.append(part);
        }
        return txt;
    }

}
