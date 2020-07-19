package com.robertx22.mine_and_slash.saveclasses.item_classes;

import com.robertx22.mine_and_slash.capability.entity.EntityCap.UnitData;
import com.robertx22.mine_and_slash.config.forge.ClientConfigs;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.database.data.unique_items.IUnique;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.FinalizedGearStatReq;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.mine_and_slash.saveclasses.item_classes.tooltips.MergedStats;
import com.robertx22.mine_and_slash.uncommon.localization.CLOC;
import com.robertx22.mine_and_slash.uncommon.localization.Styles;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.mine_and_slash.uncommon.wrappers.SText;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class GearTooltipUtils {

    public static void BuildTooltip(GearItemData gear, ItemStack stack, List<Text> tooltip, UnitData data) {

        List<Text> tip = tooltip;

        TooltipInfo info = new TooltipInfo(data, gear.getRarity()
            .StatPercents());

        BaseGearType slot = gear.GetBaseGearType();

        tip.clear();

        tip.add(gear.GetDisplayName(stack));

        if (!gear.isIdentified()) {

            tip.add(new SText(""));

            tip.add(Styles.GRAYCOMP()
                .append(Words.ItemIsUnidentified.locName()));
            tip.add(Styles.GRAYCOMP()
                .append(Words.UseAnIdentifyScroll.locName()));

            tip.add(new SText(""));

            return;
        }

        if (gear.baseStats != null) {
            tip.addAll(gear.baseStats.GetTooltipString(info, gear));
        }

        Formatting reqColor = Formatting.GRAY;
        Formatting reqNumberColor = Formatting.WHITE;

        if (!gear.meetsStatRequirements(data) || gear.level > data.getLevel()) {
            reqColor = Formatting.RED;
            reqNumberColor = Formatting.RED;
        }

        FinalizedGearStatReq req = gear.getStatRequirements();
        String reqtext = reqColor + "(Level " + reqNumberColor + gear.level;

        int dex = req.dexterity;
        int str = req.strength;
        int intr = req.intelligence;

        if (str > 0) {
            reqtext += reqColor + ", STR " + reqNumberColor + "" + str;
        }
        if (intr > 0) {
            reqtext += reqColor + ", INT " + reqNumberColor + "" + intr;
        }
        if (dex > 0) {
            reqtext += reqColor + ", DEX " + reqNumberColor + "" + dex;
        }
        reqtext += ")";

        tip.add(new SText(""));
        tip.add(new SText(reqtext));
        tip.add(new SText(""));

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

        MergedStats merged = new MergedStats(stats, info);
        list.add(merged);

        int n = 0;
        for (IGearPartTooltip part : list) {
            if (part != null) {
                tip.addAll(part.GetTooltipString(info, gear));

                if (n == list.size() - 1) {
                    for (int i = 0; i < gear.affixes.getNumberOfEmptySockets(); i++) {
                        tip.add(new SText(Formatting.YELLOW + "[Socket]"));
                    }
                }

                tip.add(new LiteralText(""));
            }
            n++;
        }

        if (Screen.hasShiftDown()) {
            if (!gear.isSalvagable) {
                tip.add(Styles.REDCOMP()
                    .append(Words.Unsalvagable.locName()));
            }
        }

        if (gear.is_unique) {
            IUnique unique = gear.uniqueStats.getUnique();

            String uniqdesc = CLOC.translate(unique.locDesc());

            List<String> lores = TooltipUtils.cutIfTooLong(uniqdesc);
            tip.add(new LiteralText(""));

            int i = 0;
            for (String desc : lores) {

                MutableText comp = new LiteralText(gear.getRarity()
                    .textFormatting() + "");

                if (i == 0) {
                    comp.append("'");
                }
                comp.append(desc);

                if (i == lores.size() - 1) {
                    comp.append("'");
                }
                i++;
                tip.add(comp);

            }
        }

        tip.add(new SText(""));

        GearRarity rarity = gear.getRarity();
        tip.add(TooltipUtils.rarity(rarity));

        int jewels = gear.affixes.getSocketedJewelsCount();

        if (jewels > 0) {
            tip.add(new SText(Formatting.LIGHT_PURPLE + "Socketed [" + jewels + "]"));
        }

        //tip.add(new SText(""));

        tip.add(new SText(Formatting.GRAY + "Durability: " + (stack.getMaxDamage() - stack.getDamage()) + "/" + stack.getMaxDamage()));

        //tip.add(new SText(""));

        if (Screen.hasShiftDown() == false) {
            tooltip
                .add(Styles.BLUECOMP()
                    .append(CLOC.tooltip("press_shift_more_info")));
        }

        List<Text> tool = TooltipUtils.removeDoubleBlankLines(tip,
            ClientConfigs.INSTANCE.REMOVE_EMPTY_TOOLTIP_LINES_IF_MORE_THAN_X_LINES);

        tip.clear();
        tip.addAll(tool);

    }
}
