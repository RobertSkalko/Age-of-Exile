package com.robertx22.age_of_exile.uncommon.testing.tests;

import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.uncommon.testing.CommandTest;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import net.minecraft.entity.player.ServerPlayerEntity;

public class SkillLevelTest extends CommandTest {
    @Override
    public void run(ServerPlayerEntity player) {
        testSkillLevelCurve();
    }

    public static void testSkillLevelCurve() {
        for (int i = 1; i < GameBalanceConfig.get().MAX_LEVEL + 1; i++) {

            int needed = LevelUtils.getExpNeededForSkillLevel(i);
            System.out.print("\nExp needed for lvl: " + i + " is " + needed);
            System.out.print("\n");

            int old = (int) (LevelUtils.getExpRequiredForLevel(i) / 0.25F);
            System.out.print("\nOLD Exp needed for lvl: " + i + " is " + old);

        }
    }

    @Override
    public String GUID() {
        return "skill_lvl_exp";
    }
}
