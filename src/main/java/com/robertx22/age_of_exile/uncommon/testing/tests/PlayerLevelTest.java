package com.robertx22.age_of_exile.uncommon.testing.tests;

import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.uncommon.testing.CommandTest;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import net.minecraft.entity.player.ServerPlayerEntity;

public class PlayerLevelTest extends CommandTest {

    @Override
    public void run(ServerPlayerEntity player) {

        testLevelCurve();

    }

    public static void testLevelCurve() {
        for (int i = 1; i < GameBalanceConfig.get().MAX_LEVEL + 1; i++) {

            int needed = LevelUtils.getExpRequiredForLevel(i);
            int basepermob = LevelUtils.getBaseExpMobReward(i);
            int killsneeded = needed / basepermob;
            System.out.print("\nExp needed for lvl: " + i + " is " + needed);
            System.out.print("\nBase mob xp reward for lvl: " + i + " is " + basepermob);
            System.out.print("\nKills needed for level: " + i + " is " + killsneeded);
            System.out.print("\n");
        }
    }

    @Override
    public String GUID() {
        return "player_lvl_exp";
    }
}
