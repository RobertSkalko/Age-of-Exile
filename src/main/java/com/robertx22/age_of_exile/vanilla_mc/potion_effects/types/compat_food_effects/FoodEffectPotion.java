package com.robertx22.age_of_exile.vanilla_mc.potion_effects.types.compat_food_effects;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.EventBuilder;
import com.robertx22.age_of_exile.uncommon.effectdatas.RestoreResourceEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.RestoreType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

public abstract class FoodEffectPotion extends Effect {

    protected FoodEffectPotion(int color) {
        super(EffectType.BENEFICIAL, color);
    }

    public abstract ResourceType resourceType();

    public abstract List<ITextComponent> GetTooltipString(TooltipInfo info, int duration, int amplifier);

    public float getTotalRestored(EntityData data, int amplifier) {
        return Health.getInstance()
            .scale(amplifier, data.getLevel());
    }

    public float getValueRestoredPerRegen(EntityData data, int amplifier, int duration) {
        float total = getTotalRestored(data, amplifier);

        return total / 30F;

    }

    @Override
    public void applyEffectTick(LivingEntity en, int amplifier) {

        try {
            if (en.tickCount % 20 == 0) {

                if (en.level.isClientSide) {
                    return;
                }

                EffectInstance instance = en.getEffect(this);

                EntityData data = Load.Unit(en);

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
    public boolean isDurationEffectTick(int duration, int amplitude) {
        return duration >= 1;
    }

    @Override
    public boolean isInstantenous() {
        return false;
    }
}
