package com.robertx22.age_of_exile.vanilla_mc.potion_effects.shaman;

import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.spells.calc.SpellCalcData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ParticleUtils;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.BasePotionEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.IApplyStatPotion;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.OnTickAction;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.data.PotionStat;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class StaticEffect extends BasePotionEffect implements IApplyStatPotion {

    public static final StaticEffect INSTANCE = new StaticEffect();

    private StaticEffect() {
        super(StatusEffectType.HARMFUL, 4393423);

        this.tickActions.add(new OnTickAction(ctx -> {
            ParticleUtils.spawnParticles(ModRegistry.PARTICLES.THUNDER, ctx.entity, 50);
            return ctx;
        }, null));
    }

    @Override
    public String GUID() {
        return "static";
    }

    @Override
    public String locNameForLangFile() {
        return "Static";
    }

    @Override
    public int getMaxStacks() {
        return 3;
    }

    @Override
    public List<PotionStat> getPotionStats() {
        List<PotionStat> list = new ArrayList<>();
        list.add(new PotionStat(-15, new ElementalResist(Elements.Thunder)));
        list.add(new PotionStat(-15, new ElementalResist(Elements.Nature)));
        return list;
    }

    @Override
    public SpellCalcData getCalc(LivingEntity caster) {
        return SpellCalcData.base(0);
    }

    @Override
    public int getDurationInSeconds(LivingEntity en) {
        return 10;
    }

    @Override
    public int getTickRate(LivingEntity en) {
        return 10;
    }

    @Override
    public List<Text> getEffectTooltip(TooltipInfo info) {
        List<Text> list = new ArrayList<>();

        list.add(new LiteralText("Slows and Reduces Resistances;"));

        return list;

    }

}
