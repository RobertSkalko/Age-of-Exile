package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.capability.player.RPGPlayerData;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.CoreStat;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class AllocateStatPacket extends MyPacket<AllocateStatPacket> {

    public String stat;
    AllocateStatPacket.ACTION action;

    public enum ACTION {
        ALLOCATE, REMOVE
    }

    public AllocateStatPacket() {

    }

    public AllocateStatPacket(Stat stat) {
        this.stat = stat.GUID();
        this.action = ACTION.ALLOCATE;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(Ref.MODID, "stat_alloc");
    }

    @Override
    public void loadFromData(PacketBuffer tag) {
        stat = tag.readUtf(30);
        action = tag.readEnum(AllocateStatPacket.ACTION.class);

    }

    @Override
    public void saveToData(PacketBuffer tag) {
        tag.writeUtf(stat, 30);
        tag.writeEnum(action);

    }

    @Override
    public void onReceived(ExilePacketContext ctx) {

        Load.Unit(ctx.getPlayer())
            .setEquipsChanged(true);
        Load.Unit(ctx.getPlayer())
            .tryRecalculateStats();

        RPGPlayerData cap = Load.playerRPGData(ctx.getPlayer());

        if (cap.statPoints.getFreePoints(ctx.getPlayer()) > 0) {
            if (ExileDB.Stats()
                .get(stat) instanceof CoreStat) {
                cap.statPoints.map.put(stat, 1 + cap.statPoints.map.getOrDefault(stat, 0));

                cap.syncToClient(ctx.getPlayer());

            }
        }
    }

    @Override
    public MyPacket<AllocateStatPacket> newInstance() {
        return new AllocateStatPacket();
    }
}
