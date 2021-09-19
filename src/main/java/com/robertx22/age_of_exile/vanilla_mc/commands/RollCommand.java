package com.robertx22.age_of_exile.vanilla_mc.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TeamUtils;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;

import static net.minecraft.command.Commands.literal;

public class RollCommand {

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register(

            literal(CommandRefs.ID)
                .then(literal("roll").executes(x -> {

                    PlayerEntity player = x.getSource()
                        .getPlayerOrException();

                    int roll = RandomUtils.RandomRange(0, 100);
                    TeamUtils.getOnlineMembers(player)
                        .forEach(p -> {
                            p.displayClientMessage(new StringTextComponent("").append(player.getDisplayName())
                                .append(" rolled a " + roll), false);
                        });

                    return 0;
                }))
        );
    }

}
