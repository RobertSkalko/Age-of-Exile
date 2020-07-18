package com.robertx22.mine_and_slash.event_hooks.world;

import com.robertx22.mine_and_slash.capability.world.AntiMobFarmCap;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

public class WorldTickMinute {

    static int ticks = 0;

    @SubscribeEvent
    public static void onTick(TickEvent.WorldTickEvent event) {

        if (event.phase == TickEvent.Phase.END) {
            if (event.side == LogicalSide.SERVER) {
                ticks++;

                if (ticks > 20 * 60) {
                    ticks = 0;

                    event.world.getCapability(AntiMobFarmCap.Data)
                        .ifPresent(x -> x.onMinutePassed());
                }
            }
        }

    }
}
