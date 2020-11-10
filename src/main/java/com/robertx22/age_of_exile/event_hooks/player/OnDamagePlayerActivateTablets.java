package com.robertx22.age_of_exile.event_hooks.player;

import com.robertx22.age_of_exile.vanilla_mc.items.protection_tablets.ProtectionTabletUtils;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.entity.player.PlayerEntity;

public class OnDamagePlayerActivateTablets extends EventConsumer<ExileEvents.OnDamageEntity> {

    @Override
    public void accept(ExileEvents.OnDamageEntity event) {

        if (event.mob.world.isClient) {
            return;
        }

        if (event.mob instanceof PlayerEntity) {
            ProtectionTabletUtils.tryUseTablets((PlayerEntity) event.mob, event.source);
        }
    }

}
