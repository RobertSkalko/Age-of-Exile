package com.robertx22.age_of_exile.vanilla_mc.commands.giveitems;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.vanilla_mc.commands.CommandRefs;
import com.robertx22.age_of_exile.vanilla_mc.commands.suggestions.DatabaseSuggestions;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Objects;

import static net.minecraft.command.Commands.argument;
import static net.minecraft.command.Commands.literal;

public class GiveExactUnique {
    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {

        commandDispatcher.register(
            literal(CommandRefs.ID)
                .then(literal("give").requires(e -> e.hasPermission(2))
                    .then(literal("unique_gear")
                        .requires(e -> e.hasPermission(2))
                        .then(argument("target", EntityArgument.player())
                            .then(argument("uniqueID", StringArgumentType.word())
                                .suggests(new DatabaseSuggestions(ExileRegistryTypes.UNIQUE_GEAR))
                                .then(argument("level", IntegerArgumentType.integer())
                                    .then(argument("amount", IntegerArgumentType
                                        .integer(1, 5000))
                                        .executes(e -> execute(e.getSource(), EntityArgument
                                            .getPlayer(e, "target"), StringArgumentType
                                            .getString(e, "uniqueID"), IntegerArgumentType
                                            .getInteger(e, "level"), IntegerArgumentType
                                            .getInteger(e, "amount")

                                        )))))))));
    }

    private static int execute(CommandSource commandSource, PlayerEntity player,
                               String id, int lvl, int amount) {

        if (Objects.isNull(player)) {
            try {
                player = commandSource.getPlayerOrException();
            } catch (CommandSyntaxException e) {
                e.printStackTrace();
                return 1;
            }
        }

        for (int i = 0; i < amount; i++) {
            GearBlueprint blueprint = new GearBlueprint(lvl, 0);
            blueprint.level.set(lvl);

            if (!id.equals("random")) {

                blueprint.rarity.set(ExileDB.GearRarities()
                    .get(ExileDB.UniqueGears()
                        .random().uniqueRarity));

                blueprint.uniquePart.set(ExileDB.UniqueGears()
                    .get(id));
                blueprint.gearItemSlot.set(blueprint.uniquePart.get()
                    .getBaseGear());
            } else {

                blueprint.rarity.set(ExileDB.GearRarities()
                    .getFilterWrapped(x -> x.is_unique_item)
                    .random());
                blueprint.uniquePart.set(ExileDB.UniqueGears()
                    .getFilterWrapped(x -> x.uniqueRarity.equals(blueprint.rarity.get()
                        .GUID()))
                    .random());
                blueprint.gearItemSlot.set(blueprint.uniquePart.get()
                    .getBaseGear());
            }

            player.addItem(blueprint.createStack());
        }

        return 0;
    }
}
