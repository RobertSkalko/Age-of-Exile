package com.robertx22.age_of_exile.vanilla_mc.commands.open_gui;

import com.mojang.brigadier.CommandDispatcher;
import com.robertx22.age_of_exile.vanilla_mc.commands.CommandRefs;
import com.robertx22.age_of_exile.vanilla_mc.packets.OpenGuiPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;

import static net.minecraft.command.Commands.literal;

public class OpenHub {
    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register(
            literal(CommandRefs.ID)
                .then(literal("open").requires(e -> e.hasPermission(0))
                    .then(literal("hub")
                        .executes(ctx -> run(ctx.getSource())))));
    }

    public static final String COMMAND = "slash open hub";

    private static int run(CommandSource source) {

        try {

            if (source.getEntity() instanceof ServerPlayerEntity) {
                Packets.sendToClient(source.getPlayerOrException(),
                    new OpenGuiPacket(OpenGuiPacket.GuiType.MAIN_HUB));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }
}

