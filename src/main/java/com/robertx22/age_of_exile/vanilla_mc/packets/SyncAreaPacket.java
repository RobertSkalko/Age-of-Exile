package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.areas.AreaData;
import com.robertx22.age_of_exile.capability.world.WorldAreas;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class SyncAreaPacket extends MyPacket<SyncAreaPacket> {

    public SyncAreaPacket() {

    }

    private CompoundTag nbt;

    public SyncAreaPacket(AreaData area) {
        nbt = new CompoundTag();
        LoadSave.Save(area, nbt, "area");
    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "syncarea");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        nbt = tag.readCompoundTag();

    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeCompoundTag(nbt);

    }

    @Override
    public void onReceived(PacketContext ctx) {
        try {

            final PlayerEntity player = ctx.getPlayer();
            WorldAreas areas = ModRegistry.COMPONENTS.WORLD_AREAS.get(player.world);
            AreaData data = LoadSave.Load(AreaData.class, new AreaData(), nbt, "area");
            areas.updateClientValue(data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public MyPacket<SyncAreaPacket> newInstance() {
        return new SyncAreaPacket();
    }
}