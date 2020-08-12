package com.robertx22.age_of_exile.vanilla_mc.potion_effects.ranger;

import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalHit;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.spells.calc.SpellCalcData;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.BasePotionEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.IApplyStatPotion;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.data.PotionStat;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class HunterInstinctEffect extends BasePotionEffect implements IApplyStatPotion {

    private HunterInstinctEffect() {
        super(StatusEffectType.BENEFICIAL, 4393423);

    }

    public static HunterInstinctEffect getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String GUID() {
        return "hunter_instinct";
    }

    @Override
    public String locNameForLangFile() {
        return "Hunter Instinct";
    }

    @Override
    public int getMaxStacks() {
        return 20;
    }

    @Override
    public List<PotionStat> getPotionStats() {
        List<PotionStat> list = new ArrayList<>();
        list.add(new PotionStat(0.75F, CriticalHit.getInstance()));
        list.add(new PotionStat(10, DodgeRating.getInstance()));
        return list;
    }

    @Override
    public SpellCalcData getCalc(LivingEntity caster) {
        return SpellCalcData.base(0);
    }

    @Override
    public int getDurationInSeconds(LivingEntity en) {
        return 30;
    }

    @Override
    public int getTickRate(LivingEntity en) {
        return 10000;
    }

    @Override
    public List<Text> getEffectTooltip(TooltipInfo info) {
        List<Text> list = new ArrayList<>();

        return list;

    }

    private static class SingletonHolder {
        private static final HunterInstinctEffect INSTANCE = new HunterInstinctEffect();
    }
}


