package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class EfficientMobUnitPacket extends MyPacket<EfficientMobUnitPacket> {

    public int id;
    public CompoundNBT nbt;

    public EfficientMobUnitPacket() {

    }

    public EfficientMobUnitPacket(Entity entity, EntityData data) {
        this.id = entity.getId();
        CompoundNBT nbt = new CompoundNBT();
        data.addClientNBT(nbt);
        this.nbt = nbt;

    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(Ref.MODID, "effmob");
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
                .loadFromClientNBT(nbt);
        }
    }

    @Override
    public MyPacket<EfficientMobUnitPacket> newInstance() {
        return new EfficientMobUnitPacket();
    }
}
