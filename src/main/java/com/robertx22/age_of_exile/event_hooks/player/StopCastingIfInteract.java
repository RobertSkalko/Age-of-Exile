package com.robertx22.age_of_exile.event_hooks.player;

import com.robertx22.age_of_exile.capability.player.EntitySpellCap;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.player.PlayerEntity;

public class StopCastingIfInteract {

    private static void stop(PlayerEntity player) {
        if (player.level.isClientSide) {
            return;
        }
        EntitySpellCap.ISpellsCap data = Load.spells(player);

        if (data.getCastingData()
                .isCasting()) {
            data.getCastingData()
                    .cancelCast(player);
            data.syncToClient(player);
        }
    }


    public static void interact(PlayerEntity playerEntity) {
        stop(playerEntity);
    }
}

