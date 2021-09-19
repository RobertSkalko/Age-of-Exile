package com.robertx22.age_of_exile.dimension.packets;

import com.robertx22.age_of_exile.capability.player.RPGPlayerData;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonData;
import com.robertx22.age_of_exile.dimension.dungeon_data.TeamSize;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TeamUtils;
import com.robertx22.library_of_exile.main.MyPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

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
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(Ref.MODID, "start_dun");
    }

    @Override
    public void loadFromData(PacketBuffer tag) {
        pos = tag.readBlockPos();
        teamSize = tag.readEnum(TeamSize.class);

    }

    @Override
    public void saveToData(PacketBuffer tag) {
        tag.writeBlockPos(pos);
        tag.writeEnum(teamSize);

    }

    @Override
    public void onReceived(Context ctx) {

        RPGPlayerData maps = Load.playerRPGData(ctx.getPlayer());

        DungeonData dungeon = maps.maps.dungeonData;

        if (teamSize.requiredMemberAmount > 1) {
            if (ModConfig.get().Server.REQUIRE_TEAM_FOR_TEAM_DUNGEONS) {
                if (TeamUtils.getOnlineTeamMembersInRange(ctx.getPlayer())
                    .size() < teamSize.requiredMemberAmount) {
                    ctx.getPlayer()
                        .displayClientMessage(new StringTextComponent("You need at least " + teamSize.requiredMemberAmount + "  party members nearby to start a dungeon in this mode."), false);
                    ctx.getPlayer()
                        .displayClientMessage(new StringTextComponent("Use /age_of_exile teams"), false);
                    return;

                }
            }
        }

        maps.maps.onStartDungeon(ctx.getPlayer(), teamSize, pos, dungeon.uuid);

    }

    @Override
    public MyPacket<StartDungeonPacket> newInstance() {
        return new StartDungeonPacket();
    }
}

