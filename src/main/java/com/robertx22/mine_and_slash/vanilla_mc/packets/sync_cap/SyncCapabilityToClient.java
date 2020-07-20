package com.robertx22.mine_and_slash.vanilla_mc.packets.sync_cap;

import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.vanilla_mc.packets.MyPacket;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class SyncCapabilityToClient extends MyPacket<SyncCapabilityToClient> {

    public SyncCapabilityToClient() {

    }

    private CompoundTag nbt;
    private PlayerCaps type;

    public SyncCapabilityToClient(PlayerEntity p, PlayerCaps type) {
        this.nbt = type.getCap(p)
            .toTag();
        this.type = type;
    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "synccaptoc");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        nbt = tag.readCompoundTag();
        type = tag.readEnumConstant(PlayerCaps.class);

    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeCompoundTag(nbt);
        tag.writeEnumConstant(type);

    }

    @Override
    public void onReceived(PacketContext ctx) {
        final PlayerEntity player = ctx.getPlayer();

        if (player != null) {
            type.getCap(player)
                .fromTag(nbt);

        }
    }

    @Override
    public MyPacket<SyncCapabilityToClient> newInstance() {
        return new SyncCapabilityToClient();
    }
}

