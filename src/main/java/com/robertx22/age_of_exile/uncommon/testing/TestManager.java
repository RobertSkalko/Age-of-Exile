package com.robertx22.age_of_exile.uncommon.testing;

import com.robertx22.age_of_exile.uncommon.testing.tests.CheckWeaponDpsBalanceTest;
import com.robertx22.age_of_exile.uncommon.testing.tests.CountUniqueGearTypes;
import com.robertx22.age_of_exile.uncommon.testing.tests.MobTypesTest;
import net.minecraft.server.world.ServerWorld;

public class TestManager {

    public static void RunAllTests(ServerWorld world) {

        if (false) {
            CountUniqueGearTypes.count();
            CheckWeaponDpsBalanceTest.run();
            MobTypesTest.run(world);
        }

    }

}
