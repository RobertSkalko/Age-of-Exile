package com.robertx22.mine_and_slash.vanilla_mc.packets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.robertx22.exile_lib.utils.LoadSave;
import com.robertx22.mine_and_slash.database.registry.*;
import com.robertx22.mine_and_slash.datapacks.bases.ISerializable;
import com.robertx22.mine_and_slash.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.saveclasses.ListStringData;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.Cached;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.stream.Collectors;

public class RegistryPacket extends MyPacket<RegistryPacket> {

    static final JsonParser PARSER = new JsonParser();

    SlashRegistryType type;
    ListStringData data;

    public RegistryPacket() {

    }

    public <T extends ISerializedRegistryEntry> RegistryPacket(SlashRegistryType type, List<T> items) {

        SlashRegistryContainer reg = SlashRegistry.getRegistry(type);

        if (reg.isEmpty()) {
            SlashRegistry.restoreBackup();
        }

        // TODO CACHE THIS
        List<String> list = items
            .stream()
            .map(x -> ((ISerializable) x).toJsonNoSpaces())
            .collect(Collectors.toList());

        if (list.isEmpty()) {
            throw new RuntimeException(type.name() + " Registry is empty on the server when trying to send registry packet!");
        }

        this.data = new ListStringData(items
            .stream()
            .map(x -> ((ISerializable) x).toJson()
                .toString())
            .collect(Collectors.toList()));

        this.type = type;

    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "reg");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        try {
            type = SlashRegistryType.valueOf(tag.readString(30));

            CompoundTag nbt = tag.readCompoundTag();

            data = LoadSave.Load(ListStringData.class, new ListStringData(), nbt, "data");

        } catch (Exception e) {
            System.out.println("Failed reading Age of Exile packet to bufferer.");
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
            System.out.println("Failed saving " + type.name() + " Age of Exile packet to bufferer.");
            e.printStackTrace();
        }
    }

    @Override
    public void onReceived(PacketContext ctx) {
        Cached.reset();

        if (data.getList()
            .isEmpty()) {
            throw new RuntimeException("Registry list sent from server is empty!");
        }

        data.getList()
            .stream()
            .map(x -> {
                try {
                    JsonObject json = (JsonObject) PARSER.parse(x);
                    return type.getSerializer()
                        .fromJson(json);
                } catch (JsonSyntaxException e) {
                    System.out.println("Failed to parse Age of Exile registry Json!!!");
                    e.printStackTrace();
                }
                return null;

            })
            .collect(Collectors.toList())
            .forEach(x -> {
                if (x instanceof ISlashRegistryEntry) {
                    SlashRegistryPackets.add((ISerializedRegistryEntry) x);
                }
            });
    }

    @Override
    public MyPacket<RegistryPacket> newInstance() {
        return new RegistryPacket();
    }
}