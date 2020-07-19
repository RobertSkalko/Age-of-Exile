package com.robertx22.mine_and_slash.vanilla_mc.commands.reset;

import com.mojang.brigadier.CommandDispatcher;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.vanilla_mc.commands.CommandRefs;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ResetSpellCooldowns {
    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(
            literal(CommandRefs.ID)
                .then(literal("reset").requires(e -> e.hasPermissionLevel(2))
                    .then(literal("spell_cooldowns")
                        .then(argument("target", EntityArgumentType.entity())
                            .executes(
                                ctx -> run(EntityArgumentType.getPlayer(ctx, "target")))))));
    }

    private static int run(PlayerEntity en) {

        try {
            Load.spells(en)
                .getCastingData()
                .onTimePass(en, Load.spells(en), 500000);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }
}
