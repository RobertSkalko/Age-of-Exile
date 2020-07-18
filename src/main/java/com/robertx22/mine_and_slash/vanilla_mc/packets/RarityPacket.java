package com.robertx22.mine_and_slash.vanilla_mc.packets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.robertx22.mine_and_slash.database.data.rarities.RarityTypeEnum;
import com.robertx22.mine_and_slash.event_hooks.data_gen.ISerializable;
import com.robertx22.mine_and_slash.saveclasses.ListStringData;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.mine_and_slash.uncommon.datasaving.base.LoadSave;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.PacketByteBuf;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class RarityPacket {

    static final JsonParser PARSER = new JsonParser();

    RarityTypeEnum type;
    ListStringData data;

    private RarityPacket() {

    }

    public RarityPacket(RarityTypeEnum type) {

        List<String> list = (List<String>) type.container.getAllRarities()
            .stream()
            .map(x -> ((ISerializable) x).toJson()
                .toString())
            .collect(Collectors.toList());

        if (list.isEmpty()) {
            throw new RuntimeException("Rarity Registry is empty on the server when trying to send registry packet!");
        }

        this.data = new ListStringData(list);

        this.type = type;

    }

    public static RarityPacket decode(PacketByteBuf buf) {

        try {
            RarityPacket newpkt = new RarityPacket();
            newpkt.type = RarityTypeEnum.valueOf(buf.readString(30));

            CompoundTag nbt = buf.readCompoundTag();

            newpkt.data = LoadSave.Load(ListStringData.class, new ListStringData(), nbt, "data");
            return newpkt;
        } catch (Exception e) {
            System.out.println("Failed reading Mine and Slash packet to bufferer.");
            e.printStackTrace();
        }
        return new RarityPacket();

    }

    public static void encode(RarityPacket packet, PacketByteBuf tag) {
        try {
            tag.writeString(packet.type.name(), 30);
            CompoundTag nbt = new CompoundTag();

            LoadSave.Save(packet.data, nbt, "data");

            tag.writeCompoundTag(nbt);
        } catch (Exception e) {
            System.out.println("Failed saving " + packet.type.name() + " Mine and Slash packet to bufferer.");
            e.printStackTrace();
        }
    }

    public static void handle(final RarityPacket pkt, Supplier<NetworkEvent.Context> ctx) {

        ctx.get()
            .enqueueWork(() -> {
                try {

                    if (pkt.data.getList()
                        .isEmpty()) {
                        throw new RuntimeException("Rarity list sent from server is empty!");
                    }

                    List<Rarity> list = pkt.data.getList()
                        .stream()
                        .map(x -> {
                            try {
                                JsonObject json = (JsonObject) PARSER.parse(x);
                                return (Rarity) pkt.type.serializer.fromJson(json);
                            } catch (JsonSyntaxException e) {
                                System.out.println("Failed to parse Mine and Slash rarity Json!!!");
                                e.printStackTrace();
                            }
                            return null;
                        })
                        .collect(Collectors.toList());

                    pkt.type.container.updateFromDatapack(list);

                } catch (Exception e) {

                    e.printStackTrace();
                }
            });

        ctx.get()
            .setPacketHandled(true);

    }

}