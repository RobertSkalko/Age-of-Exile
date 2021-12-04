package com.robertx22.addon.divine_missions;

import com.robertx22.addon.divine_missions.data_gen.DMPoolsAdder;
import com.robertx22.addon.divine_missions.data_gen.DMRewardsAdder;
import com.robertx22.addon.divine_missions.data_gen.DMTasksAdder;
import com.robertx22.addon.divine_missions.types.AoeLevelCondition;
import com.robertx22.addon.divine_missions.types.CompleteDungeonTask;
import com.robertx22.addon.divine_missions.types.GiveAoeExpReward;
import com.robertx22.addon.divine_missions.types.GiveFavorReward;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.divine_missions.db_init.RegistryInit;

public class DMRegInit {

    public static void init() {

        RegistryInit.init();

        new CompleteDungeonTask().registerToExileRegistry();
        new AoeLevelCondition().registerToExileRegistry();

        new GiveFavorReward().registerToExileRegistry();
        new GiveAoeExpReward().registerToExileRegistry();

        if (MMORPG.RUN_DEV_TOOLS) {
            DMRewardsAdder.init();
            DMTasksAdder.init();
            new DMPoolsAdder().registerAll();
        }

    }
}
