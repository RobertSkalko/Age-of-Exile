package com.robertx22.mine_and_slash.vanilla_mc.commands.stats;

import com.mojang.brigadier.CommandDispatcher;
import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import com.robertx22.mine_and_slash.vanilla_mc.commands.CommandRefs;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.command.ServerCommandSource;

import java.util.UUID;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class GiveAllStats {

    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(
            literal(CommandRefs.ID)
                .then(literal("stat").requires(e -> e.hasPermissionLevel(2))
                    .then(literal("exact")
                        .then(literal("testing").then(literal("give_all")
                            .requires(e -> e.hasPermissionLevel(2))
                            .then(argument("target", EntityArgumentType.entity())
                                .executes(
                                    ctx -> run(EntityArgumentType.getPlayer(ctx, "target")))))))));
    }

    private static int run(Entity en) {

        try {

            if (en instanceof LivingEntity) {

                EntityCap.UnitData data = Load.Unit(en);

                SlashRegistry.Stats()
                    .getList()
                    .forEach(x -> data.getCustomExactStats()
                        .add(UUID.randomUUID()
                            .toString(), x.GUID(), 100, ModType.FLAT));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }
}
