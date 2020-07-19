package com.robertx22.mine_and_slash.mmorpg;

import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.mine_and_slash.a_libraries.curios.AddCurioCapability;
import com.robertx22.mine_and_slash.mmorpg.registers.common.CapabilityRegister;
import com.robertx22.mine_and_slash.mmorpg.registers.common.ConfigRegister;
import com.robertx22.mine_and_slash.mmorpg.registers.common.PacketRegister;
import com.robertx22.mine_and_slash.mmorpg.registers.common.PotionRegister;
import com.robertx22.mine_and_slash.vanilla_mc.packets.MyPacket;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.logging.Logger;

public class MMORPG implements ModInitializer {

    // DISABLE WHEN PUBLIC BUILD
    public static boolean RUN_DEV_TOOLS = true;

    public static boolean RUN_MIXIN_LOGS() {
        return false;
    }

    @Override
    public void onInitialize() {

        System.out.println("Starting " + Ref.MOD_NAME);

        ModRegistry.init();

        SlashRegistry.initRegistries();
        RegisterEvents.register();
        ConfigRegister.registerForgeConfigs(); // MUST BE IN MAIN CLASS
        SlashRegistry.registerAllItems(); // after config registerAll
        SlashRegistry.checkGuidValidity();

        PotionRegister.register();

        //this was in common
        PacketRegister.register();
        CapabilityRegister.register();
        //common

        ConfigRegister.registerCustomConfigs();

        AddCurioCapability.addComponents();

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
