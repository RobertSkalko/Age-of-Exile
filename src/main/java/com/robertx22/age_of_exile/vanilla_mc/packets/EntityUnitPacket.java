package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class EntityUnitPacket extends MyPacket<EntityUnitPacket> {

    public int id;
    public CompoundTag nbt;

    public EntityUnitPacket() {

    }

    public EntityUnitPacket(Entity entity) {
        this.id = entity.getEntityId();
        this.nbt = Load.Unit(entity)
            .toTag(new CompoundTag());
    }

    public EntityUnitPacket(Entity entity, UnitData data) {
        this.id = entity.getEntityId();
        this.nbt = data.toTag(new CompoundTag());
    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "enpack");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        id = tag.readInt();
        nbt = tag.readCompoundTag();

    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeInt(id);
        tag.writeCompoundTag(nbt);

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
