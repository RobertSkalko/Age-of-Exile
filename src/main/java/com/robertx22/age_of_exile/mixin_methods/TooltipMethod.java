package com.robertx22.age_of_exile.mixin_methods;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.data.runewords.RuneWordItem;
import com.robertx22.age_of_exile.player_skills.items.TieredItem;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.registry.Database;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.SyncTime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

public class TooltipMethod {
    public static void getTooltip(ItemStack stack, PlayerEntity entity, ITooltipFlag tooltipContext, CallbackInfoReturnable<List<ITextComponent>> list) {

        List<ITextComponent> tooltip = list.getReturnValue();

        boolean addCurrencyTooltip = stack
            .getItem() instanceof ICurrencyItemEffect;

        PlayerEntity player = Minecraft.getInstance().player;

        try {

            if (player == null || player.level == null) {
                return;
            }

            EntityData unitdata = Load.Unit(player);

            if (unitdata == null) {
                return;
            }

            Unit unit = unitdata.getUnit();

            if (unit == null) {
                return;
            }
            if (!Database.areDatapacksLoaded(player.level)) {

                tooltip.add(new StringTextComponent("Empty MNS Registries: "));

                ExileRegistryType.getInRegisterOrder(SyncTime.ON_LOGIN)
                    .stream()
                    .filter((x) -> !Database.getRegistry(x)
                        .isRegistrationDone())
                    .forEach(e -> {
                        tooltip.add(new StringTextComponent(e.id));
                    });

                return;
            }

            com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipContext ctx = new com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipContext(stack, tooltip, unitdata);

            boolean hasdata = false;

            if (stack.getItem() instanceof RuneWordItem) {
                RuneWord word = RuneWordItem.getRuneWord(stack);
                // todo this could use some performance update
                if (word != null) {
                    for (ITextComponent txt : word
                        .getTooltip(Load.Unit(player)
                            .getLevel())
                        .build()) {
                        tooltip.add(txt);
                    }
                    return;
                }
            }
            if (stack.getItem() instanceof TieredItem) {
                TieredItem tier = (TieredItem) stack.getItem();
                tooltip.add(new StringTextComponent("Tier " + tier.tier.getDisplayTierNumber()).withStyle(TextFormatting.LIGHT_PURPLE));
            }

            if (Screen.hasControlDown()) {
                GearItemData gear = Gear.Load(stack);
                if (gear != null) {
                    return;
                }
            }

            if (stack.hasTag()) {
                ICommonDataItem data = ICommonDataItem.load(stack);
                if (data != null) {
                    data.BuildTooltip(ctx);
                    hasdata = true;
                }
                IFormattableTextComponent broken = TooltipUtils.itemBrokenText(stack, data);
                if (broken != null) {
                    tooltip.add(broken);
                }
            }

            if (!hasdata) {

                GearSlot slot = GearSlot.getSlotOf(stack.getItem());

                if (slot != null) {
                    tooltip.add(TooltipUtils.gearSlot(slot));
                }
            }

            if (addCurrencyTooltip) {
                ICurrencyItemEffect currency = (ICurrencyItemEffect) stack
                    .getItem();
                currency.addToTooltip(tooltip);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
