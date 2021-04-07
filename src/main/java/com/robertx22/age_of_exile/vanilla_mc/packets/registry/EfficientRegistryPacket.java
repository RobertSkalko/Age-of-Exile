package com.robertx22.age_of_exile.vanilla_mc.packets.registry;

import com.google.common.collect.Lists;
import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.database.IByteBuf;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.database.registry.SlashRegistryContainer;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.library_of_exile.main.MyPacket;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.List;

public class EfficientRegistryPacket<T extends IByteBuf & ISerializedRegistryEntry> extends MyPacket<EfficientRegistryPacket> {
    public static Identifier ID = new Identifier(Ref.MODID, "eff_reg");
    private List<T> items;

    SlashRegistryType type;

    public EfficientRegistryPacket() {

    }

    public EfficientRegistryPacket(SlashRegistryType type, List<T> list) {
        this.type = type;
        this.items = list;
    }

    @Override
    public Identifier getIdentifier() {
        return ID;
    }

    @Override
    public void loadFromData(PacketByteBuf buf) {

        this.type = SlashRegistryType.valueOf(buf.readString(30));

        if (MMORPG.RUN_DEV_TOOLS) {
            //System.out.print("\n Eff packet " + type.name() + " is " + buf.readableBytes() + " bytes big \n");
        }

        IByteBuf<T> serializer = (IByteBuf<T>) type.getSerializer();

        this.items = Lists.newArrayList();

        int i = buf.readVarInt();

        for (int j = 0; j < i; ++j) {
            this.items.add(serializer.getFromBuf(buf));
        }

    }

    @Override
    public void saveToData(PacketByteBuf buf) {

        buf.writeString(type.name(), 30);
        buf.writeVarInt(this.items.size());
        items.forEach(x -> x.toBuf(buf));

    }

    @Override
    public void onReceived(PacketContext ctx) {

        SlashRegistryContainer reg = Database.getRegistry(type);

        reg.unregisterAllEntriesFromDatapacks();

        items.forEach(x -> x.registerToSlashRegistry());

        System.out.println("Efficient " + type.name() + " reg load on client success with: " + reg.getSize() + " entries.");

    }

    @Override
    public MyPacket<EfficientRegistryPacket> newInstance() {
        return new EfficientRegistryPacket();
    }
}