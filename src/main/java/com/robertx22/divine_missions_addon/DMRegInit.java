package com.robertx22.divine_missions_addon;

import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.divine_missions.db_init.RegistryInit;
import com.robertx22.divine_missions_addon.data_gen.DMPoolsAdder;
import com.robertx22.divine_missions_addon.data_gen.DMRewardsAdder;
import com.robertx22.divine_missions_addon.data_gen.DMTasksAdder;
import com.robertx22.divine_missions_addon.types.*;

public class DMRegInit {

    public static void init() {

        RegistryInit.init();

        new CompleteDungeonTask().registerToExileRegistry();
        new AoeLevelCondition().registerToExileRegistry();
        new CompleteRiftTask().registerToExileRegistry();

        new GiveFavorReward().registerToExileRegistry();
        new GiveAoeExpReward().registerToExileRegistry();

        if (MMORPG.RUN_DEV_TOOLS) {
            DMRewardsAdder.init();
            DMTasksAdder.init();

            new DMPoolsAdder().registerAll();
        }

    }
}
