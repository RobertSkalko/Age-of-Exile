package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class EntityUnitPacket extends MyPacket<EntityUnitPacket> {

    public int id;
    public CompoundNBT nbt;

    public EntityUnitPacket() {

    }

    public EntityUnitPacket(Entity entity) {
        this.id = entity.getId();
        this.nbt = Load.Unit(entity)
            .saveToNBT();
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(Ref.MODID, "enpack");
    }

    @Override
    public void loadFromData(PacketBuffer tag) {
        id = tag.readInt();
        nbt = tag.readNbt();

    }

    @Override
    public void saveToData(PacketBuffer tag) {
        tag.writeInt(id);
        tag.writeNbt(nbt);

    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        Entity entity = ctx.getPlayer().level.getEntity(id);

        if (entity instanceof LivingEntity) {

            LivingEntity en = (LivingEntity) entity;

            Load.Unit(en)
                .loadFromNBT(nbt);
        }
    }

    @Override
    public MyPacket<EntityUnitPacket> newInstance() {
        return new EntityUnitPacket();
    }
}
