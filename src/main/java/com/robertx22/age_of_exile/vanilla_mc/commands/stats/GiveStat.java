package com.robertx22.age_of_exile.vanilla_mc.commands.stats;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.vanilla_mc.commands.CommandRefs;
import com.robertx22.age_of_exile.vanilla_mc.commands.suggestions.CommandSuggestions;
import com.robertx22.age_of_exile.vanilla_mc.commands.suggestions.StatSuggestions;
import com.robertx22.age_of_exile.vanilla_mc.commands.suggestions.StatTypeSuggestions;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

import java.util.Arrays;
import java.util.List;

import static net.minecraft.command.Commands.argument;
import static net.minecraft.command.Commands.literal;

public class GiveStat {

    static class ModOrExact extends CommandSuggestions {
        @Override
        public List<String> suggestions() {
            return Arrays.asList("exact", "scaling");
        }
    }

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register(
            literal(CommandRefs.ID)
                .then(literal("stat").requires(e -> e.hasPermission(2))
                    .then(literal("give")
                        .requires(e -> e.hasPermission(2))
                        .then(argument("target", EntityArgument.entity())
                            .then(argument("scaling", StringArgumentType.string())
                                .suggests(new ModOrExact())
                                .then(argument("statGUID", StringArgumentType.string())
                                    .suggests(new StatSuggestions())
                                    .then(argument("statType", StringArgumentType.string())
                                        .suggests(new StatTypeSuggestions())
                                        .then(argument("GUID", StringArgumentType
                                            .string())
                                            .then(argument("value", FloatArgumentType
                                                .floatArg())

                                                .executes(ctx -> {
                                                    return run(EntityArgument
                                                        .getPlayer(ctx, "target"), StringArgumentType
                                                        .getString(ctx, "scaling"), StringArgumentType
                                                        .getString(ctx, "statGUID"), StringArgumentType
                                                        .getString(ctx, "statType"), StringArgumentType
                                                        .getString(ctx, "GUID"), FloatArgumentType
                                                        .getFloat(ctx, "value"));
                                                }))))))))));
    }

    private static int run(Entity en, String scaling, String statGUID, String statType,
                           String GUID, float v1) {

        try {

            if (en instanceof LivingEntity) {
                EntityData data = Load.Unit(en);

                if (scaling.equals("exact")) {
                    data.getCustomExactStats()
                        .addExactStat(GUID, statGUID, v1, ModType.valueOf(statType));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }
}
