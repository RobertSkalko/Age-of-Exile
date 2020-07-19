package com.robertx22.mine_and_slash.mmorpg;

import com.robertx22.mine_and_slash.vanilla_mc.packets.MyPacket;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.logging.Logger;

public class MMORPG {

    // DISABLE WHEN PUBLIC BUILD
    public static boolean RUN_DEV_TOOLS = true;

    public static boolean RUN_MIXIN_LOGS() {
        return false;
    }

    public static void mixinLog(String str) {
        if (RUN_MIXIN_LOGS()) {
            System.out.println(str);
        }
    }

    public static boolean statEffectDebuggingEnabled() {
        return false && RUN_DEV_TOOLS;
    }

    public static Logger LOGGER = Logger.getLogger(Ref.MOD_NAME);

    public static void devToolsLog(String string) {
        if (RUN_DEV_TOOLS) {
            System.out.println(string);
        }
    }

    public static void devToolsErrorLog(String string) {
        if (RUN_DEV_TOOLS) {
            try {
                throw new Exception(string);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void logError(String s) {
        try {
            throw new Exception(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MinecraftServer server = null;

    public static void sendToTracking(MyPacket msg, Entity entity) {
        sendToTracking(msg, entity.getBlockPos(), entity.world);
    }

    public static void sendToTracking(MyPacket msg, BlockPos pos, World world) {

        if (msg == null || world == null) {
            return;
        }
        PlayerStream.watching(world, pos)
            .forEach(x -> {
                Packets.sendToClient(x, msg);
            });

    }

}
