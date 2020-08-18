package com.robertx22.age_of_exile.vanilla_mc.commands.stats;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.commands.CommandRefs;
import joptsimple.internal.Strings;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

import java.util.stream.Collectors;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ListStats {

    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(
            literal(CommandRefs.ID)
                .then(literal("stat").requires(e -> e.hasPermissionLevel(2))
                    .then(literal("list")
                        .requires(e -> e.hasPermissionLevel(0))
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

            if (en instanceof PlayerEntity) {
                EntityCap.UnitData data = Load.Unit(en);
                PlayerEntity player = (PlayerEntity) en;

                String str = "";

                if (type.equals("exact")) {
                    str = Strings.join(data.getCustomExactStats().stats.values()
                        .stream()
                        .map(x -> x.getStatId())
                        .collect(Collectors.toList()), ",");
                } else {
                    str = Strings.join(data.getCustomExactStats().mods.values()
                        .stream()
                        .map(x -> x.stat)
                        .collect(Collectors.toList()), ",");
                }
                player.sendMessage(new LiteralText(str), false);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }
}
