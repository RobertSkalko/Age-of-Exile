package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.mmorpg.registers.common.ConfigRegister;
import com.robertx22.age_of_exile.saveclasses.ListStringData;
import com.robertx22.age_of_exile.uncommon.datasaving.ListStringSaving;
import com.robertx22.library_of_exile.main.MyPacket;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class SyncConfigToClientPacket extends MyPacket<SyncConfigToClientPacket> {

    public ListStringData configData;
    public ConfigRegister.Config configType;

    public SyncConfigToClientPacket() {

    }

    public SyncConfigToClientPacket(ListStringData configData, ConfigRegister.Config configType) {
        this.configData = configData;
        this.configType = configType;

    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "syncconfig");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        configData = ListStringSaving.Load(tag.readCompoundTag());
        configType = ConfigRegister.Config.valueOf(tag.readString());

    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        CompoundTag nbt = new CompoundTag();
        ListStringSaving.Save(nbt, configData);

        tag.writeCompoundTag(nbt);
        tag.writeString(configType.name());

    }

    @Override
    public void onReceived(PacketContext ctx) {
        ConfigRegister.CONFIGS.get(configType)
            .loadFromJsons(configData.getList());
    }

    @Override
    public MyPacket<SyncConfigToClientPacket> newInstance() {
        return new SyncConfigToClientPacket();
    }
}