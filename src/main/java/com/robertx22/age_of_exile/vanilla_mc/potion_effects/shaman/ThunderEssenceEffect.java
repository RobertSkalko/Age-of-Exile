package com.robertx22.age_of_exile.vanilla_mc.potion_effects.shaman;

import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalDamageBonus;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalHit;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.spells.calc.ValueCalculationData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.BasePotionEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.IApplyStatPotion;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.data.PotionStat;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ThunderEssenceEffect extends BasePotionEffect implements IApplyStatPotion {

    public static final ThunderEssenceEffect INSTANCE = new ThunderEssenceEffect();

    private ThunderEssenceEffect() {
        super(StatusEffectType.BENEFICIAL, 4393423);

    }

    @Override
    public String GUID() {
        return "thunder_essence";
    }

    @Override
    public String locNameForLangFile() {
        return "Thunder Essence";
    }

    @Override
    public int getMaxStacks() {
        return 20;
    }

    @Override
    public List<PotionStat> getPotionStats() {
        List<PotionStat> list = new ArrayList<>();
        list.add(new PotionStat(1, new ElementalDamageBonus(Elements.Thunder)));
        list.add(new PotionStat(1, CriticalHit.getInstance()));
        return list;
    }

    @Override
    public ValueCalculationData getCalc(LivingEntity caster) {
        return ValueCalculationData.base(0);
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

}