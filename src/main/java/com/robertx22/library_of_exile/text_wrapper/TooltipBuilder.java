package com.robertx22.library_of_exile.text_wrapper;

import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TooltipBuilder {

    List<TextBuilder> list = new ArrayList<>();

    public static TooltipBuilder of() {
        return new TooltipBuilder();
    }

    public void add(TextBuilder txt) {
        list.add(txt);
    }

    public void addEmptyLine() {
        list.add(TextBuilder.empty());
    }

    // todo add remove duplicated lines

    public List<ITextComponent> build() {
        return list.stream()
            .map(x -> x.build())
            .collect(Collectors.toList());
    }

}
