package com.robertx22.mine_and_slash.uncommon.testing;

import com.robertx22.mine_and_slash.uncommon.testing.tests.CheckWeaponDpsBalanceTest;
import com.robertx22.mine_and_slash.uncommon.testing.tests.CountUniqueGearTypes;
import com.robertx22.mine_and_slash.uncommon.testing.tests.MobTypesTest;
import net.minecraft.server.world.ServerWorld;

public class TestManager {

    public static void RunAllTests(ServerWorld world) {

        if (true) {
            CountUniqueGearTypes.count();
            CheckWeaponDpsBalanceTest.run();
            MobTypesTest.run(world);
        }

    }

}
