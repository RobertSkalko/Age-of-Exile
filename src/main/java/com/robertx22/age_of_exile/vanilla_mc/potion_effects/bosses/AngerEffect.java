package com.robertx22.age_of_exile.vanilla_mc.potion_effects.bosses;

import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalHit;
import com.robertx22.age_of_exile.saveclasses.spells.calc.ValueCalculationData;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.BasePotionEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.IApplyStatPotion;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.data.PotionStat;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectType;

import java.util.ArrayList;
import java.util.List;

public class AngerEffect extends BasePotionEffect implements IApplyStatPotion {
    public static final AngerEffect INSTANCE = new AngerEffect();

    protected AngerEffect() {
        super(StatusEffectType.BENEFICIAL, 50000);

        this.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "7107DE5E-5CE8-4030-940E-514C1F160890",
            0.15F, EntityAttributeModifier.Operation.MULTIPLY_TOTAL
        );
    }

    @Override
    public ValueCalculationData getCalc(LivingEntity caster) {
        return ValueCalculationData.base(5);
    }

    @Override
    public String GUID() {
        return "anger";
    }

    @Override
    public int getDurationInSeconds(LivingEntity en) {
        return 10;
    }

    @Override
    public int getTickRate(LivingEntity en) {
        return 20;
    }

    @Override
    public String locNameForLangFile() {
        return "Anger";
    }

    @Override
    public List<PotionStat> getPotionStats() {
        List<PotionStat> list = new ArrayList<>();
        list.add(new PotionStat(50, CriticalHit.getInstance()));
        list.add(new PotionStat(20, CriticalDamage.getInstance()));
        return list;
    }
}
