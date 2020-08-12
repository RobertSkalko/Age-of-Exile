package com.robertx22.age_of_exile.vanilla_mc.commands.entity;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.robertx22.age_of_exile.vanilla_mc.commands.CommandRefs;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;


import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class GiveExp {

    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(
            literal(CommandRefs.ID)
                .then(literal("give").requires(e -> e.hasPermissionLevel(2))
                    .then(literal("exp")
                        .requires(e -> e.hasPermissionLevel(2))
                        .then(argument("target", EntityArgumentType.player())
                            .then(argument("exp", IntegerArgumentType.integer())
                                .executes(ctx -> run(EntityArgumentType.getPlayer(ctx, "target"), IntegerArgumentType
                                    .getInteger(ctx, "exp"))))))));
    }

    private static int run( PlayerEntity player, int exp) {

        try {
            Load.Unit(player)
                .GiveExp(player, exp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }
}