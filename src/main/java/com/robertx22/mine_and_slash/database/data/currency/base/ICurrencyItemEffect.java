package com.robertx22.mine_and_slash.database.data.currency.base;

import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;
import java.util.List;

public interface ICurrencyItemEffect {

    public abstract ItemStack ModifyItem(ItemStack stack, ItemStack currency);

    public abstract List<BaseLocRequirement> requirements();

    default boolean canItemBeModified(LocReqContext context) {

        for (BaseLocRequirement req : requirements()) {
            if (req.isNotAllowed(context)) {
                return false;
            }

        }
        return true;
    }

    public default void addToTooltip(List<MutableText> tooltip) {

        if (Screen.hasShiftDown()) {
            tooltip.add(TooltipUtils.color(Formatting.RED, Words.Requirements.locName()
                .append(": ")));

            for (BaseLocRequirement req : requirements()) {
                tooltip.add(TooltipUtils.color(Formatting.RED,
                    new LiteralText(" * ").append(req.getText())
                ));
            }
        } else {
            tooltip.add(TooltipUtils.color(Formatting.GREEN, Words.PressShiftForRequirements.locName()));

        }
    }

}
