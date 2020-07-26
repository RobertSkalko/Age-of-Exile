package com.robertx22.mine_and_slash.event_hooks.my_events;

import com.robertx22.exiled_lib.events.base.ExileEvents;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.MyDamageSource;
import com.robertx22.mine_and_slash.mixin_methods.OnHurtEvent;
import com.robertx22.mine_and_slash.uncommon.effectdatas.LivingHurtEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;

public class OnPlayerDamageEntityEvent extends ExileEvents.OnDamageEntity {

    @Override
    public void onDamage(LivingEntity entity, float damage, DamageSource source, ExileEvents.DamageData data) {

        if (entity.world.isClient) {
            return;
        }
        if (source instanceof MyDamageSource) {
            return;
        }
        if (source.getAttacker() instanceof PlayerEntity) {
            OnHurtEvent.onHurtEvent(new LivingHurtEvent(entity, source, damage));
        }
    }
}
