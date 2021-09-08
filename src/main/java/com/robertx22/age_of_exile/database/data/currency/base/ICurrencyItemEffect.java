package com.robertx22.age_of_exile.database.data.currency.base;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public interface ICurrencyItemEffect {

    public abstract ItemStack ModifyItem(ItemStack stack, ItemStack currency);

    public abstract List<BaseLocRequirement> requirements();

    public default float getInstability() {
        return 0;
    }

    public default float getBreakChance() {
        return 0;
    }

    public enum StationType {
        MODIFY, SOCKET
    }

    StationType forStation();

    default boolean canItemBeModified(LocReqContext context) {

        if (context.isGear()) {
            GearItemData gear = (GearItemData) context.data;
            if (gear.getInstability() >= ModConfig.get().Server.MAX_INSTABILITY) {
                return false;
            }
        }

        for (BaseLocRequirement req : requirements()) {
            if (req.isNotAllowed(context)) {
                return false;
            }

        }
        return true;
    }

    public default void addToTooltip(List<Text> tooltip) {

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
