package com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.library_of_exile.main.MyPacket;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class SyncCapabilityToClient extends MyPacket<SyncCapabilityToClient> {

    public SyncCapabilityToClient() {

    }

    private NbtCompound nbt;
    private PlayerCaps type;

    public SyncCapabilityToClient(PlayerEntity p, PlayerCaps type) {
        this.nbt = type.getCap(p)
            .toTag(new NbtCompound());
        this.type = type;
    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "synccaptoc");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        nbt = tag.readNbt();
        type = tag.readEnumConstant(PlayerCaps.class);

    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        try {
            tag.writeNbt(nbt);
            tag.writeEnumConstant(type);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

