package com.robertx22.age_of_exile.damage_hooks;

import com.robertx22.age_of_exile.damage_hooks.util.AttackInformation;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.MyDamageSource;
import com.robertx22.age_of_exile.dimension.rules.OnDmgDisableEnviroDmg;
import com.robertx22.age_of_exile.mixin_methods.OnHurtEvent;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
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
        if (LivingHurtUtils.isEnviromentalDmg(event.source)) {
            OnDmgDisableEnviroDmg.accept(event);
            return;
        }
        if (!(event.source.getAttacker() instanceof PlayerEntity)) {

            // todo, i'm not sure if i want to override vanila damage or keep using both...
            OnHurtEvent.onHurtEvent(new AttackInformation(AttackInformation.Mitigation.PRE, event.mob, event.source, event.damage));
        }
    }
}
