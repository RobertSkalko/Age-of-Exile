package com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases;

import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class OnTickAction {

    public Function<PotionContext, PotionContext> action;
    Function<TooltipInfo, List<Text>> tooltip;

    public OnTickAction(Function<PotionContext, PotionContext> action,
                        Function<TooltipInfo, List<Text>> tooltip) {

        this.action = action;
        this.tooltip = tooltip;

    }

    public final void onTick(PotionContext ctx) {
        action.apply(ctx);
    }

    public List<Text> getTooltip(TooltipInfo info, BasePotionEffect effect) {

        List<Text> list = new ArrayList<>();

        if (tooltip != null) {
            list.add(new LiteralText(Formatting.YELLOW + "Effect occurs every " + effect.getTickRate(info.player) + " ticks."));
            list.addAll(tooltip.apply(info));
        }

        return list;
    }
}
