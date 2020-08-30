package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.config.forge.ModConfig;
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
        if (ModConfig.get().Server.ALL_PLAYERS_ARE_TEAMED_PVE_MODE) {
            return true;
        }
        boolean vanilla = p1.getScoreboardTeam() != null && p2.getScoreboardTeam() != null && p1.getScoreboardTeam()
            .isEqual(p2.getScoreboardTeam());

        return vanilla;

    }

}
