package com.robertx22.age_of_exile.vanilla_mc.commands.stats;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.commands.CommandRefs;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class RemoveStat {

    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(
            literal(CommandRefs.ID)
                .then(literal("stat").requires(e -> e.hasPermissionLevel(2))
                    .then(literal("exact")
                        .then(literal("remove")
                            .requires(e -> e.hasPermissionLevel(2))
                            .then(argument("target", EntityArgumentType.entity())
                                .then(argument("GUID", StringArgumentType.string())
                                    .executes(ctx -> run(EntityArgumentType.getPlayer(ctx, "target"), StringArgumentType
                                        .getString(ctx, "GUID")))))))));
    }

    private static int run(Entity en, String GUID) {

        try {

            if (en instanceof LivingEntity) {

                EntityCap.UnitData data = Load.Unit(en);

                data.getCustomExactStats()
                    .remove(GUID);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }
}
