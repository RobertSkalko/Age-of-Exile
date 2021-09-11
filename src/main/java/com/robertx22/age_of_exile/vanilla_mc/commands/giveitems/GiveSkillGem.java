package com.robertx22.age_of_exile.vanilla_mc.commands.giveitems;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.loot.blueprints.SkillGemBlueprint;
import com.robertx22.age_of_exile.vanilla_mc.commands.CommandRefs;
import com.robertx22.age_of_exile.vanilla_mc.commands.suggestions.DatabaseSuggestions;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;

import java.util.Objects;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class GiveSkillGem {
    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {

        commandDispatcher.register(
            literal(CommandRefs.ID)
                .then(literal("give").requires(e -> e.hasPermissionLevel(2))
                    .then(literal("skill_gem")
                        .requires(e -> e.hasPermissionLevel(2))
                        .then(argument("target", EntityArgumentType.player())
                            .then(argument("id", StringArgumentType.word())
                                .suggests(new DatabaseSuggestions(ExileRegistryTypes.SKILL_GEM))
                                .then(argument("level", IntegerArgumentType.integer())
                                    .then(argument("rarity", StringArgumentType.word())
                                        .then(argument("amount", IntegerArgumentType
                                            .integer(1, 5000))
                                            .executes(e -> execute(e.getSource(), EntityArgumentType
                                                .getPlayer(e, "target"), StringArgumentType
                                                .getString(e, "id"), IntegerArgumentType
                                                .getInteger(e, "level"), StringArgumentType.getString(e, "rarity"), IntegerArgumentType
                                                .getInteger(e, "amount")

                                            ))))))))));
    }

    private static int execute(ServerCommandSource commandSource, PlayerEntity player,
                               String id, int lvl, String rar, int amount) {

        if (Objects.isNull(player)) {
            try {
                player = commandSource.getPlayer();
            } catch (CommandSyntaxException e) {
                e.printStackTrace();
                return 1;
            }
        }

        for (int i = 0; i < amount; i++) {
            SkillGemBlueprint blueprint = new SkillGemBlueprint(lvl);
            blueprint.level.set(lvl);

            if (!id.equals("random")) {
                blueprint.type.set(ExileDB.SkillGems()
                    .get(id));
            }

            player.giveItemStack(blueprint.createStack());
        }

        return 0;
    }
}

