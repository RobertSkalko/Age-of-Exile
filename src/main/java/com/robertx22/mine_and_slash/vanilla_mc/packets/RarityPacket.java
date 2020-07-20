package com.robertx22.mine_and_slash.vanilla_mc.packets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.robertx22.mine_and_slash.database.data.rarities.RarityTypeEnum;
import com.robertx22.mine_and_slash.datapacks.bases.ISerializable;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.saveclasses.ListStringData;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.mine_and_slash.uncommon.datasaving.base.LoadSave;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.stream.Collectors;

public class RarityPacket extends MyPacket<RarityPacket> {

    static final JsonParser PARSER = new JsonParser();

    RarityTypeEnum type;
    ListStringData data;

    public RarityPacket() {

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

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "rar");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {

        try {
            type = RarityTypeEnum.valueOf(tag.readString(30));

            CompoundTag nbt = tag.readCompoundTag();

            data = LoadSave.Load(ListStringData.class, new ListStringData(), nbt, "data");

        } catch (Exception e) {
            System.out.println("Failed reading Mine and Slash packet to bufferer.");
            e.printStackTrace();
        }
    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        try {
            tag.writeString(type.name(), 30);
            CompoundTag nbt = new CompoundTag();

            LoadSave.Save(data, nbt, "data");

            tag.writeCompoundTag(nbt);
        } catch (Exception e) {
            System.out.println("Failed saving " + type.name() + " Mine and Slash packet to bufferer.");
            e.printStackTrace();
        }
    }

    @Override
    public void onReceived(PacketContext ctx) {
        if (data.getList()
            .isEmpty()) {
            throw new RuntimeException("Rarity list sent from server is empty!");
        }

        List<Rarity> list = data.getList()
            .stream()
            .map(x -> {
                try {
                    JsonObject json = (JsonObject) PARSER.parse(x);
                    return (Rarity) type.serializer.fromJson(json);
                } catch (JsonSyntaxException e) {
                    System.out.println("Failed to parse Mine and Slash rarity Json!!!");
                    e.printStackTrace();
                }
                return null;
            })
            .collect(Collectors.toList());

        type.container.updateFromDatapack(list);

    }

    @Override
    public MyPacket<RarityPacket> newInstance() {
        return new RarityPacket();
    }
}