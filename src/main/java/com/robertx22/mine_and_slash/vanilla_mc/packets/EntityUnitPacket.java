package com.robertx22.mine_and_slash.vanilla_mc.packets;

import com.robertx22.mine_and_slash.capability.entity.EntityCap.UnitData;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.PacketByteBuf;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class EntityUnitPacket {

    public int id;
    public CompoundTag nbt;

    public EntityUnitPacket() {

    }

    public EntityUnitPacket(Entity entity) {
        this.id = entity.getEntityId();
        this.nbt = Load.Unit(entity)
            .saveToNBT();
    }

    public EntityUnitPacket(Entity entity, UnitData data) {
        this.id = entity.getEntityId();
        this.nbt = data.saveToNBT();
    }

    public static EntityUnitPacket decode(PacketByteBuf buf) {

        EntityUnitPacket newpkt = new EntityUnitPacket();

        newpkt.id = buf.readInt();
        newpkt.nbt = buf.readCompoundTag();

        return newpkt;

    }

    public static void encode(EntityUnitPacket packet, PacketByteBuf tag) {

        tag.writeInt(packet.id);
        tag.writeCompoundTag(packet.nbt);

        //System.out.println("old uses " + tag.writerIndex());

    }

    public static void handle(final EntityUnitPacket pkt, Supplier<NetworkEvent.Context> ctx) {

        ctx.get()
            .enqueueWork(() -> {
                try {

                    Entity entity = MMORPG.proxy.getPlayerEntityFromContext(ctx).world.getEntityById(pkt.id);

                    if (entity instanceof LivingEntity) {

                        LivingEntity en = (LivingEntity) entity;

                        Load.Unit(en)
                            .loadFromNBT(pkt.nbt);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        ctx.get()
            .setPacketHandled(true);
    }

}
