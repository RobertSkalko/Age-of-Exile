package com.robertx22.age_of_exile.mmorpg;

import com.robertx22.age_of_exile.auto_comp.ItemAutoPowerLevels;
import com.robertx22.age_of_exile.mmorpg.registers.server.CommandRegister;
import com.robertx22.age_of_exile.uncommon.testing.TestManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.world.GameRules;

public class LifeCycleEvents {

    static boolean regDefault = true;

    public static void register() {

        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            DataGeneration.generateAll();
            MMORPG.server = server;

        });

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            ItemAutoPowerLevels.setupHashMaps();

            CommandRegister.Register(server);

            regDefault = server
                .getGameRules()
                .get(GameRules.NATURAL_REGENERATION)
                .get();

            server.getGameRules()
                .get(GameRules.NATURAL_REGENERATION)
                .set(false, server);

            if (MMORPG.RUN_DEV_TOOLS) { // CHANGE ON PUBLIC BUILDS TO FALSE
                TestManager.RunAllTests(server.getOverworld());
            }

        });

        ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
            server.getGameRules()
                .get(GameRules.NATURAL_REGENERATION)
                .set(regDefault, server);
        });

    }
}
