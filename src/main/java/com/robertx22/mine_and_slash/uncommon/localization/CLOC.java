package com.robertx22.mine_and_slash.uncommon.localization;

import com.robertx22.mine_and_slash.mmorpg.Ref;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class CLOC {

    public static String translate(Text s) {
        return I18n.translate(s.asString()
            .replaceAll("%", "PERCENT"))
            .replaceAll("PERCENT", "%");
    }

    private static MutableText base(String s) {
        if (s.isEmpty()) {
            return new LiteralText("");
        } else {
            return new TranslatableText(s);
        }
    }

    public static MutableText tooltip(String str) {

        return base(Ref.MODID + ".tooltip." + str);

    }

    public static MutableText lore(String str) {

        return new LiteralText(Formatting.GREEN + "'").append(base(Ref.MODID + ".lore." + str)
            .append(new LiteralText("'")));

    }

    public static MutableText blank(String string) {
        return base(string);
    }

}
