package com.robertx22.age_of_exile.capability.player;

import com.robertx22.age_of_exile.capability.bases.ICommonPlayerCap;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import java.util.UUID;

public class TeamCap implements ICommonPlayerCap {

    public static final Identifier RESOURCE = new Identifier(Ref.MODID, "team");
    private static final String LOC = "team";

    PlayerEntity player;

    public String team_id = "";
    public String invitedToTeam = "";
    public boolean isLeader = false;

    public TeamCap(PlayerEntity player) {
        this.player = player;
    }

    public void joinTeamOf(PlayerEntity other) {
        this.team_id = Load.team(other).team_id;
    }

    public void leaveTeam() {
        this.team_id = "";
    }

    public boolean isOnTeam() {
        return !team_id.isEmpty();
    }

    public boolean isOnSameTeam(PlayerEntity other) {

        if (team_id.isEmpty() || Load.team(other).team_id.isEmpty()) {
            return false;
        }

        return team_id.equals(Load.team(other).team_id);
    }

    public void createTeam() {
        this.team_id = UUID.randomUUID()
            .toString();
        this.isLeader = true;
    }

    @Override
    public NbtCompound toTag(NbtCompound nbt) {
        nbt.putString(LOC, team_id);
        nbt.putString("i", invitedToTeam);
        nbt.putBoolean("l", isLeader);
        return nbt;
    }

    @Override
    public PlayerCaps getCapType() {
        return PlayerCaps.FAVOR;
    }

    @Override
    public void fromTag(NbtCompound nbt) {
        this.team_id = nbt.getString(LOC);
        this.invitedToTeam = nbt.getString("i");
        this.isLeader = nbt.getBoolean("l");
    }

}
