package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.capability.world.WorldAreas;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class RequestAreaSyncPacket extends MyPacket<RequestAreaSyncPacket> {

    public RequestAreaSyncPacket() {

    }

    private BlockPos pos;

    public RequestAreaSyncPacket(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "reqsyncarea");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        pos = tag.readBlockPos();

    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeBlockPos(pos);

    }

    @Override
    public void onReceived(PacketContext ctx) {
        try {
            final PlayerEntity player = ctx.getPlayer();

            Packets.sendToClient(player, new SyncAreaPacket(WorldAreas.getArea(player.world, pos)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public MyPacket<RequestAreaSyncPacket> newInstance() {
        return new RequestAreaSyncPacket();
    }
}