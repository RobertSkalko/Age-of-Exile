package com.robertx22.library_of_exile.text_wrapper;

import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class ModTranslatedText implements IHasText {

    TranslationTextComponent text;

    public ModTranslatedText(String key) {
        this.text = new TranslationTextComponent(key);
    }

    @Override
    public IFormattableTextComponent getText() {
        return text;
    }

    public ModTranslatedText format(TextFormatting format) {
        text = (TranslationTextComponent) text.withStyle(format);
        return this;
    }
}
