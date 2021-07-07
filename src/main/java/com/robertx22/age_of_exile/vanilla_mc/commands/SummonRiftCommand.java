package com.robertx22.age_of_exile.vanilla_mc.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.robertx22.age_of_exile.database.data.tiers.base.Difficulty;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonType;
import com.robertx22.age_of_exile.dimension.teleporter.TeleportedBlockEntity;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.server.command.CommandManager.literal;

public class SummonRiftCommand {
    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(
            literal(CommandRefs.ID)
                .then(literal("summon_rift").requires(e -> e.hasPermissionLevel(2))
                    .executes(
                        ctx -> run(ctx.getSource()
                            .getPlayer()))));
    }

    private static int run(PlayerEntity player) {

        summonRift(player);

        return 1;
    }

    static List<String> randomMessages = new ArrayList<>();

    static {
        randomMessages.add("Horrors call for you.");
        randomMessages.add("A rift has appeared.");
        randomMessages.add("Something stirs beyond the boundary.");

    }

    public static void summonRift(PlayerEntity player) {
        World world = player.getServer()
            .getWorld(RegistryKey.of(Registry.WORLD_KEY, new Identifier("world_of_exile:hell1")));

        ChunkPos cp = new ChunkPos(player.getBlockPos());

        world.getChunk(cp.x, cp.z);

        BlockPos pos = world.getTopPosition(Heightmap.Type.WORLD_SURFACE, player.getBlockPos()); // todo

        if (world.isAir(pos)) {

            player.sendMessage(new LiteralText(RandomUtils.randomFromList(randomMessages))
                .formatted(Formatting.DARK_PURPLE), false);

            int lvl = Load.Unit(player)
                .getLevel();
            Difficulty difficulty = ExileDB.Difficulties()
                .random();

            world.setBlockState(pos, ModRegistry.BLOCKS.TELEPORTER.getDefaultState());
            TeleportedBlockEntity be = (TeleportedBlockEntity) world.getBlockEntity(pos);
            be.data.dungeon_type = DungeonType.RIFT;
            be.data.rift_data.dun_type = DungeonType.RIFT;
            be.data.rift_data.randomize(lvl, difficulty);

        }
    }

}

