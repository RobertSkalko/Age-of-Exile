package com.robertx22.mine_and_slash.vanilla_mc.packets;

import com.robertx22.mine_and_slash.mmorpg.registers.common.ConfigRegister;
import com.robertx22.mine_and_slash.saveclasses.ListStringData;
import com.robertx22.mine_and_slash.uncommon.datasaving.ListStringSaving;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.PacketByteBuf;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncConfigToClientPacket {

    public ListStringData configData;
    public ConfigRegister.Config configType;

    public SyncConfigToClientPacket() {

    }

    public SyncConfigToClientPacket(ListStringData configData, ConfigRegister.Config configType) {
        this.configData = configData;
        this.configType = configType;

    }

    public static SyncConfigToClientPacket decode(PacketByteBuf buf) {

        try {
            SyncConfigToClientPacket newpkt = new SyncConfigToClientPacket();
            newpkt.configData = ListStringSaving.Load(buf.readCompoundTag());
            newpkt.configType = ConfigRegister.Config.valueOf(buf.readString());
            return newpkt;
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to decode " + buf.toString());
            e.printStackTrace();

        }
        return null;
    }

    public static void encode(SyncConfigToClientPacket packet, PacketByteBuf buf) {

        try {
            CompoundTag nbt = new CompoundTag();
            ListStringSaving.Save(nbt, packet.configData);

            buf.writeCompoundTag(nbt);
            buf.writeString(packet.configType.name());
        } catch (Exception e) {
            System.out.println("Failed to encode " + packet.configType.name());
            e.printStackTrace();
        }

    }

    public static void handle(final SyncConfigToClientPacket pkt, Supplier<NetworkEvent.Context> ctx) {

        ctx.get()
            .enqueueWork(() -> {
                try {
                    ConfigRegister.CONFIGS.get(pkt.configType)
                        .loadFromJsons(pkt.configData.getList());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        ctx.get()
            .setPacketHandled(true);

    }

}