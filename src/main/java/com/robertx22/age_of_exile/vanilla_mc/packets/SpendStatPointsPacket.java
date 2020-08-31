package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.MyPacket;
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