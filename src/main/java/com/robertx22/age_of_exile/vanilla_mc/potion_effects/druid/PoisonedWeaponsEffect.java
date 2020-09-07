package com.robertx22.age_of_exile.vanilla_mc.potion_effects.druid;

import com.robertx22.age_of_exile.database.data.stats.types.generated.WeaponDamage;
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

public class PoisonedWeaponsEffect extends BasePotionEffect implements IApplyStatPotion {

    private PoisonedWeaponsEffect() {
        super(StatusEffectType.BENEFICIAL, 4393423);

    }

    public static PoisonedWeaponsEffect getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String GUID() {
        return "poisoned_weapons";
    }

    @Override
    public String locNameForLangFile() {
        return "Poison Weapon";
    }

    @Override
    public int getMaxStacks() {
        return 1;
    }

    @Override
    public List<PotionStat> getPotionStats() {
        List<PotionStat> list = new ArrayList<>();
        list.add(new PotionStat(3, new WeaponDamage(Elements.Nature)));
        return list;
    }

    @Override
    public List<Text> getEffectTooltip(TooltipInfo info) {
        List<Text> list = new ArrayList<>();

        return list;

    }

    @Override
    public ValueCalculationData getCalc(LivingEntity caster) {
        return ValueCalculationData.base(4);
    }

    @Override
    public int getDurationInSeconds(LivingEntity en) {
        return 30;
    }

    @Override
    public int getTickRate(LivingEntity en) {
        return 10000;
    }

    private static class SingletonHolder {
        private static final PoisonedWeaponsEffect INSTANCE = new PoisonedWeaponsEffect();
    }
}

