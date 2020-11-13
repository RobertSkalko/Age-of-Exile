package com.robertx22.age_of_exile.event_hooks.my_events;

import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.MyDamageSource;
import com.robertx22.age_of_exile.event_hooks.entity.damage.LivingHurtUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.HealthUtils;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class ScaleVanillaMobDamage extends EventConsumer<ExileEvents.OnDamageEntity> {

    @Override
    public void accept(ExileEvents.OnDamageEntity event) {
        if (event.mob.world.isClient) {
            return;
        }
        if (event.source instanceof MyDamageSource) {
            return;
        }
        if (LivingHurtUtils.isEnviromentalDmg(event.source)) {
            return;
        }
        if (event.source.getAttacker() instanceof LivingEntity) {
            if (!(event.source.getAttacker() instanceof PlayerEntity)) {
                event.damage = HealthUtils.realToVanilla(event.mob, event.damage);
            }
        }
    }
}

