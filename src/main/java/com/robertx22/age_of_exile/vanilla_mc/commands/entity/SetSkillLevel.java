package com.robertx22.age_of_exile.vanilla_mc.commands.entity;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.commands.CommandRefs;
import com.robertx22.age_of_exile.vanilla_mc.commands.suggestions.DatabaseSuggestions;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;

import java.util.Objects;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class SetSkillLevel {

    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(
            literal(CommandRefs.ID)
                .then(literal("set").requires(e -> e.hasPermissionLevel(2))
                    .then(literal("skill_level")
                        .then(argument("skill", StringArgumentType.string())
                            .suggests(new DatabaseSuggestions(ExileRegistryTypes.PLAYER_SKILLS))
                            .requires(e -> e.hasPermissionLevel(2))
                            .then(argument("target", EntityArgumentType.player())
                                .then(argument("level", IntegerArgumentType.integer())
                                    .executes(e -> execute(e.getSource(), EntityArgumentType.getPlayer(e, "target"), IntegerArgumentType
                                        .getInteger(e, "level"), StringArgumentType.getString(e, "skill")))))))));
    }

    private static int execute(ServerCommandSource commandSource, PlayerEntity player,
                               int lvl, String id) {
        if (Objects.isNull(player)) {
            try {
                player = commandSource.getPlayer();
            } catch (CommandSyntaxException e) {
                e.printStackTrace();
                return 1;
            }
        }

        Load.playerSkills(player)
            .getDataFor(id)
            .setLvl(lvl);

        return 0;
    }
}