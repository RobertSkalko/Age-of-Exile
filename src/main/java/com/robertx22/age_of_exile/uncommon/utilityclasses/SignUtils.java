package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.world_of_exile.mixin_ducks.SignDuck;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;

public class SignUtils {

    public static boolean has(String id, SignTileEntity sign) {
        return getText(sign).stream()
            .anyMatch(x -> x.contains(id));
    }

    public static String removeBraces(String id) {
        return id.replace("[", "")
            .replace("]", "");
    }

    public static List<String> getText(SignTileEntity sign) {
        SignDuck duck = (SignDuck) sign;

        ITextComponent[] list = duck.getTexts();

        List<String> texts = new ArrayList<>();

        for (ITextComponent text : list) {
            texts.add(text.getContents());
        }
        return texts;
    }
}
