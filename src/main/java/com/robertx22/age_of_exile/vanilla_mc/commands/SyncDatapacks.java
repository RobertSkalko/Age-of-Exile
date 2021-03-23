package com.robertx22.age_of_exile.vanilla_mc.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.robertx22.age_of_exile.event_hooks.player.OnLogin;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.literal;

public class SyncDatapacks {
    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(
            literal(CommandRefs.ID)
                .then(literal("sync_datapacks").requires(e -> e.hasPermissionLevel(2))
                    .executes(
                        ctx -> run())));
    }

    private static int run() {

        try {
            MMORPG.server.getPlayerManager()
                .getPlayerList()
                .forEach(x -> {
                    OnLogin.onLoad(x);
                });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }
}

