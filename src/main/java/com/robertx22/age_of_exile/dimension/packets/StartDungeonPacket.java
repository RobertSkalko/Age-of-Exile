package com.robertx22.age_of_exile.dimension.packets;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonData;
import com.robertx22.age_of_exile.dimension.player_data.PlayerMapsCap;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TeamUtils;
import com.robertx22.library_of_exile.main.MyPacket;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.tuple.ImmutablePair;

public class StartDungeonPacket extends MyPacket<StartDungeonPacket> {

    int x;
    int y;
    BlockPos pos;
    Boolean isTeam = false;

    public StartDungeonPacket() {

    }

    public StartDungeonPacket(Boolean isteam, BlockPos pos, PointData point) {

        this.x = point.x;
        this.y = point.y;
        this.pos = pos;
        this.isTeam = isteam;
    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "start_dun");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        pos = tag.readBlockPos();
        isTeam = tag.readBoolean();
        x = tag.readInt();
        y = tag.readInt();
    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeBlockPos(pos);
        tag.writeBoolean(isTeam);
        tag.writeInt(x);
        tag.writeInt(y);
    }

    @Override
    public void onReceived(PacketContext ctx) {

        PlayerMapsCap maps = Load.playerMaps(ctx.getPlayer());

        ImmutablePair<Integer, DungeonData> dungeon = maps.getDungeonFromUUID(uuid);

        if (maps.canStart(dungeon.right)) {

            if (isTeam) {
                if (ModConfig.get().Server.REQUIRE_TEAM_FOR_TEAM_DUNGEONS) {
                    if (TeamUtils.getOnlineTeamMembersInRange(ctx.getPlayer())
                        .size() < 2) {
                        ctx.getPlayer()
                            .sendMessage(new LiteralText("You need at least 2 party members nearby to start a dungeon in Team mode."), false);
                        ctx.getPlayer()
                            .sendMessage(new LiteralText("Use /age_of_exile teams"), false);
                        return;

                    }
                }
            }

            maps.onStartDungeon(isTeam, pos, uuid);
        }
    }

    @Override
    public MyPacket<StartDungeonPacket> newInstance() {
        return new StartDungeonPacket();
    }
}

