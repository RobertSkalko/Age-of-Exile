package com.robertx22.age_of_exile.vanilla_mc.potion_effects.compat_food_effects;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.EventBuilder;
import com.robertx22.age_of_exile.uncommon.effectdatas.RestoreResourceEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.RestoreType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.text.Text;

import java.util.List;

public abstract class FoodEffectPotion extends StatusEffect {

    protected FoodEffectPotion(int color) {
        super(StatusEffectType.BENEFICIAL, color);
    }

    public abstract ResourceType resourceType();

    public abstract List<Text> GetTooltipString(TooltipInfo info, int duration, int amplifier);

    public float getTotalRestored(EntityCap.UnitData data, int amplifier) {
        return Health.getInstance()
            .scale(amplifier, data.getLevel());
    }

    public float getValueRestoredPerRegen(EntityCap.UnitData data, int amplifier, int duration) {
        float total = getTotalRestored(data, amplifier);

        return total / 30F;

    }

    @Override
    public void applyUpdateEffect(LivingEntity en, int amplifier) {

        try {
            if (en.age % 20 == 0) {

                if (en.world.isClient) {
                    return;
                }

                StatusEffectInstance instance = en.getStatusEffect(this);

                EntityCap.UnitData data = Load.Unit(en);

                float heal = getValueRestoredPerRegen(data, amplifier, instance.getDuration());

                RestoreResourceEvent restore = EventBuilder.ofRestore(en, en, resourceType(), RestoreType.food, heal)
                    .build();

                restore.Activate();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplitude) {
        return duration >= 1;
    }

    @Override
    public boolean isInstant() {
        return false;
    }
}
