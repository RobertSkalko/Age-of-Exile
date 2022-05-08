package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.wrappers.SText;
import com.robertx22.library_of_exile.utils.CLOC;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TooltipUtils {

    public static String CHECKMARK = TextFormatting.GREEN + "\u2714";
    public static String X = TextFormatting.RED + "\u2716";

    public static IFormattableTextComponent color(TextFormatting format, IFormattableTextComponent comp) {
        return new StringTextComponent(format + "").append(comp);
    }

    public static void addRequirements(List<ITextComponent> tip, int lvl, StatRequirement req, EntityData data) {

        if (data.getLevel() >= lvl) {
            tip.add(new StringTextComponent(TextFormatting.GREEN + "" + TextFormatting.BOLD + StatRequirement.CHECK_YES_ICON + TextFormatting.GRAY)
                .append(TextFormatting.GRAY + " Level Min: " + lvl + " "));

        } else {
            tip.add(new StringTextComponent(TextFormatting.RED + "" + TextFormatting.BOLD + StatRequirement.NO_ICON + TextFormatting.GRAY)
                .append(TextFormatting.GRAY + " Level Min: " + lvl + " ")
            );
        }
        tip.addAll(req
            .GetTooltipString(lvl, data));
    }

    public static void addEmpty(List<ITextComponent> tooltip) {
        tooltip.add(CLOC.blank(""));
    }

    public static List<String> compsToStrings(List<ITextComponent> list) {
        return list.stream()
            .map(x -> x.getContents())
            .collect(Collectors.toList());
    }

    public static IFormattableTextComponent level(int lvl) {
        return new StringTextComponent(TextFormatting.YELLOW + "").append(Words.Level.locName())
            .append((": " + lvl))
            .withStyle(TextFormatting.YELLOW);

    }

    public static List<ITextComponent> cutIfTooLong(IFormattableTextComponent comp) {
        List<String> stringList = cutIfTooLong(CLOC.translate(comp));
        return stringList.stream()
            .map(x -> new SText(x))
            .collect(Collectors.toList());

    }

    public static List<IFormattableTextComponent> cutIfTooLong(IFormattableTextComponent comp, TextFormatting format) {
        List<String> stringList = cutIfTooLong(CLOC.translate(comp));
        return stringList.stream()
            .map(x -> new SText(x).withStyle(format))
            .collect(Collectors.toList());

    }

    // private static final Pattern PATTERN = Pattern.compile("(?)§[0-9A-FK-OR]");

    static Character CHAR = "§".charAt(0); // TODO WTF INTELIJ

    public static List<String> cutIfTooLong(String str) {

        List<String> list = new ArrayList<>();

        TextFormatting format = null;

        char[] array = str.toCharArray();

        int start = 0;
        int i = 0;

        TextFormatting formattouse = null;

        for (Character c : array) {

            if (c.equals(CHAR)) {
                format = TextFormatting.getByCode(array[i + 1]);
            }

            if (i == str.length() - 1) {
                String cut = str.substring(start);
                if (cut.startsWith(" ")) {
                    cut = cut.substring(1);
                }
                if (formattouse != null) {
                    cut = formattouse + cut;
                    format = null;
                    formattouse = null;
                }
                list.add(cut);
            } else if (i - start > 28 && c == ' ') {
                String cut = str.substring(start, i);
                if (start > 0) {
                    cut = cut.substring(1);
                }

                if (format != null) {
                    formattouse = format;
                }

                list.add(cut);

                start = i;
            }
            i++;
        }

        return list;
    }

    public static IFormattableTextComponent itemBrokenText(ItemStack stack, ICommonDataItem data) {

        if (data != null) {

            if (RepairUtils.isItemBroken(stack)) {
                IFormattableTextComponent comp = new StringTextComponent(X + " ").append(Words.Broken.locName());
                return comp;
            }

        }

        return null;
    }

    public static List<ITextComponent> removeDoubleBlankLines(List<ITextComponent> list) {
        return removeDoubleBlankLines(list, 5000);
    }

    public static List<ITextComponent> removeDoubleBlankLines(List<ITextComponent> list, int minLinesCutAllBlanks) {

        List<ITextComponent> newt = new ArrayList();

        boolean lastIsEmpty = false;

        boolean alwaysRemoveEmpty = list.size() > minLinesCutAllBlanks;

        for (int i = 0; i < list.size(); i++) {

            if (list.get(i)
                .getString()
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

    public static IFormattableTextComponent rarity(Rarity rarity) {

        return new StringTextComponent(rarity.textFormatting() + "")
            .append(rarity.locName())
            .withStyle(rarity.textFormatting());
    }

    public static IFormattableTextComponent rarityShort(Rarity rarity) {
        return (new StringTextComponent(rarity.textFormatting() + "").append(rarity.locName()));
    }

    public static IFormattableTextComponent tier(int tier) {
        return Words.Tier.locName()
            .append(": " + tier);

    }

    public static IFormattableTextComponent gearSlot(GearSlot slot) {
        return new StringTextComponent("Item Type: ").withStyle(TextFormatting.WHITE)
            .append(slot.locName()
                .withStyle(TextFormatting.AQUA));
    }

    public static IFormattableTextComponent gearTier(int tier) {
        return new StringTextComponent("Item Tier: ").withStyle(TextFormatting.WHITE)
            .append(new StringTextComponent(tier + "").withStyle(TextFormatting.AQUA));
    }

    public static IFormattableTextComponent gearRarity(GearRarity rarity) {
        return new StringTextComponent(TextFormatting.GRAY + "Rarity: ")
            .append(rarity.locName()
                .withStyle(rarity.textFormatting(), TextFormatting.BOLD));
    }

    public static IFormattableTextComponent gearLevel(int lvl) {
        IFormattableTextComponent txt = Words.Level.locName()
            .append(" " + lvl);
        txt.withStyle(TextFormatting.BOLD, TextFormatting.YELLOW);
        return txt;

    }

    public static IFormattableTextComponent gearLevelAndRarity(GearItemData gear) {

        IFormattableTextComponent txt = new StringTextComponent("")
            .append(gear.getRarity()
                .locName()
                .withStyle(gear.getRarity()
                    .textFormatting(), TextFormatting.BOLD));

        int stars = gear.getStarsAmount();
        IFormattableTextComponent starText = new StringTextComponent("");
        for (int i = 0; i < stars; i++) {
            starText = starText.append(gear.getRarity()
                .textFormatting() + STAR);
        }

        txt.append(" (");
        txt.append(starText);
        txt.append(")");
        return txt.withStyle(TextFormatting.GREEN);
    }

    public static String STAR = "\u2605";

    public static IFormattableTextComponent gearStars(GearItemData gear) {
        int stars = gear.getStarsAmount();
        IFormattableTextComponent txt = new StringTextComponent(TextFormatting.GRAY + "Rating: ");
        for (int i = 0; i < stars; i++) {
            txt = txt.append(gear.getRarity()
                .textFormatting() + STAR);
        }
        return txt;
    }

    public static IFormattableTextComponent reforgeStars(GearItemData gear) {
        int stars = gear.reforge.getRarity().item_tier;

        IFormattableTextComponent starstext = new StringTextComponent("");

        IFormattableTextComponent txt = new StringTextComponent("").append(gear.reforge.getReforge()
                .locName())
            .append(":");

        for (int i = 0; i < stars; i++) {
            starstext = starstext.append(gear.reforge.getRarity()
                .textFormatting() + STAR);
        }
        if (stars > 0) {
            txt.append(" (");
            txt.append(starstext);
            txt.append(")");
        }

        return txt.withStyle(TextFormatting.GREEN);
    }

    public static String STAR_2 = "\u272B";

    public static IFormattableTextComponent dragOntoGearToUse() {
        return Words.DragToGearToUse.locName()
            .withStyle(TextFormatting.AQUA, TextFormatting.BOLD);
    }
}
