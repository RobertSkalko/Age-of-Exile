package com.robertx22.age_of_exile.player_skills.items.inscribing;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class ScrollBuffItem extends AutoItem {

    public ScrollBuffItem() {
        super(new Settings());
    }

    @Override
    public Text getName(ItemStack stack) {
        MutableText txt = new TranslatableText(this.getTranslationKey()).formatted(Formatting.YELLOW);

        try {
            ScrollBuffData data = getData(stack);

            if (data != null) {
                txt.append(" ")
                    .append(data.getBuff()
                        .locName())
                    .formatted(Formatting.YELLOW);
            }
        } catch (Exception e) {
        }
        return txt;

    }

    public static ItemStack create(ScrollBuffData data) {
        ItemStack stack = new ItemStack(ModRegistry.MISC_ITEMS.SCROLL_BUFF);
        data.saveToStack(stack);
        return stack;
    }

    public ScrollBuffData getData(ItemStack stack) {
        return LoadSave.Load(ScrollBuffData.class, new ScrollBuffData(), stack.getOrCreateTag(), "sb");
    }

    @Override
    public String locNameForLangFile() {
        return "Scroll";
    }

    @Override
    public String GUID() {
        return "";
    }
}
