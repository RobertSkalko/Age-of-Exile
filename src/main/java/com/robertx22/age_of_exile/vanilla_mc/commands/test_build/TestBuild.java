package com.robertx22.age_of_exile.vanilla_mc.commands.test_build;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.vanilla_mc.commands.CommandRefs;
import com.robertx22.age_of_exile.vanilla_mc.commands.suggestions.GearRaritySuggestions;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;

import java.util.Map;
import java.util.Objects;

import static net.minecraft.command.Commands.literal;
import static net.minecraft.commands.Commands.argument;

public class TestBuild {
    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {

        commandDispatcher.register(
            literal(CommandRefs.ID)
                .then(literal("dev_replace_equipped_gear").requires(e -> e.hasPermission(2))
                    .requires(e -> e.hasPermission(2))
                    .then(argument("target", EntityArgument.player())
                        .then(argument("tag", StringArgumentType.word())
                            .suggests(new SlotTagSuggestions())
                            .then(argument("level", IntegerArgumentType.integer())
                                .then(argument("rarity", StringArgumentType
                                    .word()).suggests(new GearRaritySuggestions())
                                    .executes(e -> execute(e.getSource(), EntityArgument
                                        .getPlayer(e, "target"), StringArgumentType
                                        .getString(e, "tag"), IntegerArgumentType
                                        .getInteger(e, "level"), StringArgumentType
                                        .getString(e, "rarity")

                                    ))))))));
    }

    private static int execute(CommandSource commandSource, PlayerEntity player,
                               String tag, int lvl, String rarity) {

        if (Objects.isNull(player)) {
            try {
                player = commandSource.getPlayerOrException();
            } catch (CommandSyntaxException e) {
                e.printStackTrace();
                return 1;
            }
        }

        for (Map.Entry<EquipmentSlotType, BaseGearType> entry : TestBuilds.getGearsFor(BaseGearType.SlotTag.valueOf(tag), player)
            .entrySet()) {

            GearBlueprint blueprint = new GearBlueprint(lvl, 0);
            blueprint.actionsAfterGeneration.clear();
            blueprint.level.set(lvl);
            blueprint.rarity.set(ExileDB.GearRarities()
                .get(rarity));
            blueprint.gearItemSlot.set(entry.getValue());

            player.setItemSlot(entry.getKey(), blueprint.createStack());

        }

        return 0;
    }
}
