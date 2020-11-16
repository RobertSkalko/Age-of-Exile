package com.robertx22.age_of_exile.event_hooks.my_events;

import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.MyDamageSource;
import com.robertx22.age_of_exile.mixin_methods.OnHurtEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackInformation;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.entity.player.PlayerEntity;

public class OnPlayerDamageEntityEvent extends EventConsumer<ExileEvents.OnDamageEntity> {

    @Override
    public void accept(ExileEvents.OnDamageEntity event) {

        if (event.mob.world.isClient) {
            return;
        }
        if (event.source instanceof MyDamageSource) {
            return;
        }
        if (event.source.getAttacker() instanceof PlayerEntity) {
            OnHurtEvent.onHurtEvent(new AttackInformation(AttackInformation.Mitigation.POST, event.mob, event.source, event.damage));
        }
    }

}
