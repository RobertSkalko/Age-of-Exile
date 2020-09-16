package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.robertx22.age_of_exile.database.registry.RegistryPackets;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.ListStringData;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.Cached;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class RegistryPacket extends MyPacket<RegistryPacket> {

    public static final JsonParser PARSER = new JsonParser();

    SlashRegistryType type;
    ListStringData data;

    public RegistryPacket() {

    }

    public <T extends ISerializedRegistryEntry> RegistryPacket(SlashRegistryType type, ListStringData data) {
        this.type = type;
        this.data = data;
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
            .forEach(x -> {
                try {
                    JsonObject json = (JsonObject) PARSER.parse(x);
                    RegistryPackets.add(this.type, json);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            });

    }

    @Override
    public MyPacket<RegistryPacket> newInstance() {
        return new RegistryPacket();
    }
}