package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.a_libraries.dmg_number_particle.DamageParticleAdder;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.library_of_exile.main.MyPacket;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class DmgNumPacket extends MyPacket<DmgNumPacket> {

    public String string;
    public int id;
    public boolean iscrit = false;
    public Formatting format = Formatting.RED;

    public DmgNumPacket() {

    }

    public DmgNumPacket(LivingEntity entity, String str, boolean iscrit, Formatting format) {
        string = str;
        this.id = entity.getEntityId();
        this.iscrit = iscrit;
        this.format = format;
    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "dmgnum");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        string = tag.readString(500);
        id = tag.readInt();
        this.iscrit = tag.readBoolean();
        this.format = Formatting.byName(tag.readString(100));

    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeString(string);
        tag.writeInt(id);
        tag.writeBoolean(iscrit);
        tag.writeString(format.getName());

    }

    @Override
    public void onReceived(PacketContext ctx) {
        if (ModConfig.get().client.dmgParticleConfig.ENABLE_FLOATING_DMG) {
            DamageParticleAdder.displayParticle(ctx.getPlayer().world.getEntityById(id), this);
        }
    }

    @Override
    public MyPacket<DmgNumPacket> newInstance() {
        return new DmgNumPacket();
    }
}
