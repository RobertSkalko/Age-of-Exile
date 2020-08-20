package com.robertx22.age_of_exile.vanilla_mc.potion_effects.food_effects;

import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.unit.ResourcesData;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class ManaRegenFoodEffect extends FoodEffectPotion {

    public static ManaRegenFoodEffect INSTANCE = new ManaRegenFoodEffect(3035801);

    protected ManaRegenFoodEffect(int color) {
        super(color);
    }

    @Override
    public ResourcesData.Type resourceType() {
        return ResourcesData.Type.MANA;
    }

    @Override
    public List<Text> GetTooltipString(TooltipInfo info, int duration, int amplifier) {
        List<Text> list = new ArrayList<>();
        int val = (int) getTotalRestored(info.unitdata, amplifier);
        list.add(new LiteralText("Restores " + val + " Mana over " + duration / 20 + "s").formatted(Formatting.AQUA));
        return list;
    }

}
