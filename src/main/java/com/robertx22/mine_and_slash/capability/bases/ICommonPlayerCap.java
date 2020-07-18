package com.robertx22.mine_and_slash.capability.bases;

import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.mine_and_slash.vanilla_mc.packets.sync_cap.SyncCapabilityToClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public interface ICommonPlayerCap extends ICommonCap {

    PlayerCaps getCapType();

    default void syncToClient(PlayerEntity player) {
        MMORPG.sendToClient(
            new SyncCapabilityToClient((ServerPlayerEntity) player, getCapType()), (ServerPlayerEntity) player);
    }

}
