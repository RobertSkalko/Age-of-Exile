package com.robertx22.mine_and_slash.event_hooks.entity;

import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class OnPlayerAtkEntity {

    @SubscribeEvent
    public static void event(AttackEntityEvent event) {
        Load.Unit(event.getPlayer())
            .setCooledAttackStrength(event.getPlayer()
                .getAttackCooldownProgress(0.5F));

    }

}
