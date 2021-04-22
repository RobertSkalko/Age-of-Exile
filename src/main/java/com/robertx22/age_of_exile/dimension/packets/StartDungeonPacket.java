package com.robertx22.age_of_exile.dimension.packets;

import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonData;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.MyPacket;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class StartDungeonPacket extends MyPacket<StartDungeonPacket> {

    String uuid = "";
    BlockPos pos;

    public StartDungeonPacket() {

    }

    public StartDungeonPacket(BlockPos pos, DungeonData dungeon) {
        this.uuid = dungeon.uuid;
        this.pos = pos;
    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "start_dun");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        uuid = tag.readString(100);
        pos = tag.readBlockPos();
    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeString(uuid, 100);
        tag.writeBlockPos(pos);

    }

    @Override
    public void onReceived(PacketContext ctx) {
        Load.playerMaps(ctx.getPlayer())
            .onEnterDungeon(pos, uuid);
    }

    @Override
    public MyPacket<StartDungeonPacket> newInstance() {
        return new StartDungeonPacket();
    }
}

