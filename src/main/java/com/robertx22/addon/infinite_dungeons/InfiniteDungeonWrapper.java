package com.robertx22.addon.infinite_dungeons;

import com.robertx22.infinite_dungeons.components.MobIDCap;
import com.robertx22.infinite_dungeons.components.PlayerIDCap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class InfiniteDungeonWrapper {

    public static boolean isDungeonMob(LivingEntity en) {
        return MobIDCap.get(en)
            .isDungeonMob();
    }

    public static boolean isPlayerInDungeon(PlayerEntity en) {
        return PlayerIDCap.get(en)
            .isInDungeon();
    }

}
