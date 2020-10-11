package com.robertx22.age_of_exile.saveclasses.item_classes;

import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.rarities.IGearRarity;
import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.MergedStats;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.age_of_exile.uncommon.wrappers.SText;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class GearTooltipUtils {

    public static void BuildTooltip(GearItemData gear, ItemStack stack, List<Text> tooltip, UnitData data) {

        List<Text> tip = tooltip;

        if (gear.GetBaseGearType() == null) {
            return;
        }

        TooltipInfo info = new TooltipInfo(data, gear.getRarity()
            .StatPercents());

        tip.clear();

        tip.add(gear.GetDisplayName(stack));

        if (!gear.isIdentified()) {

            tip.add(new SText(""));

            tip.add(
                Words.ItemIsUnidentified.locName()
                    .formatted(Formatting.GRAY));
            tip.add(
                Words.UseAnIdentifyScroll.locName()
                    .formatted(Formatting.GRAY));

            tip.add(new SText(""));

            return;
        }

        if (gear.baseStats != null) {
            tip.addAll(gear.baseStats.GetTooltipString(info, gear));
        }

        TooltipUtils.addRequirements(tip, gear, data);

        if (gear.implicitStats != null) {
            tip.addAll(gear.implicitStats.GetTooltipString(info, gear));
        }

        tip.add(new LiteralText(""));

        List<IGearPartTooltip> list = new ArrayList<IGearPartTooltip>();

        if (gear.uniqueStats != null) {
            tip.addAll(gear.uniqueStats.GetTooltipString(info, gear));
        }

        tip.add(new LiteralText(""));

        List<ExactStatData> stats = new ArrayList<>();

        gear.affixes.getAllAffixesAndSockets()
            .forEach(x -> stats.addAll(x.GetAllStats(gear)));

        stats.addAll(gear.sockets.GetAllStats(gear));

        MergedStats merged = new MergedStats(stats, info);
        list.add(merged);

        int n = 0;
        for (IGearPartTooltip part : list) {
            if (part != null) {
                tip.addAll(part.GetTooltipString(info, gear));

                if (n == list.size() - 1) {
                    for (int i = 0; i < gear.sockets.getEmptySockets(); i++) {
                        tip.add(new SText(Formatting.YELLOW + "[Socket]"));
                    }
                }

                tip.add(new LiteralText(""));
            }
            n++;
        }

        if (Screen.hasShiftDown()) {
            if (!gear.isSalvagable) {
                tip.add(
                    Words.Unsalvagable.locName()
                        .formatted(Formatting.RED));
            }
        }

        if (gear.is_unique) {
            UniqueGear unique = gear.uniqueStats.getUnique();
            TooltipUtils.addUniqueDesc(tip, unique, gear);
        }

        tip.add(new SText(""));

        RuneWord word = gear.sockets.getRuneWord();

        if (word != null) {

            tip.add(new LiteralText(Formatting.GOLD + "'Runeword: ").append(word.locName())
                .append("'")
                .formatted(Formatting.GOLD));
        }
        tip.add(new LiteralText(""));

        IGearRarity rarity = gear.getRarity();

        int socketed = gear.sockets.sockets.size();

        if (socketed > 0) {
            TooltipUtils.addSocketNamesLine(tip, gear);
        }

        tip.add(new LiteralText(""));

        tip.add(TooltipUtils.rarity(rarity));

        tip.add(new SText(Formatting.GRAY + "Durability: " + (stack.getMaxDamage() - stack.getDamage()) + "/" + stack.getMaxDamage()));

        if (gear.sealed) {
            tip.add(Words.Sealed.locName()
                .formatted(Formatting.RED));
        }

        if (Screen.hasShiftDown() == false) {
            tooltip.add(new LiteralText(Formatting.BLUE + "").append(new TranslatableText(Ref.MODID + ".tooltip." + "press_shift_more_info")
            )
                .formatted(Formatting.BLUE));
        } else {
            tip.add(Words.Instability.locName()
                .formatted(Formatting.RED)
                .append(": " + (int) gear.getInstability() + "/" + (int) ModConfig.get().ItemSealing.ALWAYS_SEAL_AT_X_INSTABILITY)
            );
        }

        List<Text> tool = TooltipUtils.removeDoubleBlankLines(tip,
            ModConfig.get().client.REMOVE_EMPTY_TOOLTIP_LINES_IF_MORE_THAN_X_LINES);

        tip.clear();
        tip.addAll(tool);

    }

}
