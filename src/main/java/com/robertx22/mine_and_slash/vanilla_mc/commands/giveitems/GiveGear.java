package com.robertx22.mine_and_slash.vanilla_mc.commands.giveitems;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.robertx22.mine_and_slash.vanilla_mc.commands.CommandRefs;
import com.robertx22.mine_and_slash.vanilla_mc.commands.suggestions.GearTypeSuggestions;
import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.loot.blueprints.GearBlueprint;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;

import java.util.Objects;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class GiveGear {

    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {

        commandDispatcher.register(
            literal(CommandRefs.ID)
                .then(literal("give").requires(e -> e.hasPermissionLevel(2))
                    .then(literal("gear")
                        .then(argument("target", EntityArgumentType.player())
                            .then(argument("type", StringArgumentType.word())
                                .suggests(new GearTypeSuggestions())
                                .then(argument("level",
                                    IntegerArgumentType.integer()
                                )
                                    .then(argument(
                                        "rarity",
                                        IntegerArgumentType.integer(
                                            Rarities.Gears.lowest()
                                                .Rank() - 1, Rarities.Gears.highest()
                                                .Rank())
                                    )
                                        .then(argument(
                                            "amount",
                                            IntegerArgumentType
                                                .integer(
                                                    1,
                                                    5000
                                                )
                                        )
                                            .executes(
                                                e -> execute(
                                                    e.getSource(),
                                                    EntityArgumentType
                                                        .getPlayer(
                                                            e,
                                                            "target"
                                                        ),
                                                    StringArgumentType
                                                        .getString(
                                                            e,
                                                            "type"
                                                        ),
                                                    IntegerArgumentType
                                                        .getInteger(
                                                            e,
                                                            "level"
                                                        ),
                                                    IntegerArgumentType
                                                        .getInteger(
                                                            e,
                                                            "rarity"
                                                        ),
                                                    IntegerArgumentType
                                                        .getInteger(
                                                            e,
                                                            "amount"
                                                        )

                                                ))))))))));
    }

    private static int execute(ServerCommandSource commandSource, PlayerEntity player, String type, int lvl,
                               int rarity, int amount) {

        if (Objects.isNull(player)) {
            try {
                player = commandSource.getPlayer();
            } catch (CommandSyntaxException e) {
                e.printStackTrace();
                return 1;
            }
        }
        for (int i = 0; i < amount; i++) {

            GearBlueprint blueprint = new GearBlueprint(lvl);
            blueprint.unidentifiedPart.set(false);

            if (Rarities.Gears.has(rarity)) {
                blueprint.rarity.setSpecificRarity(rarity);
            }
            if (!type.equals("random")) {
                blueprint.gearItemSlot.set(type);
            }

            player.giveItemStack(blueprint.createStack());
        }

        return 0;
    }
}
