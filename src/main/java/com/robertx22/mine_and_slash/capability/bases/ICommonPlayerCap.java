package com.robertx22.mine_and_slash.capability.bases;

import com.robertx22.mine_and_slash.mmorpg.Packets;
import com.robertx22.mine_and_slash.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.mine_and_slash.vanilla_mc.packets.sync_cap.SyncCapabilityToClient;
import net.minecraft.entity.player.PlayerEntity;

public interface ICommonPlayerCap extends ICommonCap {

    PlayerCaps getCapType();

    default void syncToClient(PlayerEntity player) {
        Packets.sendToClient(player, new SyncCapabilityToClient(player, getCapType()));
    }

}
