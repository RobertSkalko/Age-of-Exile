package com.robertx22.age_of_exile.mixin_methods;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.food_effects.FoodEffect;
import com.robertx22.age_of_exile.database.data.food_effects.FoodEffectUtils;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.age_of_exile.uncommon.utilityclasses.CompatibleItemUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.age_of_exile.uncommon.wrappers.SText;
import com.robertx22.library_of_exile.utils.CLOC;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TooltipMethod {
    public static void getTooltip(ItemStack stack, PlayerEntity entity, TooltipContext tooltipContext, CallbackInfoReturnable<List<Text>> list) {

        List<Text> tooltip = list.getReturnValue();

        boolean addCurrencyTooltip = stack
            .getItem() instanceof ICurrencyItemEffect;

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

            EntityCap.UnitData unitdata = Load.Unit(player);

            if (unitdata == null) {
                return;
            }

            Unit unit = unitdata.getUnit();

            if (unit == null) {
                return;
            }
            if (!Database.areDatapacksLoaded(player.world)) {
                return;
            }

            if (FoodEffectUtils.isFood(stack.getItem())) {

                FoodEffect effect = FoodEffectUtils.getEffect(stack.getItem());

                if (effect != null) {
                    tooltip.addAll(effect.GetTooltipString(new TooltipInfo(player)));
                }
            }

            com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipContext ctx = new com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipContext(stack, tooltip, unitdata);

            if (stack.hasTag()) {

                ICommonDataItem data = ICommonDataItem.load(stack);

                if (data != null) {
                    data.BuildTooltip(ctx);
                } else {

                    CompatibleItemUtils.Data cdata = CompatibleItemUtils.getData(player.world, stack.getItem());

                    if (cdata.isCompatible) {
                        int mini = cdata.minLevel;
                        int maxi = cdata.maxLevel;
                        tooltip.add(new LiteralText("Level: " + mini + " - " + maxi));
                    }
                }

                MutableText broken = TooltipUtils.itemBrokenText(stack, data);
                if (broken != null) {
                    tooltip.add(broken);
                }

                if (data instanceof GearItemData) {
                    if (false) { // tooltip centering
                        List<String> strings = tooltip
                            .stream()
                            .map(x -> CLOC.translate(x))
                            .collect(Collectors.toList());

                        TextRenderer font = MinecraftClient.getInstance().textRenderer;

                        int max = font.getWidth(strings.stream()
                            .max(Comparator.comparingInt(x -> font.getWidth(x)))
                            .get());

                        tooltip.clear();

                        strings.forEach(x -> {

                            String str = x;

                            while (font.getWidth(str) <= max) {
                                str = " " + str + " ";
                            }

                            tooltip
                                .add(new SText(str));

                        });
                    }
                }
            }

            if (addCurrencyTooltip) {
                ICurrencyItemEffect currency = (ICurrencyItemEffect) stack
                    .getItem();
                currency.addToTooltip(tooltip);
            }

            /*
            if (stack.getItem() instanceof IReqSkillLevel) {
                IReqSkillLevel req = (IReqSkillLevel) stack.getItem();
                tooltip.add(Words.RequiresLevel.locName()
                    .append(": " + req.getSkillLevelRequired() + " ")
                    .append(req.getItemSkillType().word.locName())
                    .formatted(Formatting.RED));
            }

             */

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
