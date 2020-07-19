package com.robertx22.mine_and_slash.vanilla_mc.commands.misc;

import com.mojang.brigadier.CommandDispatcher;
import com.robertx22.mine_and_slash.vanilla_mc.commands.CommandRefs;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import com.robertx22.mine_and_slash.mmorpg.registers.common.ConfigRegister;

import static net.minecraft.server.command.CommandManager.literal;

public class ReloadConfigs {
    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(

            literal(CommandRefs.ID)
                .then(literal("configs").requires(e -> e.hasPermissionLevel(2))
                    .then(literal("reload")

                        .requires(e -> e.hasPermissionLevel(2))
                        .executes(ctx -> run(ctx.getSource())))));
    }

    private static int run(ServerCommandSource source) {

        try {

            ConfigRegister.registerCustomConfigs();

            if (source.getEntity() instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity) source.getEntity()).sendSystemMessage(new LiteralText("Configs reloaded"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }
}
