package com.robertx22.age_of_exile.database.data.reforge;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.vanilla_mc.items.ItemDefault;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class ReforgeItem extends Item implements IAutoLocName {

    public ReforgeItem() {
        super(new ItemDefault().tab(CreativeTabs.RuneWords));
    }

    public static String getIdPath(String id) {
        return "runewords/" + id;
    }

    public static Reforge getReforge(ItemStack stack) {

        try {
            return ExileDB.Reforges()
                .get(stack.getOrCreateTag()
                    .getString("reforge"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> list) {
        if (this.allowdedIn(group)) {
            for (Reforge word : ExileDB.Reforges()
                .getList()) {
                list.add(word.getStack());
            }
        }
    }

    @Override
    public ITextComponent getName(ItemStack stack) {

        Words word = Words.Reforge;

        Reforge reforge = getReforge(stack);

        if (reforge != null) {
            return new StringTextComponent("").append(word.locName())
                .append(" ")
                .append(reforge.locName())
                .withStyle(reforge.getRarity()
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
        return "Reforge";
    }

    @Override
    public String GUID() {
        return "reforge";
    }
}

