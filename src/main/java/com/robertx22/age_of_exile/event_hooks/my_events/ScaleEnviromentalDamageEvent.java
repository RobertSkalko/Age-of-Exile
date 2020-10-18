package com.robertx22.age_of_exile.event_hooks.my_events;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.event_hooks.entity.damage.LivingHurtUtils;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.entity.LivingEntity;

public class ScaleEnviromentalDamageEvent extends EventConsumer<ExileEvents.OnDamageEntity> {

    @Override
    public void accept(ExileEvents.OnDamageEntity event) {

        if (LivingHurtUtils.isEnviromentalDmg(event.source)) {
            LivingEntity target = event.mob;
            EntityCap.UnitData data = Load.Unit(event.mob);
            int lvl = data
                .getLevel();

            float dmg = Health.getInstance()
                .scale(event.damage, lvl);

            event.damage = dmg;

        }

    }
}
