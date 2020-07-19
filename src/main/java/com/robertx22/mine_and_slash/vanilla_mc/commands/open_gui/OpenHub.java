package com.robertx22.mine_and_slash.vanilla_mc.commands.open_gui;

import com.mojang.brigadier.CommandDispatcher;
import com.robertx22.mine_and_slash.mmorpg.Packets;
import com.robertx22.mine_and_slash.vanilla_mc.commands.CommandRefs;
import com.robertx22.mine_and_slash.vanilla_mc.packets.OpenGuiPacket;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.minecraft.server.command.CommandManager.literal;

public class OpenHub {
    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(
            literal(CommandRefs.ID)
                .then(literal("open").requires(e -> e.hasPermissionLevel(0))
                    .then(literal("hub")
                        .executes(ctx -> run(ctx.getSource())))));
    }

    public static final String COMMAND = "slash open hub";

    private static int run(ServerCommandSource source) {

        try {

            if (source.getEntity() instanceof ServerPlayerEntity) {
                Packets.sendToClient(source.getPlayer(),
                    new OpenGuiPacket(OpenGuiPacket.GuiType.MAIN_HUB));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }
}

