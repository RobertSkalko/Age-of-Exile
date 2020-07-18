package com.robertx22.mine_and_slash.vanilla_mc.packets;

import com.robertx22.mine_and_slash.config.forge.ClientContainer;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.PacketByteBuf;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class NoManaPacket {

    public enum MessageTypes {
        NoEnergy,
        NoMana
    }

    public NoManaPacket() {
    }

    public static NoManaPacket decode(PacketByteBuf buf) {

        NoManaPacket newpkt = new NoManaPacket();

        return newpkt;

    }

    public static void encode(NoManaPacket packet, PacketByteBuf tag) {

    }

    public static void handle(final NoManaPacket pkt, Supplier<NetworkEvent.Context> ctx) {

        ctx.get()
            .enqueueWork(() -> {
                try {

                    if (ClientContainer.INSTANCE.SHOW_LOW_ENERGY_MANA_WARNING.get()) {

                        PlayerEntity player = MMORPG.proxy.getPlayerEntityFromContext(ctx);
                        player.playSound(SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, 0.5F, 0);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        ctx.get()
            .setPacketHandled(true);

    }

}