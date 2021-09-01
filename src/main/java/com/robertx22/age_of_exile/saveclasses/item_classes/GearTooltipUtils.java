package com.robertx22.age_of_exile.saveclasses.item_classes;

import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.rarities.IGearRarity;
import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.MergedStats;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
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

    public static void BuildTooltip(GearItemData gear, ItemStack stack, List<Text> tooltip, UnitData data) {

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

        if (false && gear.GetBaseGearType()
            .family() == BaseGearType.SlotFamily.Weapon) {

            String speed = "Normal";

            float atks = gear.GetBaseGearType().attacksPerSecond;

            if (atks < 1) {
                speed = "Slow";
            }
            if (atks > 1) {
                speed = "Fast";
            }
            String txt = speed + " Attack Speed";

            if (Screen.hasShiftDown()) {
                txt += " (" + atks + "/s)";
            }

            tip.add(new LiteralText(txt).formatted(Formatting.GRAY));

        }

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

        if (gear.isCorrupted()) {
            if (!Screen.hasShiftDown()) {
                tip.add(new SText(""));

                tip.add(new LiteralText(Formatting.RED + "").append(
                        Words.Corrupted.locName())
                    .formatted(Formatting.RED));

                tip.add(new SText(""));

                tip.add(
                    Words.CorruptedExplanation1.locName()
                        .formatted(Formatting.GRAY));
                tip.add(
                    Words.CorruptedExplanation2.locName()
                        .formatted(Formatting.GRAY));

                tip.add(new SText(""));

                return;
            }

        }

        if (gear.baseStats != null) {
            tip.addAll(gear.baseStats.GetTooltipString(info, gear));
        }

        tip.add(new SText(""));

        if (gear.imp != null) {
            tip.addAll(gear.imp.GetTooltipString(info, gear));
        }

        // tip.add(new LiteralText(""));

        List<IGearPartTooltip> list = new ArrayList<IGearPartTooltip>();

        List<ExactStatData> specialStats = new ArrayList<>();

        if (gear.uniqueStats != null) {
            tip.addAll(gear.uniqueStats.GetTooltipString(info, gear));

            gear.uniqueStats.GetAllStats(gear)
                .forEach(x -> {
                    if (x.getStat().is_long) {
                        specialStats.add(x);
                    }
                });

        }

        //tip.add(new LiteralText(""));

        if (info.useInDepthStats()) {
            tip.addAll(gear.affixes.GetTooltipString(info, gear));
            tip.addAll(gear.sockets.GetTooltipString(info, gear));
        } else {
            List<ExactStatData> stats = new ArrayList<>();
            gear.affixes.getAllAffixesAndSockets()
                .forEach(x -> stats.addAll(x.GetAllStats(gear)));
            stats.addAll(gear.sockets.GetAllStats(gear));

            List<ExactStatData> longstats = stats.stream()
                .filter(x -> x.getStat().is_long)
                .collect(Collectors.toList());
            specialStats.addAll(longstats);

            MergedStats merged = new MergedStats(stats, info, gear);

            list.add(merged);
        }

        if (true || MMORPG.RUN_DEV_TOOLS) {
            /*
            list.clear();


            Formatting format = gear.getRarity()
                .textFormatting();
            tip.add(new LiteralText(format + "+6% Armor"));
            tip.add(new LiteralText(format + "+3% Health"));
            tip.add(new LiteralText(format + "+20% Water Resist"));
            tip.add(new LiteralText(format + "+12% Critical Damage"));
            tip.add(new LiteralText(format + "+7% Spell Damage"));
            tip.add(new LiteralText(format + "+12% Magic Find"));


             */
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

        if (gear.getRarity()
            .GUID()
            .equals(IRarity.UNIQUE_ID)) {
            if (false && !info.shouldShowDescriptions()) {
                UniqueGear unique = gear.uniqueStats.getUnique(gear);
                TooltipUtils.addUniqueDesc(tip, unique, gear);
            }
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

        // TODO

        tip.add(TooltipUtils.gearTier(gear.getTier()));
        tip.add(TooltipUtils.gearLevel(gear.lvl));
        tip.add(TooltipUtils.gearRarity(gear.getRarity()));
        if (false) {
            tip.add(TooltipUtils.rarity(rarity)
                .append(" ")
                .append(gear.GetBaseGearType()
                    .locName()));

        }

        tip.add(new LiteralText(""));
        ItemStack.appendEnchantments(tip, stack.getEnchantments());

        if (true || ModConfig.get().client.SHOW_DURABILITY) {
            if (stack.isDamageable()) {
                tip.add(new SText(Formatting.WHITE + "Durability: " + (stack.getMaxDamage() - stack.getDamage()) + "/" + stack.getMaxDamage()));
            } else {
                tip.add(new SText(Formatting.WHITE + "Unbreakable"));
            }
        }

        if (gear.s) {
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
                .append(": " + (int) gear.getInstability() + "/" + (int) ModConfig.get().ItemSealing.MAX_INSTABILITY)
            );
        }

        List<Text> tool = TooltipUtils.removeDoubleBlankLines(tip,
            ModConfig.get().client.REMOVE_EMPTY_TOOLTIP_LINES_IF_MORE_THAN_X_LINES);

        tip.clear();
        tip.addAll(tool);

    }

}
