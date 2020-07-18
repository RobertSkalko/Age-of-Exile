package com.robertx22.mine_and_slash.event_hooks.player;

import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class OnPlayerClone {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerClone(PlayerEvent.Clone event) {

        PlayerEntity original = event.getOriginal();
        PlayerEntity current = event.getPlayer();

        try {
            EntityCap.UnitData data = Load.Unit(current);
            data.loadFromNBT(Load.Unit(original)
                .saveToNBT());
            data.syncToClient(current);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Load.spells(current)
                .loadFromNBT(Load.spells(original)
                    .saveToNBT());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Load.statPoints(current)
                .loadFromNBT(Load.statPoints(original)
                    .saveToNBT());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
