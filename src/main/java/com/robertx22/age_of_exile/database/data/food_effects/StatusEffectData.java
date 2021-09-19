package com.robertx22.age_of_exile.database.data.food_effects;

import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.types.FoodExileStatusEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.types.compat_food_effects.FoodEffectPotion;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class StatusEffectData implements ITooltipList {

    public String effect_id;
    public int duration_in_seconds;
    public int amplifier;

    protected StatusEffectData() {

    }

    public StatusEffectData(ResourceLocation effect_id, int duration_in_seconds, int amplifier) {
        this.effect_id = effect_id.toString();
        this.duration_in_seconds = duration_in_seconds;
        this.amplifier = amplifier;
    }

    public void apply(LivingEntity en) {
        try {
            EffectInstance in = new EffectInstance(getEffect(), duration_in_seconds * 20, amplifier);
            en.addEffect(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Effect getEffect() {
        return Registry.MOB_EFFECT.get(new ResourceLocation(effect_id));
    }

    @Override
    public List<ITextComponent> GetTooltipString(TooltipInfo info) {
        List<ITextComponent> list = null;
        try {
            list = new ArrayList<>();

            Effect eff = getEffect();

            if (eff instanceof FoodEffectPotion) {
                FoodEffectPotion food = (FoodEffectPotion) eff;
                list.addAll(food.GetTooltipString(info, duration_in_seconds * 20, amplifier));
            } else if (eff instanceof FoodExileStatusEffect) {
                FoodExileStatusEffect food = (FoodExileStatusEffect) eff;
                list.addAll(food.GetTooltipString(info, duration_in_seconds * 20, amplifier));

            } else {
                list.add(new StringTextComponent("Gives Effect: ").append(getEffect().getDisplayName())
                    .withStyle(TextFormatting.GOLD));
                list.add(new StringTextComponent("Duration: " + duration_in_seconds + "s").withStyle(TextFormatting.AQUA));
                list.add(new StringTextComponent("Strength: " + amplifier).withStyle(TextFormatting.RED));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
