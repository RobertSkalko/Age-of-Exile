package com.robertx22.age_of_exile.dimension.rules;

import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.level.GameType;

public class OnTickSetGameMode {

    public static void onTick(ServerPlayerEntity player) {

        GameType current = player.gameMode.getGameModeForPlayer();

        if (WorldUtils.isMapWorldClass(player.level)) {
            if (current == GameType.SURVIVAL) {
                Load.playerRPGData(player).maps.orig_gamemode = current.getName();
                player.setGameMode(GameType.ADVENTURE);
            }
        } else {
            if (!Load.playerRPGData(player).maps.orig_gamemode.isEmpty()) {
                GameType mode = GameType.byName(Load.playerRPGData(player).maps.orig_gamemode);
                player.setGameMode(mode);
                Load.playerRPGData(player).maps.orig_gamemode = "";
            }
        }
    }
}
