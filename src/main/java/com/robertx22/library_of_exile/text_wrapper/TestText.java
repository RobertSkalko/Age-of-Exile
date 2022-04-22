package com.robertx22.library_of_exile.text_wrapper;

import net.minecraft.util.text.TextFormatting;

public class TestText {

    public static void test() {

        TextBuilder.of()
            .append(new ModStringText("test").format(TextFormatting.GREEN))
            .build();

    }
}
