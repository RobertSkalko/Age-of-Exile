package com.robertx22.age_of_exile.saveclasses.item_classes;

import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.rarities.IGearRarity;
import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.MergedStats;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.age_of_exile.uncommon.wrappers.SText;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
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

        if (!gear.GetBaseGearType()
            .isTool()) {

            tip.clear();

            gear.GetDisplayName(stack)
                .forEach(x -> {
                    tip.add(x);
                });

            if (gear.GetBaseGearType()
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

            PlayerSkillEnum skill = gear.getSkillNeeded();

            if (skill != null) {
                int skillvll = Load.playerSkills(info.player)
                    .getLevel(skill);
                if (skillvll >= gear.lvl) {
                    tip.add(new LiteralText(Formatting.GREEN + "" + Formatting.BOLD + StatRequirement.CHECK_YES_ICON + Formatting.GRAY)
                        .append(Formatting.GRAY + " " + skill.word.translate() + " Level Min: " + gear.lvl + " "));

                } else {
                    tip.add(new LiteralText(Formatting.RED + "" + Formatting.BOLD + StatRequirement.NO_ICON + Formatting.GRAY)
                        .append(Formatting.GRAY + " " + skill.word.translate() + " Level Min: " + gear.lvl + " ")
                    );
                }
            }

            if (!info.shouldShowDescriptions()) {
                // save some space when the big stat descriptions show
                TooltipUtils.addRequirements(tip, gear.lvl, gear.getRequirement(), data);
                tip.add(new SText(""));
            }

            if (gear.implicit != null) {
                tip.addAll(gear.implicit.GetTooltipString(info, gear));
            }

            // tip.add(new LiteralText(""));

            List<IGearPartTooltip> list = new ArrayList<IGearPartTooltip>();

            List<ExactStatData> specialStats = new ArrayList<>();

            if (gear.uniqueStats != null) {
                tip.addAll(gear.uniqueStats.GetTooltipString(info, gear));

                gear.uniqueStats.GetAllStats(gear)
                    .forEach(x -> {
                        if (x.getStat().isLongStat) {
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

                MergedStats merged = new MergedStats(stats, info, gear);

                stats.forEach(x -> {
                    if (x.getStat().isLongStat) {
                        specialStats.add(x);
                    }
                });

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

            tip.add(TooltipUtils.rarity(rarity)
                .append(" ")
                .append(gear.GetBaseGearType()
                    .locName()));

            if (ModConfig.get().client.SHOW_DURABILITY)
                if (stack.isDamageable()) {
                    tip.add(new SText(Formatting.GRAY + "Durability: " + (stack.getMaxDamage() - stack.getDamage()) + "/" + stack.getMaxDamage()));
                } else {
                    tip.add(new SText(Formatting.GRAY + "Unbreakable"));
                }

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
                    .append(": " + (int) gear.getInstability() + "/" + (int) ModConfig.get().ItemSealing.MAX_INSTABILITY)
                );
            }

            List<Text> tool = TooltipUtils.removeDoubleBlankLines(tip,
                ModConfig.get().client.REMOVE_EMPTY_TOOLTIP_LINES_IF_MORE_THAN_X_LINES);

            tip.clear();
            tip.addAll(tool);
        } else {
            // if tool

            tooltip.add(new LiteralText(""));
            tooltip.add(new LiteralText("Upgrades:").formatted(Formatting.RED)
                .formatted(Formatting.BOLD));

            gear.affixes.getAllAffixesAndSockets()
                .forEach(x -> {

                    int tiers = x.getAffix().tier_map.size();
                    int tier = x.tier;

                    int stars = 1 + tiers - tier;

                    Formatting format = Formatting.GREEN;

                    if (stars == 2) {
                        format = Formatting.BLUE;
                    }
                    if (stars == 3) {
                        format = Formatting.LIGHT_PURPLE;
                    }

                    MutableText txt = new LiteralText("").append(x.getAffix()
                        .locName())
                        .formatted(Formatting.GOLD);

                    //  txt.append(" ");

                    for (int i = 0; i < stars; i++) {
                        txt.append(new LiteralText(" " + "\u2605").formatted(format));
                    }

                    tooltip.add(txt);

                    if (Screen.hasShiftDown()) {
                        tooltip.addAll(x.GetTooltipString(info, gear));
                    }

                });
        }

    }

}
