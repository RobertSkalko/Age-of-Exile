package com.robertx22.age_of_exile.vanilla_mc.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.robertx22.age_of_exile.uncommon.testing.CommandTests;
import com.robertx22.age_of_exile.vanilla_mc.commands.suggestions.CommandsSuggestions;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class RunTestCommand {
    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(
            literal(CommandRefs.ID)
                .then(literal("runtest").requires(e -> e.hasPermissionLevel(2))
                    .then(argument("test", StringArgumentType.string()).suggests(new CommandsSuggestions())
                        .executes(
                            ctx -> run(ctx.getSource()
                                .getPlayer(), StringArgumentType.getString(ctx, "test"))))));
    }

    private static int run(ServerPlayerEntity player, String test) {

        CommandTests.run(test, player);

        player.sendMessage(new LiteralText("Test completed."), false);

        return 1;
    }
}
