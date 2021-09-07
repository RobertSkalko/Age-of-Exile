package com.robertx22.age_of_exile.dimension.packets;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonData;
import com.robertx22.age_of_exile.dimension.dungeon_data.TeamSize;
import com.robertx22.age_of_exile.dimension.player_data.PlayerMapsCap;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TeamUtils;
import com.robertx22.library_of_exile.main.MyPacket;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class StartDungeonPacket extends MyPacket<StartDungeonPacket> {

    BlockPos pos;
    TeamSize teamSize = TeamSize.SOLO;

    public StartDungeonPacket() {

    }

    public StartDungeonPacket(TeamSize teamSize, BlockPos pos) {

        this.pos = pos;
        this.teamSize = teamSize;
    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "start_dun");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        pos = tag.readBlockPos();
        teamSize = tag.readEnumConstant(TeamSize.class);

    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeBlockPos(pos);
        tag.writeEnumConstant(teamSize);

    }

    @Override
    public void onReceived(PacketContext ctx) {

        PlayerMapsCap maps = Load.playerMaps(ctx.getPlayer());

        DungeonData dungeon = maps.data.dungeonData;

        if (teamSize.requiredMemberAmount > 1) {
            if (ModConfig.get().Server.REQUIRE_TEAM_FOR_TEAM_DUNGEONS) {
                if (TeamUtils.getOnlineTeamMembersInRange(ctx.getPlayer())
                    .size() < teamSize.requiredMemberAmount) {
                    ctx.getPlayer()
                        .sendMessage(new LiteralText("You need at least " + teamSize.requiredMemberAmount + "  party members nearby to start a dungeon in this mode."), false);
                    ctx.getPlayer()
                        .sendMessage(new LiteralText("Use /age_of_exile teams"), false);
                    return;

                }
            }
        }

        maps.onStartDungeon(teamSize, pos, dungeon.uuid);

    }

    @Override
    public MyPacket<StartDungeonPacket> newInstance() {
        return new StartDungeonPacket();
    }
}

