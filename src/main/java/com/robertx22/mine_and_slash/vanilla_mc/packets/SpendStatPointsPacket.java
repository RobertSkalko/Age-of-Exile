package com.robertx22.mine_and_slash.vanilla_mc.packets;

import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class SpendStatPointsPacket extends MyPacket<SpendStatPointsPacket> {

    public String stat;

    public SpendStatPointsPacket() {

    }

    public SpendStatPointsPacket(Stat stat) {
        this.stat = stat.GUID();

    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "spendstat");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        stat = tag.readString(30);

    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeString(stat, 30);

    }

    @Override
    public void onReceived(PacketContext ctx) {
        PlayerEntity player = ctx.getPlayer();
        if (player != null) {
            Load.statPoints(player)
                .addPoint(player, SlashRegistry.Stats()
                    .get(stat), Load.Unit(player));
        }
    }

    @Override
    public MyPacket<SpendStatPointsPacket> newInstance() {
        return new SpendStatPointsPacket();
    }
}