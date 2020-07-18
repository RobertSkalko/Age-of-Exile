package com.robertx22.mine_and_slash.event_hooks.item;

import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.mine_and_slash.capability.entity.EntityCap.UnitData;
import com.robertx22.mine_and_slash.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipContext;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.saveclasses.unit.Unit;
import com.robertx22.mine_and_slash.uncommon.datasaving.Gear;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.mine_and_slash.uncommon.localization.CLOC;
import com.robertx22.mine_and_slash.uncommon.localization.Styles;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.mine_and_slash.uncommon.wrappers.SText;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;


import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OnTooltip {

    @Environment(EnvType.CLIENT)
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onItemTooltip(ItemTooltipEvent event) {

        buildDataTootltip(event);
        buildCurrencyEffectTooltip(event);

        ItemStack stack = event.getItemStack();

    }

    private static void buildDataTootltip(ItemTooltipEvent event) {

        try {
            if (Screen.hasControlDown()) {
                GearItemData gear = Gear.Load(event.getItemStack());
                if (gear != null) {
                    return;
                }
            }

            if (event.getPlayer() == null || event.getPlayer().world == null || !event.getPlayer().world.isClient) {
                return;
            }

            ItemStack stack;

            stack = event.getItemStack();

            UnitData unitdata = Load.Unit(event.getPlayer());

            if (unitdata == null) {
                return;
            }

            Unit unit = unitdata.getUnit();

            if (unit == null) {
                return;
            }

            TooltipContext ctx = new TooltipContext(stack, event.getToolTip(), unitdata);

            if (!stack.hasTag()) {
                return;
            }

            ICommonDataItem data = ICommonDataItem.load(stack);

            if (data != null) {
                data.BuildTooltip(ctx);
            } else {
                if (stack.getItem()
                    .getRegistryName() != null) {
                    if (SlashRegistry.CompatibleItems()
                        .isRegistered(stack.getItem()
                            .getRegistryName()
                            .toString())) {

                        event.getToolTip()
                            .add(new LiteralText(Styles.RED + "Compatible Mine and Slash Item"));

                    }
                }
            }

            Text broken = TooltipUtils.itemBrokenText(stack, data);
            if (broken != null) {
                event.getToolTip()
                    .add(broken);
            }

            if (data instanceof GearItemData) {
                List<String> list = event.getToolTip()
                    .stream()
                    .map(x -> CLOC.translate(x))
                    .collect(Collectors.toList());

                TextRenderer font = MinecraftClient.getInstance().textRenderer;

                int max = font.getStringWidth(list.stream()
                    .max(Comparator.comparingInt(x -> font.getStringWidth(x)))
                    .get());

                event.getToolTip()
                    .clear();

                list.forEach(x -> {

                    String str = x;

                    while (font.getStringWidth(str) <= max) {
                        str = " " + str + " ";
                    }

                    event.getToolTip()
                        .add(new SText(str));

                });

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void buildCurrencyEffectTooltip(ItemTooltipEvent event) {

        if (event.getItemStack()
            .getItem() instanceof ICurrencyItemEffect) {
            ICurrencyItemEffect currency = (ICurrencyItemEffect) event.getItemStack()
                .getItem();
            currency.addToTooltip(event.getToolTip());
        }

    }

}
