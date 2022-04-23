package com.robertx22.age_of_exile.database.data.runewords;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.vanilla_mc.items.ItemDefault;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class RuneWordItem extends Item implements IAutoLocName {

    public RuneWordItem() {
        super(new ItemDefault().tab(CreativeTabs.RuneWords));
    }

    public static String getIdPath(String id) {
        return "runewords/" + id;
    }

    public static RuneWord getRuneWord(ItemStack stack) {

        try {
            return ExileDB.RuneWords()
                .get(stack.getOrCreateTag()
                    .getString("runeword"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> list) {
        if (this.allowdedIn(group)) {
            for (RuneWord word : ExileDB.RuneWords()
                .getList()) {
                list.add(word.getStack());
            }
        }
    }

    @Override
    public ITextComponent getName(ItemStack stack) {

        RuneWord word = getRuneWord(stack);
        if (word != null) {
            return new TranslationTextComponent(this.getDescriptionId(stack)).append(" ")
                .append(word.getUnique()
                    .locName())
                .withStyle(word.getUnique()
                    .getUniqueRarity()
                    .textFormatting());
        }
        return super.getName(stack);

    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public String locNameLangFileGUID() {
        return Registry.ITEM.getKey(this)
            .toString();
    }

    @Override
    public String locNameForLangFile() {
        return "Rune Word";
    }

    @Override
    public String GUID() {
        return "runeword";
    }
}
