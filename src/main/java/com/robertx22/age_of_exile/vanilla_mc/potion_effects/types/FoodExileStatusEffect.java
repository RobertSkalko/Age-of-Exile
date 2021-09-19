package com.robertx22.age_of_exile.vanilla_mc.potion_effects.types;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.player_skills.items.foods.FoodExileEffect;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IApplyableStats;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.SimpleStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.IOneOfATypePotion;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FoodExileStatusEffect extends Effect implements IApplyableStats, IOneOfATypePotion, IAutoLocName {

    FoodExileEffect effect;

    public FoodExileStatusEffect(FoodExileEffect effect) {
        super(EffectType.BENEFICIAL, 0);
        this.effect = effect;

    }

    public List<ITextComponent> GetTooltipString(TooltipInfo info, int duration, int amplifier) {
        List<ITextComponent> tooltip = new ArrayList<>();
        List<OptScaleExactStat> list = getStats(amplifier);

        tooltip.add(new StringTextComponent(""));
        tooltip.add(new StringTextComponent("Exile Stats:").withStyle(TextFormatting.AQUA));
        list.forEach(x -> tooltip.addAll(x.GetTooltipString(info)));
        return tooltip;
    }

    @Override
    public void addAttributeModifiers(LivingEntity entity, AttributeMap attributes, int amplifier) {

        Load.Unit(entity)
            .forceRecalculateStats();

        super.addAttributeModifiers(entity, attributes, amplifier);

    }

    @Override
    public void removeAttributeModifiers(LivingEntity target, AttributeMap attributes,
                                         int amplifier) {

        try {
            Load.Unit(target)
                .forceRecalculateStats();

            super.removeAttributeModifiers(target, attributes, amplifier);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<OptScaleExactStat> getStats(int amplifier) {
        SkillItemTier tier = SkillItemTier.of(amplifier - 1);

        List<OptScaleExactStat> list = effect.stats.stream()
            .map(x -> {
                OptScaleExactStat stat = new OptScaleExactStat(x.v1 * tier.statMulti, x.getStat(), x.getModType());
                stat.scale_to_lvl = x.scale_to_lvl;
                return stat;

            })
            .collect(Collectors.toList());

        return list;

    }

    @Override
    public String getOneOfATypeType() {
        return effect.getOneOfAKindType();
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Potions;
    }

    @Override
    public String locNameLangFileGUID() {
        return Registry.MOB_EFFECT.getKey(this)
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

    @Override
    public List<StatContext> getStatAndContext(LivingEntity en) {
        EffectInstance instance = en.getEffect(this);

        return Arrays.asList(new SimpleStatCtx(
            StatContext.StatCtxType.FOOD_BUFF,
            getStats(instance.getAmplifier()).stream()
                .map(x -> x.toExactStat(Load.Unit(en)
                    .getLevel()))
                .collect(Collectors.toList())));

    }
}
