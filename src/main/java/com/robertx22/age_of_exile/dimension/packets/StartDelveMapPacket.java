package com.robertx22.age_of_exile.dimension.packets;

import com.robertx22.age_of_exile.database.data.tiers.base.Difficulty;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.dimension.player_data.PlayerMapsCap;
import com.robertx22.age_of_exile.dimension.teleporter.TeleportedBlockEntity;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.MyPacket;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class StartDelveMapPacket extends MyPacket<StartDelveMapPacket> {

    String diff;
    BlockPos pos;

    public StartDelveMapPacket() {

    }

    public StartDelveMapPacket(Difficulty diff, BlockPos pos) {
        this.diff = diff.GUID();
        this.pos = pos;
    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "start_delve");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        pos = tag.readBlockPos();
        diff = tag.readString(500);
    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeBlockPos(pos);
        tag.writeString(diff);

    }

    @Override
    public void onReceived(PacketContext ctx) {

        PlayerEntity player = ctx.getPlayer();

        PlayerMapsCap maps = Load.playerMaps(player);

        Difficulty difficulty = ExileDB.Difficulties()
            .get(diff);

        BlockEntity tile = player.world.getBlockEntity(pos);

        if (tile instanceof TeleportedBlockEntity) {
            TeleportedBlockEntity be = (TeleportedBlockEntity) tile;
            if (be.data.type.isDungeon()) {
                maps.createRandomDungeon(difficulty);
                be.data.activated = true;
            }
        }
    }

    @Override
    public MyPacket<StartDelveMapPacket> newInstance() {
        return new StartDelveMapPacket();
    }
}

