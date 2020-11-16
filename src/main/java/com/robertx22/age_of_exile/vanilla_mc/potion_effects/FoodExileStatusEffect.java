package com.robertx22.age_of_exile.vanilla_mc.potion_effects;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.player_skills.items.foods.FoodExileEffect;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FoodExileStatusEffect extends StatusEffect implements IApplyStatPotion, IOneOfATypePotion, IAutoLocName {

    FoodExileEffect effect;

    public FoodExileStatusEffect(FoodExileEffect effect) {
        super(StatusEffectType.BENEFICIAL, 0);
        this.effect = effect;

    }

    public List<Text> GetTooltipString(TooltipInfo info, int duration, int amplifier) {
        List<Text> tooltip = new ArrayList<>();
        List<OptScaleExactStat> list = getStats(amplifier);

        tooltip.add(new LiteralText(""));
        tooltip.add(new LiteralText("Exile Stats:").formatted(Formatting.AQUA));
        list.forEach(x -> tooltip.addAll(x.GetTooltipString(info)));
        return tooltip;
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {

        Load.Unit(entity)
            .forceRecalculateStats();

        super.onApplied(entity, attributes, amplifier);

    }

    @Override
    public void onRemoved(LivingEntity target, AttributeContainer attributes,
                          int amplifier) {

        try {
            Load.Unit(target)
                .forceRecalculateStats();

            super.onRemoved(target, attributes, amplifier);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void applyStats(World world, StatusEffectInstance instance, LivingEntity target) {

        List<OptScaleExactStat> list = getStats(instance.getAmplifier());

        list.forEach(x -> x.applyStats(Load.Unit(target)));
    }

    public List<OptScaleExactStat> getStats(int amplifier) {
        SkillItemTier tier = SkillItemTier.of(amplifier - 1);

        List<OptScaleExactStat> list = effect.stats.stream()
            .map(x -> {
                OptScaleExactStat stat = new OptScaleExactStat(x.first * tier.statMulti, x.getStat(), x.getModType());
                stat.scaleToLevel = x.scaleToLevel;
                return stat;

            })
            .collect(Collectors.toList());

        return list;

    }

    @Override
    public Type getOneOfATypeType() {
        return Type.FOOD_BUFF;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Potions;
    }

    @Override
    public String locNameLangFileGUID() {
        return Registry.STATUS_EFFECT.getId(this)
            .toString();
    }

    @Override
    public String locNameForLangFile() {
        return this.effect.word;
    }

    @Override
    public String GUID() {
        return "";
    }
}
