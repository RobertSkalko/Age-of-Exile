package com.robertx22.age_of_exile.vanilla_mc.commands.entity;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.commands.CommandRefs;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class GiveFavor {

    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(
            literal(CommandRefs.ID)
                .then(literal("give").requires(e -> e.hasPermissionLevel(2))
                    .then(literal("favor")
                        .requires(e -> e.hasPermissionLevel(2))
                        .then(argument("target", EntityArgumentType.player())
                            .then(argument("favor", IntegerArgumentType.integer())
                                .executes(ctx -> run(EntityArgumentType.getPlayer(ctx, "target"), IntegerArgumentType
                                    .getInteger(ctx, "favor"))))))));
    }

    private static int run(PlayerEntity player, int favor) {

        try {
            Load.playerRPGData(player).favor
                .addFavor(favor);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }
}