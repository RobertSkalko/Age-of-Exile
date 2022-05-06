package com.robertx22.age_of_exile.saveclasses.item_classes;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.config.forge.ClientConfigs;
import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.gearitem.rework.MergedStats;
import com.robertx22.age_of_exile.saveclasses.gearitem.rework.StatModifierInfo;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.age_of_exile.uncommon.wrappers.SText;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.*;

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

        List<IFormattableTextComponent> name = gear.GetDisplayName(stack);

        name.forEach(x -> {
            tip.add(x.withStyle(TextFormatting.BOLD));
        });

        tip.add(new SText(""));

        List<IGearPartTooltip> list = new ArrayList<IGearPartTooltip>();

        List<StatModifierInfo> specialStats = new ArrayList<>();

        if (info.useInDepthStats()) {
            if (gear.baseStats != null) {
                tip.addAll(gear.baseStats.GetTooltipString(info, gear));
            }
            if (gear.uniqueStats != null) {
                tip.addAll(gear.uniqueStats.GetTooltipString(info, gear));
            }
            tip.addAll(gear.affixes.GetTooltipString(info, gear));

        } else {

            List<StatModifierInfo> stats = new ArrayList<>();
            if (gear.baseStats != null) {
                stats.addAll(gear.baseStats.getStatsWithContext(gear).list);
            }

            stats.addAll(gear.affixes.getStatsWithContext(gear).list);

            if (gear.uniqueStats != null) {
                stats.addAll(gear.uniqueStats.getStatsWithContext(gear)
                    .list);
            }

            List<StatModifierInfo> longstats = stats.stream()
                .filter(x -> x.exactStat.getStat().is_long)
                .collect(Collectors.toList());
            specialStats.addAll(longstats);

            MergedStats merged = new MergedStats(stats);

            list.add(merged);

        }

        int n = 0;
        for (IGearPartTooltip part : list) {
            if (part != null) {
                tip.addAll(part.GetTooltipString(info, gear));
                tip.add(new StringTextComponent(""));
            }
            n++;
        }

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

        if (gear.reforge.hasReforge()) {
            tip.addAll(gear.reforge.GetTooltipString(info, gear));
        }
        if (Screen.hasShiftDown()) {
            if (!gear.can_sal) {
                tip.add(
                    Words.Unsalvagable.locName()
                        .withStyle(TextFormatting.RED));
            }
        }

        tip.add(new StringTextComponent(""));

        tip.add(TooltipUtils.gearLevelAndRarity(gear));
        tip.add(TooltipUtils.gearLevel(gear.lvl));

        //tip.add(TooltipUtils.gearRarity(gear.getRarity()));
        //tip.add(TooltipUtils.gearStars(gear));

        tip.add(new StringTextComponent(""));

        ItemStack.appendEnchantmentNames(tip, stack.getEnchantmentTags());

        // todo
        if (ClientConfigs.getConfig().SHOW_DURABILITY.get()) {
            if (stack.isDamageableItem()) {
                tip.add(new SText(TextFormatting.GRAY + "Durability: " + (stack.getMaxDamage() - stack.getDamageValue()) + "/" + stack.getMaxDamage()));
            } else {
                tip.add(new SText(TextFormatting.GRAY + "Unbreakable"));
            }
        }

        if (Screen.hasShiftDown() == false) {
            tooltip.add(new StringTextComponent(TextFormatting.BLUE + "").append(new TranslationTextComponent(SlashRef.MODID + ".tooltip." + "press_shift_more_info")
                )
                .withStyle(TextFormatting.BLUE));

        } else {
            tip.add(Words.Instability.locName()
                .withStyle(TextFormatting.RED)
                .append(": " + (int) gear.getInstability() + "/" + (int) ServerContainer.get().MAX_INSTABILITY.get()
                    .intValue())
            );
        }

        List<ITextComponent> tool = TooltipUtils.removeDoubleBlankLines(tip,
            ClientConfigs.getConfig().REMOVE_EMPTY_TOOLTIP_LINES_IF_MORE_THAN_X_LINES);

        tip.clear();
        tip.addAll(tool);

    }

}
