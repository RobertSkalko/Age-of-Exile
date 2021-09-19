package com.robertx22.age_of_exile.vanilla_mc.potion_effects.types.compat_food_effects;

import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class HealthRegenFoodEffect extends FoodEffectPotion {

    public static HealthRegenFoodEffect INSTANCE = new HealthRegenFoodEffect(14981690);

    protected HealthRegenFoodEffect(int color) {
        super(color);
    }

    @Override
    public ResourceType resourceType() {
        return ResourceType.health;
    }

    @Override
    public List<ITextComponent> GetTooltipString(TooltipInfo info, int duration, int amplifier) {
        List<ITextComponent> list = new ArrayList<>();
        int val = (int) getTotalRestored(info.unitdata, amplifier);
        list.add(new StringTextComponent("Restores " + val + " Health over " + duration / 20 + "s").withStyle(TextFormatting.RED));
        return list;
    }
}
