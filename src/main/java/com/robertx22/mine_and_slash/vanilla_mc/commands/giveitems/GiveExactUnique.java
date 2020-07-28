package com.robertx22.mine_and_slash.vanilla_mc.commands.giveitems;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.robertx22.mine_and_slash.vanilla_mc.commands.CommandRefs;
import com.robertx22.mine_and_slash.vanilla_mc.commands.suggestions.UniqueGearsSuggestions;
import com.robertx22.mine_and_slash.loot.blueprints.GearBlueprint;
import com.robertx22.mine_and_slash.database.registry.SlashRegistry;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;

import java.util.Objects;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class GiveExactUnique {
    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {

        commandDispatcher.register(
            literal(CommandRefs.ID)
                .then(literal("give").requires(e -> e.hasPermissionLevel(2))
                    .then(literal("unique_gear")
                        .requires(e -> e.hasPermissionLevel(2))
                        .then(argument("target", EntityArgumentType.player())
                            .then(argument("uniqueID", StringArgumentType.word())
                                .suggests(new UniqueGearsSuggestions())
                                .then(argument("level", IntegerArgumentType.integer())
                                    .then(argument("amount", IntegerArgumentType
                                        .integer(1, 5000))
                                        .executes(e -> execute(e.getSource(), EntityArgumentType
                                            .getPlayer(e, "target"), StringArgumentType
                                            .getString(e, "uniqueID"), IntegerArgumentType
                                            .getInteger(e, "level"), IntegerArgumentType
                                            .getInteger(e, "amount")

                                        )))))))));
    }

    private static int execute(ServerCommandSource commandSource, PlayerEntity player,
                               String id, int lvl, int amount) {

        if (Objects.isNull(player)) {
            try {
                player = commandSource.getPlayer();
            } catch (CommandSyntaxException e) {
                e.printStackTrace();
                return 1;
            }
        }

        for (int i = 0; i < amount; i++) {
            GearBlueprint blueprint = new GearBlueprint(lvl, 0);
            blueprint.unidentifiedPart.set(false);
            blueprint.isUniquePart.set(true);
            blueprint.level.set(lvl);

            if (!id.equals("random")) {

                blueprint.uniquePart.set(SlashRegistry.UniqueGears()
                    .get(id));
                blueprint.gearItemSlot.set(blueprint.uniquePart.get()
                    .getBaseGearType());
            }

            player.giveItemStack(blueprint.createStack());
        }

        return 0;
    }
}
