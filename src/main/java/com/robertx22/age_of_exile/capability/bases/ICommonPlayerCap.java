package com.robertx22.age_of_exile.capability.bases;

import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.SyncCapabilityToClient;
import net.minecraft.entity.player.PlayerEntity;

public interface ICommonPlayerCap extends ICommonCap {

    PlayerCaps getCapType();

    default void syncToClient(PlayerEntity player) {
        Packets.sendToClient(player, new SyncCapabilityToClient(player, getCapType()));
    }

}
