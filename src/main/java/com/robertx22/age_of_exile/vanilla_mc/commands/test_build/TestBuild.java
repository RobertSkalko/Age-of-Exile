package com.robertx22.age_of_exile.vanilla_mc.commands.test_build;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.vanilla_mc.commands.CommandRefs;
import com.robertx22.age_of_exile.vanilla_mc.commands.suggestions.GearRaritySuggestions;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;

import java.util.Map;
import java.util.Objects;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class TestBuild {
    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {

        commandDispatcher.register(
            literal(CommandRefs.ID)
                .then(literal("dev_replace_equipped_gear").requires(e -> e.hasPermissionLevel(2))
                    .requires(e -> e.hasPermissionLevel(2))
                    .then(argument("target", EntityArgumentType.player())
                        .then(argument("tag", StringArgumentType.word())
                            .suggests(new SlotTagSuggestions())
                            .then(argument("level", IntegerArgumentType.integer())
                                .then(argument("rarity", StringArgumentType
                                    .word()).suggests(new GearRaritySuggestions())
                                    .executes(e -> execute(e.getSource(), EntityArgumentType
                                        .getPlayer(e, "target"), StringArgumentType
                                        .getString(e, "tag"), IntegerArgumentType
                                        .getInteger(e, "level"), StringArgumentType
                                        .getString(e, "rarity")

                                    ))))))));
    }

    private static int execute(ServerCommandSource commandSource, PlayerEntity player,
                               String tag, int lvl, String rarity) {

        if (Objects.isNull(player)) {
            try {
                player = commandSource.getPlayer();
            } catch (CommandSyntaxException e) {
                e.printStackTrace();
                return 1;
            }
        }

        for (Map.Entry<EquipmentSlot, BaseGearType> entry : TestBuilds.getGearsFor(BaseGearType.SlotTag.valueOf(tag), player)
            .entrySet()) {

            GearBlueprint blueprint = new GearBlueprint(lvl, 0);
            blueprint.actionsAfterGeneration.clear();
            blueprint.unidentifiedPart.set(false);
            blueprint.level.set(lvl);
            blueprint.rarity.set(Database.GearRarities()
                .get(rarity));
            blueprint.gearItemSlot.set(entry.getValue());

            player.equipStack(entry.getKey(), blueprint.createStack());

        }

        return 0;
    }
}
