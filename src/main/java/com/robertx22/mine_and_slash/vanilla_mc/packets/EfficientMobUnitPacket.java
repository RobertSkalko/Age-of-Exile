package com.robertx22.mine_and_slash.vanilla_mc.packets;

import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.PacketByteBuf;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class EfficientMobUnitPacket {

    public int id;
    public CompoundTag nbt;

    public EfficientMobUnitPacket() {

    }

    public EfficientMobUnitPacket(Entity entity, EntityCap.UnitData data) {
        this.id = entity.getEntityId();
        this.nbt = data.getClientNBT();

    }

    public static EfficientMobUnitPacket decode(PacketByteBuf buf) {

        EfficientMobUnitPacket newpkt = new EfficientMobUnitPacket();

        newpkt.id = buf.readInt();
        newpkt.nbt = buf.readCompoundTag();

        return newpkt;

    }

    public static void encode(EfficientMobUnitPacket packet, PacketByteBuf tag) {

        tag.writeInt(packet.id);
        tag.writeCompoundTag(packet.nbt);

    }

    public static void handle(final EfficientMobUnitPacket pkt, Supplier<NetworkEvent.Context> ctx) {

        ctx.get()
            .enqueueWork(() -> {
                try {

                    Entity entity = MMORPG.proxy.getPlayerEntityFromContext(ctx).world.getEntityById(pkt.id);

                    if (entity instanceof LivingEntity) {

                        LivingEntity en = (LivingEntity) entity;

                        Load.Unit(en)
                            .setClientNBT(pkt.nbt);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        ctx.get()
            .setPacketHandled(true);
    }

}
