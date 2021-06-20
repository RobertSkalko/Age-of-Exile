package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.MyPacket;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class EntityUnitPacket extends MyPacket<EntityUnitPacket> {

    public int id;
    public NbtCompound nbt;

    public EntityUnitPacket() {

    }

    public EntityUnitPacket(Entity entity) {
        this.id = entity.getEntityId();
        this.nbt = Load.Unit(entity)
            .toTag(new NbtCompound());
    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "enpack");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        id = tag.readInt();
        nbt = tag.readNbt();

    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeInt(id);
        tag.writeNbt(nbt);

    }

    @Override
    public void onReceived(PacketContext ctx) {
        Entity entity = ctx.getPlayer().world.getEntityById(id);

        if (entity instanceof LivingEntity) {

            LivingEntity en = (LivingEntity) entity;

            Load.Unit(en)
                .fromTag(nbt);
        }
    }

    @Override
    public MyPacket<EntityUnitPacket> newInstance() {
        return new EntityUnitPacket();
    }
}
