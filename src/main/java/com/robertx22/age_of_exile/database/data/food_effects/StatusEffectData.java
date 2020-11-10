package com.robertx22.age_of_exile.database.data.food_effects;

import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.FoodExileStatusEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.compat_food_effects.FoodEffectPotion;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class StatusEffectData implements ITooltipList {

    public String effect_id;
    public int duration_in_seconds;
    public int amplifier;

    protected StatusEffectData() {

    }

    public StatusEffectData(Identifier effect_id, int duration_in_seconds, int amplifier) {
        this.effect_id = effect_id.toString();
        this.duration_in_seconds = duration_in_seconds;
        this.amplifier = amplifier;
    }

    public void apply(LivingEntity en) {
        try {
            StatusEffectInstance in = new StatusEffectInstance(getEffect(), duration_in_seconds * 20, amplifier);
            en.addStatusEffect(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public StatusEffect getEffect() {
        return Registry.STATUS_EFFECT.get(new Identifier(effect_id));
    }

    @Override
    public List<Text> GetTooltipString(TooltipInfo info) {
        List<Text> list = null;
        try {
            list = new ArrayList<>();

            StatusEffect eff = getEffect();

            if (eff instanceof FoodEffectPotion) {
                FoodEffectPotion food = (FoodEffectPotion) eff;
                list.addAll(food.GetTooltipString(info, duration_in_seconds * 20, amplifier));
            } else if (eff instanceof FoodExileStatusEffect) {
                FoodExileStatusEffect food = (FoodExileStatusEffect) eff;
                list.addAll(food.GetTooltipString(info, duration_in_seconds * 20, amplifier));

            } else {
                list.add(new LiteralText("Gives Effect: ").append(getEffect().getName())
                    .formatted(Formatting.GOLD));
                list.add(new LiteralText("Duration: " + duration_in_seconds + "s").formatted(Formatting.AQUA));
                list.add(new LiteralText("Strength: " + amplifier).formatted(Formatting.RED));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
