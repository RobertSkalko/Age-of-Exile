package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.capability.player.PlayerStatPointsCap;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.BaseCoreStat;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.SyncCapabilityToClient;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

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
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "stat_alloc");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        stat = tag.readString(30);
        action = tag.readEnumConstant(AllocateStatPacket.ACTION.class);

    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeString(stat, 30);
        tag.writeEnumConstant(action);

    }

    @Override
    public void onReceived(PacketContext ctx) {

        Load.Unit(ctx.getPlayer())
            .setEquipsChanged(true);
        Load.Unit(ctx.getPlayer())
            .tryRecalculateStats();

        PlayerStatPointsCap cap = Load.statPoints(ctx.getPlayer());

        if (cap.getFreePoints() > 0) {
            if (ExileDB.Stats()
                .get(stat) instanceof BaseCoreStat) {
                cap.data.map.put(stat, 1 + cap.data.map.getOrDefault(stat, 0));
                Packets.sendToClient(ctx.getPlayer(), new SyncCapabilityToClient(ctx.getPlayer(), PlayerCaps.STAT_POINTS));
            }
        }
    }

    @Override
    public MyPacket<AllocateStatPacket> newInstance() {
        return new AllocateStatPacket();
    }
}
