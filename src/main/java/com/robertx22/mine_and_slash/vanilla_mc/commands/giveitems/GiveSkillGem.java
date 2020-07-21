package com.robertx22.mine_and_slash.vanilla_mc.commands.giveitems;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.mine_and_slash.loot.blueprints.SkillGemBlueprint;
import com.robertx22.mine_and_slash.vanilla_mc.commands.CommandRefs;
import com.robertx22.mine_and_slash.vanilla_mc.commands.suggestions.RegistrySuggestions;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;

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
                            .then(argument("gemid", StringArgumentType.word())
                                .suggests(new RegistrySuggestions(SlashRegistry.Spells()
                                    .getList()))
                                .then(argument("level", IntegerArgumentType.integer())
                                    .then(argument("amount", IntegerArgumentType
                                        .integer(1, 5000))
                                        .executes(e -> execute(EntityArgumentType
                                            .getPlayer(e, "target"), StringArgumentType
                                            .getString(e, "gemid"), IntegerArgumentType
                                            .getInteger(e, "level"), IntegerArgumentType
                                            .getInteger(e, "amount")

                                        )))))))));
    }

    private static int execute(PlayerEntity player,
                               String id, int lvl, int amount) {

        for (int i = 0; i < amount; i++) {
            SkillGemBlueprint blueprint = new SkillGemBlueprint(lvl);
            blueprint.level.set(lvl);

            if (!id.equals("random")) {
                blueprint.spellPart.set(SlashRegistry.Spells()
                    .get(id));
            }
            player.giveItemStack(blueprint.createStack());
        }

        return 0;
    }
}
