package com.robertx22.age_of_exile.capability.player.data;

import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.player.PlayerEntity;

import java.util.UUID;

@Storable
public class TeamData {
    @Store
    public String team_id = "";
    @Store
    public String invitedToTeam = "";
    @Store
    public boolean isLeader = false;

    public void joinTeamOf(PlayerEntity other) {
        this.team_id = Load.playerRPGData(other).team.team_id;
    }

    public void leaveTeam() {
        this.team_id = "";
    }

    public boolean isOnTeam() {
        return !team_id.isEmpty();
    }

    public boolean isOnSameTeam(PlayerEntity other) {

        if (team_id.isEmpty() || Load.playerRPGData(other).team.team_id.isEmpty()) {
            return false;
        }

        return team_id.equals(Load.playerRPGData(other).team.team_id);
    }

    public void createTeam() {
        this.team_id = UUID.randomUUID()
            .toString();
        this.isLeader = true;
    }

}
