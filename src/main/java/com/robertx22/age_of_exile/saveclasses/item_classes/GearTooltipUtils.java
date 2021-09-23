package com.robertx22.age_of_exile.saveclasses.item_classes;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.MergedStats;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.age_of_exile.uncommon.wrappers.SText;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GearTooltipUtils {

    public static void BuildTooltip(GearItemData gear, ItemStack stack, List<ITextComponent> tooltip, EntityData data) {

        List<ITextComponent> tip = tooltip;

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
            if (gear.hasCraftedStats()) {
                tip.addAll(gear.getCraftedStats()
                    .GetTooltipString(info, gear));
            }
        } else {
            List<ExactStatData> stats = new ArrayList<>();
            gear.affixes.getAllAffixesAndSockets()
                .forEach(x -> stats.addAll(x.GetAllStats(gear)));
            stats.addAll(gear.sockets.GetAllStats(gear));
            if (gear.hasCraftedStats()) {
                stats.addAll(gear.getCraftedStats()
                    .GetAllStats(gear));
            }
            stats.addAll(gear.imp.GetAllStats(gear));
            if (gear.isUnique()) {
                stats.addAll(gear.uniqueStats.GetAllStats(gear));
            }
            List<ExactStatData> longstats = stats.stream()
                .filter(x -> x.getStat().is_long)
                .collect(Collectors.toList());
            specialStats.addAll(longstats);

            MergedStats merged = new MergedStats(stats, info);

            list.add(merged);
        }

        int n = 0;
        for (IGearPartTooltip part : list) {
            if (part != null) {
                tip.addAll(part.GetTooltipString(info, gear));

                if (n == list.size() - 1) {
                    for (int i = 0; i < gear.sockets.getEmptySockets(); i++) {
                        tip.add(new SText(TextFormatting.YELLOW + "[Socket]"));
                    }
                }

                tip.add(new StringTextComponent(""));
            }
            n++;
        }
        tip.add(new StringTextComponent(""));

        specialStats.forEach(x -> {
            x.GetTooltipString(info)
                .forEach(e -> {
                    tip.add(e);

                });
        });
        tip.add(new StringTextComponent(""));

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
                        .withStyle(TextFormatting.RED));
            }
        }
        if (gear.isCorrupted()) {
            tip.add(new StringTextComponent(TextFormatting.RED + "").append(
                    Words.Corrupted.locName())
                .withStyle(TextFormatting.RED));
        }
        int socketed = gear.sockets.sockets.size();
        if (socketed > 0) {
            TooltipUtils.addSocketNamesLine(tip, gear);
        }

        tip.add(new StringTextComponent(""));

        tip.add(TooltipUtils.gearTier(gear.getTier()));
        tip.add(TooltipUtils.gearLevel(gear.lvl));
        tip.add(TooltipUtils.gearRarity(gear.getRarity()));

        tip.add(new StringTextComponent(""));
        ItemStack.appendEnchantmentNames(tip, stack.getEnchantmentTags());

        if (ModConfig.get().client.SHOW_DURABILITY) {
            if (stack.isDamageableItem()) {
                tip.add(new SText(TextFormatting.WHITE + "Durability: " + (stack.getMaxDamage() - stack.getDamageValue()) + "/" + stack.getMaxDamage()));
            } else {
                tip.add(new SText(TextFormatting.WHITE + "Unbreakable"));
            }
        }

        if (Screen.hasShiftDown() == false) {
            tooltip.add(new StringTextComponent(TextFormatting.BLUE + "").append(new TranslationTextComponent(SlashRef.MODID + ".tooltip." + "press_shift_more_info")
                )
                .withStyle(TextFormatting.BLUE));
        } else {
            tip.add(Words.Instability.locName()
                .withStyle(TextFormatting.RED)
                .append(": " + (int) gear.getInstability() + "/" + (int) ModConfig.get().Server.MAX_INSTABILITY)
            );
        }

        List<ITextComponent> tool = TooltipUtils.removeDoubleBlankLines(tip,
            ModConfig.get().client.REMOVE_EMPTY_TOOLTIP_LINES_IF_MORE_THAN_X_LINES);

        tip.clear();
        tip.addAll(tool);

    }

}
