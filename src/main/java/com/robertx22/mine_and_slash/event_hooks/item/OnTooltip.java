package com.robertx22.mine_and_slash.event_hooks.item;

import com.robertx22.mine_and_slash.capability.entity.EntityCap.UnitData;
import com.robertx22.mine_and_slash.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipContext;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.saveclasses.unit.Unit;
import com.robertx22.mine_and_slash.uncommon.datasaving.Gear;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.mine_and_slash.uncommon.localization.CLOC;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.mine_and_slash.uncommon.wrappers.SText;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OnTooltip implements ItemTooltipCallback {

    @Override
    public void getTooltip(ItemStack stack, net.minecraft.client.item.TooltipContext tooltipContext, List<MutableText> tooltip) {

        if (stack
            .getItem() instanceof ICurrencyItemEffect) {
            ICurrencyItemEffect currency = (ICurrencyItemEffect) stack
                .getItem();
            currency.addToTooltip(tooltip);
            return;
        }

        PlayerEntity player = MinecraftClient.getInstance().player;

        try {
            if (Screen.hasControlDown()) {
                GearItemData gear = Gear.Load(stack);
                if (gear != null) {
                    return;
                }
            }

            if (player == null || player.world == null) {
                return;
            }

            UnitData unitdata = Load.Unit(player);

            if (unitdata == null) {
                return;
            }

            Unit unit = unitdata.getUnit();

            if (unit == null) {
                return;
            }

            TooltipContext ctx = new TooltipContext(stack, tooltip, unitdata);

            if (!stack.hasTag()) {
                return;
            }

            ICommonDataItem data = ICommonDataItem.load(stack);

            if (data != null) {
                data.BuildTooltip(ctx);
            }

            MutableText broken = TooltipUtils.itemBrokenText(stack, data);
            if (broken != null) {
                tooltip.add(broken);
            }

            if (data instanceof GearItemData) {
                List<String> strings = tooltip
                    .stream()
                    .map(x -> CLOC.translate(x))
                    .collect(Collectors.toList());

                TextRenderer font = MinecraftClient.getInstance().textRenderer;

                int max = font.getStringWidth(strings.stream()
                    .max(Comparator.comparingInt(x -> font.getStringWidth(x)))
                    .get());

                tooltip.clear();

                strings.forEach(x -> {

                    String str = x;

                    while (font.getStringWidth(str) <= max) {
                        str = " " + str + " ";
                    }

                    tooltip
                        .add(new SText(str));

                });

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
