package com.robertx22.age_of_exile.dimension.rules;

import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;

public class OnTickSetGameMode {

    public static void onTick(ServerPlayerEntity player) {

        GameMode current = player.interactionManager.getGameMode();

        if (WorldUtils.isDungeonWorld(player.world)) {
            if (current == GameMode.SURVIVAL) {
                Load.playerMaps(player).mapsData.orig_gamemode = current.getName();
                player.setGameMode(GameMode.ADVENTURE);
            }
        } else {
            if (!Load.playerMaps(player).mapsData.orig_gamemode.isEmpty()) {
                GameMode mode = GameMode.byName(Load.playerMaps(player).mapsData.orig_gamemode);
                player.setGameMode(mode);
                Load.playerMaps(player).mapsData.orig_gamemode = "";
            }
        }
    }
}
