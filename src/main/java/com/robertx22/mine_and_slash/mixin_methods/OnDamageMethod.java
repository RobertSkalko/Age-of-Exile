package com.robertx22.mine_and_slash.mixin_methods;

import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.uncommon.effectdatas.LivingHurtEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

public class OnDamageMethod {

    public static float on$damage(float amount, DamageSource source, LivingEntity entity) {

        float modified = amount;

        if (entity.world.isClient) {
            return modified;
        }

        MMORPG.mixinLog("before dmg hook: " + modified);
        modified = OnHurtEvent.onHurtEvent(new LivingHurtEvent(entity, source, modified));
        MMORPG.mixinLog("after dmg hook: " + modified);

        return modified;
    }
}
