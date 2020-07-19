package com.robertx22.mine_and_slash.vanilla_mc.commands.stats;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import com.robertx22.mine_and_slash.vanilla_mc.commands.CommandRefs;
import com.robertx22.mine_and_slash.vanilla_mc.commands.suggestions.StatSuggestions;
import com.robertx22.mine_and_slash.vanilla_mc.commands.suggestions.StatTypeSuggestions;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class GiveStat {

    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(

            literal(CommandRefs.ID)
                .then(literal("stat").requires(e -> e.hasPermissionLevel(2))
                    .then(literal("exact")
                        .then(literal("give")
                            .requires(e -> e.hasPermissionLevel(2))
                            .then(argument("target", EntityArgumentType.entity())
                                .then(argument("statGUID", StringArgumentType.string())
                                    .suggests(new StatSuggestions())
                                    .then(argument("statType", StringArgumentType.string())
                                        .suggests(new StatTypeSuggestions())
                                        .then(argument("GUID", StringArgumentType
                                            .string())
                                            .then(argument("value", FloatArgumentType
                                                .floatArg())
                                                .executes(ctx -> run(EntityArgumentType
                                                    .getPlayer(ctx, "target"), StringArgumentType
                                                    .getString(ctx, "statGUID"), StringArgumentType
                                                    .getString(ctx, "statType"), StringArgumentType
                                                    .getString(ctx, "GUID"), FloatArgumentType
                                                    .getFloat(ctx, "value"))))))))))));
    }

    private static int run(Entity en, String statGUID, String statType,
                           String GUID, float value) {

        try {

            if (en instanceof LivingEntity) {
                EntityCap.UnitData data = Load.Unit(en);
                data.getCustomExactStats()
                    .add(GUID, statGUID, value, ModType.valueOf(statType));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }
}
