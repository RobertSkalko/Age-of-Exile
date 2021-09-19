package com.robertx22.age_of_exile.vanilla_mc.commands.stats;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.commands.CommandRefs;
import joptsimple.internal.Strings;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;

import java.util.stream.Collectors;

import static net.minecraft.command.Commands.argument;
import static net.minecraft.command.Commands.literal;

public class ListStats {

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register(
            literal(CommandRefs.ID)
                .then(literal("stat").requires(e -> e.hasPermission(2))
                    .then(literal("list")
                        .requires(e -> e.hasPermission(0))
                        .then(argument("target", EntityArgument.entity())
                            .then(argument("scaling", StringArgumentType.string())
                                .suggests(new GiveStat.ModOrExact())
                                .executes(ctx -> {
                                    return run(EntityArgument.getPlayer(ctx, "target"), StringArgumentType
                                        .getString(ctx, "scaling"));

                                }))))));
    }

    private static int run(Entity en, String type) {

        try {

            if (en instanceof PlayerEntity) {
                EntityData data = Load.Unit(en);
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
                player.displayClientMessage(new StringTextComponent(str), false);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }
}
