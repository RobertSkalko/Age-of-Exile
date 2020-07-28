package com.robertx22.mine_and_slash.mmorpg;

import com.robertx22.mine_and_slash.database.registry.SlashRegistry;
import com.robertx22.mine_and_slash.auto_comp.DeterminePowerLevels;
import com.robertx22.mine_and_slash.database.registrators.CurrencyItems;
import com.robertx22.mine_and_slash.mmorpg.registers.server.CommandRegister;
import com.robertx22.mine_and_slash.uncommon.error_checks.base.ErrorChecks;
import com.robertx22.mine_and_slash.uncommon.testing.TestManager;
import com.robertx22.mine_and_slash.uncommon.testing.tests.CheckWeaponDpsBalanceTest;
import com.robertx22.mine_and_slash.uncommon.testing.tests.CountUniqueGearTypes;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.world.GameRules;

public class LifeCycleEvents {

    static boolean regDefault = true;

    public static void register() {

        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            new CurrencyItems().registerAll();

            DataGeneration.generateAll();

            MMORPG.server = server;

        });

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {

            DeterminePowerLevels.setupHashMaps();

            CommandRegister.Register(server);

            SlashRegistry.checkGuidValidity();

            ErrorChecks.getAll()
                .forEach(x -> x.check());
            SlashRegistry.unregisterInvalidEntries();

            regDefault = server
                .getGameRules()
                .get(GameRules.NATURAL_REGENERATION)
                .get();

            server.getGameRules()
                .get(GameRules.NATURAL_REGENERATION)
                .set(false, server);

            if (MMORPG.RUN_DEV_TOOLS) { // CHANGE ON PUBLIC BUILDS TO FALSE
                TestManager.RunAllTests();
                CountUniqueGearTypes.count();
                CheckWeaponDpsBalanceTest.run();
            }

        });

        ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
            server.getGameRules()
                .get(GameRules.NATURAL_REGENERATION)
                .set(regDefault, server);
        });

    }
}
