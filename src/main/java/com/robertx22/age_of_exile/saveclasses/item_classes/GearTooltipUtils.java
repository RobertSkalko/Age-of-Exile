package com.robertx22.age_of_exile.saveclasses.item_classes;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.config.forge.ModConfig;
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
import java.util.stream.Collectors;

public class GearTooltipUtils {

    public static void BuildTooltip(GearItemData gear, ItemStack stack, List<Text> tooltip, EntityData data) {

        List<Text> tip = tooltip;

        if (gear.GetBaseGearType() == null) {
            return;
        }

        TooltipInfo info = new TooltipInfo(data, gear.getRarity()
            .StatPercents());

        tip.clear();

        gear.GetDisplayName(stack)
            .forEach(x -> {
                tip.add(x);
            });

        if (gear.baseStats != null) {
            tip.addAll(gear.baseStats.GetTooltipString(info, gear));
        }

        tip.add(new SText(""));

        if (gear.imp != null) {
            tip.addAll(gear.imp.GetTooltipString(info, gear));
        }

        List<IGearPartTooltip> list = new ArrayList<IGearPartTooltip>();

        List<ExactStatData> specialStats = new ArrayList<>();

        //tip.add(new LiteralText(""));

        if (info.useInDepthStats()) {
            if (gear.isUnique()) {
                tip.addAll(gear.uniqueStats.GetTooltipString(info, gear));
            }
            tip.addAll(gear.affixes.GetTooltipString(info, gear));
            tip.addAll(gear.sockets.GetTooltipString(info, gear));
            tip.addAll(gear.imp.GetTooltipString(info, gear));
        } else {
            List<ExactStatData> stats = new ArrayList<>();
            gear.affixes.getAllAffixesAndSockets()
                .forEach(x -> stats.addAll(x.GetAllStats(gear)));
            stats.addAll(gear.sockets.GetAllStats(gear));
            stats.addAll(gear.imp.GetAllStats(gear));
            if (gear.isUnique()) {
                stats.addAll(gear.uniqueStats.GetAllStats(gear));
            }
            List<ExactStatData> longstats = stats.stream()
                .filter(x -> x.getStat().is_long)
                .collect(Collectors.toList());
            specialStats.addAll(longstats);

            MergedStats merged = new MergedStats(stats, info, gear);

            list.add(merged);
        }

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
        tip.add(new LiteralText(""));

        specialStats.forEach(x -> {
            x.GetTooltipString(info)
                .forEach(e -> {
                    tip.add(e);

                });
        });
        tip.add(new LiteralText(""));

        if (gear.uniqueStats != null) {
            UniqueGear uniq = gear.uniqueStats.getUnique(gear);

            if (uniq != null && uniq.hasSet()) {
                tooltip.addAll(uniq.getSet()
                    .GetTooltipString(info));
            }
        }

        if (Screen.hasShiftDown()) {
            if (!gear.can_sal) {
                tip.add(
                    Words.Unsalvagable.locName()
                        .formatted(Formatting.RED));
            }
        }
        if (gear.isCorrupted()) {
            tip.add(new LiteralText(Formatting.RED + "").append(
                    Words.Corrupted.locName())
                .formatted(Formatting.RED));
        }
        int socketed = gear.sockets.sockets.size();
        if (socketed > 0) {
            TooltipUtils.addSocketNamesLine(tip, gear);
        }

        tip.add(new LiteralText(""));

        tip.add(TooltipUtils.gearTier(gear.getTier()));
        tip.add(TooltipUtils.gearLevel(gear.lvl));
        tip.add(TooltipUtils.gearRarity(gear.getRarity()));

        tip.add(new LiteralText(""));
        ItemStack.appendEnchantments(tip, stack.getEnchantments());

        if (ModConfig.get().client.SHOW_DURABILITY) {
            if (stack.isDamageable()) {
                tip.add(new SText(Formatting.WHITE + "Durability: " + (stack.getMaxDamage() - stack.getDamage()) + "/" + stack.getMaxDamage()));
            } else {
                tip.add(new SText(Formatting.WHITE + "Unbreakable"));
            }
        }

        if (Screen.hasShiftDown() == false) {
            tooltip.add(new LiteralText(Formatting.BLUE + "").append(new TranslatableText(Ref.MODID + ".tooltip." + "press_shift_more_info")
                )
                .formatted(Formatting.BLUE));
        } else {
            tip.add(Words.Instability.locName()
                .formatted(Formatting.RED)
                .append(": " + (int) gear.getInstability() + "/" + (int) ModConfig.get().Server.MAX_INSTABILITY)
            );
        }

        List<Text> tool = TooltipUtils.removeDoubleBlankLines(tip,
            ModConfig.get().client.REMOVE_EMPTY_TOOLTIP_LINES_IF_MORE_THAN_X_LINES);

        tip.clear();
        tip.addAll(tool);

    }

}
