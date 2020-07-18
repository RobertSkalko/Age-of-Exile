package com.robertx22.mine_and_slash.vanilla_mc.packets;

import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.OnDisplayDamage;
import com.robertx22.mine_and_slash.config.forge.ClientContainer;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
import com.robertx22.mine_and_slash.uncommon.wrappers.SText;
import net.fabricmc.fabric.api.network.PacketConsumer;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.PacketByteBuf;

public class DmgNumPacket implements PacketConsumer {

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

    public static DmgNumPacket of(LivingEntity entity, Elements ele, String str, float number) {
        DmgNumPacket pkt = new DmgNumPacket();
        pkt.element = ele.toString();
        pkt.string = str;
        pkt.x = entity.getX();
        pkt.y = entity.getY();
        pkt.z = entity.getZ();
        pkt.height = entity.getHeight();
        pkt.number = number;
        return pkt;
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

    @Override
    public void accept(PacketContext ctx, PacketByteBuf buf) {

        String element = buf.readString(10);
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        float height = buf.readFloat();
        boolean isExp = buf.readBoolean();
        String string = buf.readString(30);
        float number = buf.readFloat();

        ctx.getTaskQueue()
            .execute(() -> {
                try {
                    if (isExp && ClientContainer.INSTANCE.dmgParticleConfig.ENABLE_CHAT_EXP_MSG.get()) {
                        ClientOnly.getPlayer()
                            .sendMessage(new SText(Formatting.GREEN + "" + Formatting.BOLD + "+" + number + " EXP"));

                    } else if (isExp == false && ClientContainer.INSTANCE.dmgParticleConfig.ENABLE_FLOATING_DMG.get()) {
                        OnDisplayDamage.displayParticle(element, string, x, y, z, height);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

    }
}
