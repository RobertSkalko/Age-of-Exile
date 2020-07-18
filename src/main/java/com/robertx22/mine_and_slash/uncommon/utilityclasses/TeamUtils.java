package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class TeamUtils {

    public static List<PlayerEntity> getOnlineTeamMembers(PlayerEntity player) {

        List<PlayerEntity> players = new ArrayList<>();
        players.add(player);

        if (player.getScoreboardTeam() != null) {
            try {
                player.getServer()
                    .getPlayerManager()
                    .getPlayerList()
                    .forEach(x -> {
                        if (player.getScoreboardTeam()
                            .isEqual(x.getScoreboardTeam())) {
                            players.add(x);
                        }
                    });
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return players;

    }

    public static boolean areOnSameTeam(PlayerEntity p1, PlayerEntity p2) {

        boolean vanilla = p1.getScoreboardTeam() != null && p2.getScoreboardTeam() != null && p1.getScoreboardTeam()
            .isEqual(p2.getScoreboardTeam());

        return vanilla;

    }

}
