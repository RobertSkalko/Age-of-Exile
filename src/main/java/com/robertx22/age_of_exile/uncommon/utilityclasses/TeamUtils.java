package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TeamUtils {

    public static List<PlayerEntity> getOnlineTeamMembersInRange(PlayerEntity player) {
        return getOnlineMembers(player).stream()
            .filter(x -> player.distanceTo(x) < ModConfig.get().Server.PARTY_RADIUS)
            .collect(Collectors.toList());

    }

    public static List<PlayerEntity> getOnlineMembers(PlayerEntity player) {
        List<PlayerEntity> players = new ArrayList<>();

        try {
            player.getServer()
                .getPlayerManager()
                .getPlayerList()
                .forEach(x -> {
                    if (areOnSameTeam(player, x)) {
                        players.add(x);
                    }

                });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return players;
    }

    public static boolean areOnSameTeam(PlayerEntity p1, PlayerEntity p2) {
        if (ModConfig.get().Server.ALL_PLAYERS_ARE_TEAMED_PVE_MODE) {
            return true;
        }

        if (Load.team(p1)
            .isOnSameTeam(p2)) {
            return true;
        }

        return false;
        /*

        boolean vanilla = p1.getScoreboardTeam() != null && p2.getScoreboardTeam() != null && p1.getScoreboardTeam()
            .isEqual(p2.getScoreboardTeam());

        return vanilla;

         */

    }

}
