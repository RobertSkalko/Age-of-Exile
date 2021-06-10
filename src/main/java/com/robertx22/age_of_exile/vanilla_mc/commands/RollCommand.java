package com.robertx22.age_of_exile.vanilla_mc.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TeamUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

import static net.minecraft.server.command.CommandManager.literal;

public class RollCommand {

    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(

            literal(CommandRefs.ID)
                .then(literal("roll").executes(x -> {

                    PlayerEntity player = x.getSource()
                        .getPlayer();

                    int roll = RandomUtils.RandomRange(0, 100);
                    TeamUtils.getOnlineMembers(player)
                        .forEach(p -> {
                            p.sendMessage(new LiteralText("").append(player.getDisplayName())
                                .append(" rolled a " + roll), false);
                        });

                    return 0;
                }))
        );
    }

}
