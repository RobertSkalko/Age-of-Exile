package com.robertx22.age_of_exile.vanilla_mc.commands.entity;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.commands.CommandRefs;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class SetEntityRarity {

    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(

            literal(CommandRefs.ID)
                .then(literal("set").requires(e -> e.hasPermissionLevel(2))
                    .then(literal("entity")
                        .then(literal("rarity")
                            .requires(e -> e.hasPermissionLevel(2))
                            .then(argument("target", EntityArgumentType.entity())
                                .then(argument("rarity", IntegerArgumentType.integer(0, 5))
                                    .executes(e -> execute(e.getSource(), EntityArgumentType.getEntity(e, "target"), StringArgumentType
                                        .getString(e, "rarity")))))))));
    }

    private static int execute(ServerCommandSource commandSource, Entity player,
                               String rarity) {

        EntityCap.UnitData data = Load.Unit(player);

        data.setRarity(rarity);

        return 0;
    }
}
