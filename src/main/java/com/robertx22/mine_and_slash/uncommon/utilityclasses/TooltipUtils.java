package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import com.robertx22.mine_and_slash.database.data.rarities.gears.UniqueGear;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.mine_and_slash.uncommon.localization.CLOC;
import com.robertx22.mine_and_slash.uncommon.localization.Styles;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.wrappers.SText;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TooltipUtils {

    public static String CHECKMARK = Formatting.GREEN + "\u2714";
    public static String X = Formatting.RED + "\u2716";

    public static MutableText color(Formatting format, MutableText comp) {
        return new LiteralText(format + "").append(comp);
    }

    public static void addEmpty(List<MutableText> tooltip) {
        tooltip.add(CLOC.blank(""));
    }

    public static List<String> compsToStrings(List<MutableText> list) {
        return list.stream()
            .map(MutableText::asFormattedString)
            .collect(Collectors.toList());
    }

    public static MutableText level(int lvl) {
        return new LiteralText(Formatting.YELLOW + "").append(Words.Level.locName())
            .append((": " + lvl));

    }

    public static List<MutableText> cutIfTooLong(MutableText comp) {
        List<String> stringList = cutIfTooLong(CLOC.translate(comp));
        return stringList.stream()
            .map(x -> new SText(x))
            .collect(Collectors.toList());

    }

    public static List<String> cutIfTooLong(String str) {

        List<String> list = new ArrayList<>();

        int start = 0;
        int i = 0;
        for (Character c : str.toCharArray()) {

            if (i == str.length() - 1) {
                list.add(str.substring(start));
            } else if (i - start > 28 && c == ' ') {
                String cut = str.substring(start, i);
                if (start > 0) {
                    cut = cut.substring(1);
                }

                list.add(cut);

                start = i;
            }
            i++;
        }

        return list;
    }

    public static MutableText itemBrokenText(ItemStack stack, ICommonDataItem data) {

        if (data != null) {

            if (RepairUtils.isItemBroken(stack)) {
                MutableText comp = new LiteralText(X + " ").append(Words.Broken.locName());
                return comp;
            }

        }

        return null;
    }

    public static MutableText requirement(MutableText text, int stat, int req) {

        MutableText comp;

        if (stat >= req) {
            comp = new LiteralText(CHECKMARK);
        } else {
            comp = new LiteralText(X);
        }

        return comp.append(
            new LiteralText(Formatting.GRAY + " ").append(text)
                .append(": " + req));

    }

    public static List<MutableText> removeDoubleBlankLines(List<MutableText> list) {
        return removeDoubleBlankLines(list, 5000);
    }

    public static List<MutableText> removeDoubleBlankLines(List<MutableText> list, int minLinesCutAllBlanks) {

        List<MutableText> newt = new ArrayList();

        boolean lastIsEmpty = false;

        boolean alwaysRemoveEmpty = list.size() > minLinesCutAllBlanks;

        for (int i = 0; i < list.size(); i++) {

            if (list.get(i)
                .asFormattedString()
                .length() > 2) {
                lastIsEmpty = false;
                newt.add(list.get(i));
            } else {

                if ((lastIsEmpty || alwaysRemoveEmpty)) {

                } else {
                    newt.add(list.get(i));
                }

                lastIsEmpty = true;

            }
        }

        list.clear();

        list.addAll(newt);

        return newt;
    }

    public static MutableText rarity(Rarity rarity) {
        return (new LiteralText(rarity.textFormatting() + "")
            .append(rarity.locName()
                .append(" ")
                .append(Words.Item.locName())));
    }

    public static MutableText rarityShort(Rarity rarity) {
        return (new LiteralText(rarity.textFormatting() + "").append(rarity.locName()));
    }

    public static MutableText tier(int tier) {

        return Styles.YELLOWCOMP()
            .append(Words.Tier.locName())
            .append(": " + tier);

    }

    public static MutableText uniqueTier(int tier) {
        return Styles.YELLOWCOMP()
            .append(Words.Tier.locName())
            .append(" " + tier + " ")
            .append(UniqueGear.getInstance()
                .locName());

    }

    public static void abilityLevel(List<MutableText> list, int current, int max) {
        list.add(
            new SText(Formatting.YELLOW + "").append("Ability ")
                .append(Words.Level.locName())
                .append(": " + current + "/" + max));
    }
}
