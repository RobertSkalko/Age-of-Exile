package com.robertx22.age_of_exile.vanilla_mc.commands.giveitems;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.player_skills.items.inscribing.ScrollBuffData;
import com.robertx22.age_of_exile.player_skills.items.inscribing.ScrollBuffItem;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.age_of_exile.vanilla_mc.commands.CommandRefs;
import com.robertx22.age_of_exile.vanilla_mc.commands.suggestions.DatabaseSuggestions;
import com.robertx22.age_of_exile.vanilla_mc.commands.suggestions.GearRaritySuggestions;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Objects;

import static net.minecraft.command.Commands.argument;
import static net.minecraft.command.Commands.literal;

public class GiveBuffScroll {
    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {

        commandDispatcher.register(
            literal(CommandRefs.ID)
                .then(literal("give").requires(e -> e.hasPermission(2))
                    .then(literal("buff_scroll")
                        .requires(e -> e.hasPermission(2))
                        .then(argument("target", EntityArgument.player())
                            .then(argument("id", StringArgumentType.word())
                                .suggests(new DatabaseSuggestions(ExileRegistryTypes.SCROLL_BUFFS))
                                .then(argument("level", IntegerArgumentType.integer())
                                    .then(argument("rarity", StringArgumentType.word())
                                        .suggests(new GearRaritySuggestions())
                                        .then(argument("amount", IntegerArgumentType
                                            .integer(1, 5000))
                                            .executes(e -> execute(e.getSource(), EntityArgument
                                                .getPlayer(e, "target"), StringArgumentType
                                                .getString(e, "id"), IntegerArgumentType
                                                .getInteger(e, "level"), StringArgumentType.getString(e, "rarity"), IntegerArgumentType
                                                .getInteger(e, "amount")

                                            ))))))))));
    }

    private static int execute(CommandSource commandSource, PlayerEntity player,
                               String id, int lvl, String rar, int amount) {

        if (Objects.isNull(player)) {
            try {
                player = commandSource.getPlayerOrException();
            } catch (CommandSyntaxException e) {
                e.printStackTrace();
                return 1;
            }
        }

        for (int i = 0; i < amount; i++) {
            ScrollBuffData data = new ScrollBuffData();

            data.lvl = lvl;
            data.rar = rar;
            data.id = id;

            if (id.equals("random")) {
                data.id = ExileDB.ScrollBuffs()
                    .random().id;
            }

            if (rar.equals("random")) {
                data.rar = ExileDB.GearRarities()
                    .random()
                    .GUID();
            }

            PlayerUtils.giveItem(ScrollBuffItem.create(data), player);

        }

        return 0;
    }
}

