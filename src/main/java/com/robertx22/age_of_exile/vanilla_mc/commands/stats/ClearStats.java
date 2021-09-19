package com.robertx22.age_of_exile.vanilla_mc.commands.stats;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.commands.CommandRefs;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ClearStats {

    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(
            literal(CommandRefs.ID)
                .then(literal("stat").requires(e -> e.hasPermissionLevel(2))
                    .then(literal("clear")
                        .requires(e -> e.hasPermissionLevel(2))
                        .then(argument("target", EntityArgumentType.entity())
                            .then(argument("scaling", StringArgumentType.string())
                                .suggests(new GiveStat.ModOrExact())
                                .executes(ctx -> {

                                    return run(EntityArgumentType.getPlayer(ctx, "target"), StringArgumentType
                                        .getString(ctx, "scaling"));

                                }))))));
    }

    private static int run(Entity en, String type) {

        try {

            if (en instanceof LivingEntity) {
                EntityData data = Load.Unit(en);

                if (type.equals("exact")) {
                    data.getCustomExactStats().stats.clear();
                } else {
                    data.getCustomExactStats().mods.clear();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }
}
