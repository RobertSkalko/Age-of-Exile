package com.robertx22.mine_and_slash.event_hooks.my_events;

import com.robertx22.exile_lib.events.base.EventConsumer;
import com.robertx22.exile_lib.events.base.ExileEvents;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.MyDamageSource;
import com.robertx22.mine_and_slash.mixin_methods.OnHurtEvent;
import com.robertx22.mine_and_slash.uncommon.effectdatas.LivingHurtEvent;
import net.minecraft.entity.player.PlayerEntity;

public class OnNonPlayerDamageEntityEvent extends EventConsumer<ExileEvents.OnDamageEntity> {

    @Override
    public void accept(ExileEvents.OnDamageEntity event) {
        if (event.mob.world.isClient) {
            return;
        }
        if (event.source instanceof MyDamageSource) {
            return;
        }
        if (!(event.source.getAttacker() instanceof PlayerEntity)) {
            OnHurtEvent.onHurtEvent(new LivingHurtEvent(event.mob, event.source, event.damage));
        }
    }
}
