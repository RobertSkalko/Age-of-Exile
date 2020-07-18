package com.robertx22.mine_and_slash.vanilla_mc.packets;

import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.OnDisplayDamage;
import com.robertx22.mine_and_slash.config.forge.ClientContainer;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
import com.robertx22.mine_and_slash.uncommon.wrappers.SText;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.PacketByteBuf;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class DmgNumPacket {

    public String element;
    public String string;
    public double x;
    public double y;
    public double z;
    public float height;
    public boolean isExp;
    public float number;

    private DmgNumPacket() {

    }

    public DmgNumPacket(LivingEntity entity, Elements ele, String str, float number) {
        this.element = ele.toString();
        this.string = str;
        this.x = entity.x;
        this.y = entity.y;
        this.z = entity.z;
        this.height = entity.getHeight();
        this.number = number;

    }

    public static DmgNumPacket decode(PacketByteBuf buf) {

        DmgNumPacket newpkt = new DmgNumPacket();

        newpkt.element = buf.readString(10);
        newpkt.x = buf.readDouble();
        newpkt.y = buf.readDouble();
        newpkt.z = buf.readDouble();
        newpkt.height = buf.readFloat();
        newpkt.isExp = buf.readBoolean();
        newpkt.string = buf.readString(30);
        newpkt.number = buf.readFloat();

        return newpkt;

    }

    public static void encode(DmgNumPacket packet, PacketByteBuf tag) {

        tag.writeString(packet.element);
        tag.writeDouble(packet.x);
        tag.writeDouble(packet.y);
        tag.writeDouble(packet.z);
        tag.writeFloat(packet.height);
        tag.writeBoolean(packet.isExp);
        tag.writeString(packet.string);
        tag.writeFloat(packet.number);

    }

    public static void handle(final DmgNumPacket pkt, Supplier<NetworkEvent.Context> ctx) {

        ctx.get()
            .enqueueWork(() -> {
                try {
                    if (pkt.isExp && ClientContainer.INSTANCE.dmgParticleConfig.ENABLE_CHAT_EXP_MSG.get()) {
                        ClientOnly.getPlayer()
                            .sendMessage(new SText(Formatting.GREEN + "" + Formatting.BOLD + "+" + pkt.number + " EXP"));

                    } else if (pkt.isExp == false && ClientContainer.INSTANCE.dmgParticleConfig.ENABLE_FLOATING_DMG.get()) {
                        OnDisplayDamage.displayParticle(pkt);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        ctx.get()
            .setPacketHandled(true);

    }

}
