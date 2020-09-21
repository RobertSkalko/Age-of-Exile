package com.robertx22.age_of_exile.event_hooks.ontick;

import com.robertx22.age_of_exile.database.data.exile_effects.ExileStatusEffect;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.entity.LivingEntity;

public class OnClientEntityTick extends EventConsumer<ExileEvents.OnEntityTick> {

    @Override
    public void accept(ExileEvents.OnEntityTick event) {

        if (event.entity.world.isClient) {
            LivingEntity x = event.entity;

            (x).getStatusEffects()
                .forEach(effect -> {
                    if (effect.getEffectType() instanceof ExileStatusEffect) {
                        ExileStatusEffect es = (ExileStatusEffect) effect.getEffectType();
                        es.applyUpdateEffect(x, x.getStatusEffect(es)
                            .getAmplifier());
                    }
                });

        }
    }
}