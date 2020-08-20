package com.robertx22.age_of_exile.vanilla_mc.potion_effects.ocean_mystic;

import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.spells.calc.SpellCalcData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ParticleUtils;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.BasePotionEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.IApplyStatPotion;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.OnTickAction;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.data.PotionStat;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class FrostEffect extends BasePotionEffect implements IApplyStatPotion {

    public static final FrostEffect INSTANCE = new FrostEffect();

    private FrostEffect() {
        super(StatusEffectType.HARMFUL, 4393423);

        this.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890",
            (double) -0.05F, EntityAttributeModifier.Operation.MULTIPLY_TOTAL
        );

        this.tickActions.add(new OnTickAction(ctx -> {
            ParticleUtils.spawnParticles(ParticleTypes.ITEM_SNOWBALL, ctx.entity, 15);
            return ctx;
        }, null));

    }

    @Override
    public String GUID() {
        return "frost";
    }

    @Override
    public String locNameForLangFile() {
        return "Chill";
    }

    @Override
    public int getMaxStacks() {
        return 5;
    }

    @Override
    public List<PotionStat> getPotionStats() {
        List<PotionStat> list = new ArrayList<>();
        list.add(new PotionStat(-15, new ElementalResist(Elements.Water)));
        list.add(new PotionStat(-15, new ElementalResist(Elements.Fire)));
        return list;
    }

    @Override
    public SpellCalcData getCalc(LivingEntity caster) {
        return SpellCalcData.base(0);
    }

    @Override
    public int getDurationInSeconds(LivingEntity en) {
        return 20;
    }

    @Override
    public int getTickRate(LivingEntity en) {
        return 20;
    }

    @Override
    public List<Text> getEffectTooltip(TooltipInfo info) {
        List<Text> list = new ArrayList<>();

        list.add(new LiteralText("Slows"));

        return list;

    }

}

