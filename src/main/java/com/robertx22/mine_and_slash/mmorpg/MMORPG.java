package com.robertx22.mine_and_slash.mmorpg;

import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.mine_and_slash.a_libraries.curios.AddCurioCapability;
import com.robertx22.mine_and_slash.a_libraries.curios.RegisterCurioSlots;
import com.robertx22.mine_and_slash.mmorpg.proxy.ClientProxy;
import com.robertx22.mine_and_slash.mmorpg.proxy.IProxy;
import com.robertx22.mine_and_slash.mmorpg.proxy.ServerProxy;
import com.robertx22.mine_and_slash.mmorpg.registers.client.ClientSetup;
import com.robertx22.mine_and_slash.mmorpg.registers.common.CapabilityRegister;
import com.robertx22.mine_and_slash.mmorpg.registers.common.ConfigRegister;
import com.robertx22.mine_and_slash.mmorpg.registers.common.PacketRegister;
import com.robertx22.mine_and_slash.mmorpg.registers.common.PotionRegister;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
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

    public static IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    private static final String PROTOCOL_VERSION = Integer.toString(1);

    public static final SimpleChannel Network = NetworkRegistry.ChannelBuilder.named(
        new Identifier(Ref.MODID, "main_channel"))
        .clientAcceptedVersions(PROTOCOL_VERSION::equals)
        .serverAcceptedVersions(PROTOCOL_VERSION::equals)
        .networkProtocolVersion(() -> PROTOCOL_VERSION)
        .simpleChannel();

    public static void logError(String s) {
        try {
            throw new Exception(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void interModEnqueue(final InterModEnqueueEvent event) {
        System.out.println(Ref.MODID + ":InterModEnqueueEvent");
        RegisterCurioSlots.register(event);

    }

    public void clientSetup(final FMLClientSetupEvent event) {
        ClientSetup.setup(event);
    }

    public static MinecraftServer server = null;

    public static <MSG> void sendToTracking(MSG msg, Entity entity) {

        if (msg == null || entity == null) {
            return;
        }
        if (entity.world.isClient) {
            return;
        }

        Network.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), msg);

        if (entity instanceof PlayerEntity) {
            sendToClient(msg, (ServerPlayerEntity) entity);
        }

    }

    public static <MSG> void sendToTracking(MSG msg, BlockPos pos, World world) {

        if (msg == null || world == null) {
            return;
        }
        PlayerStream.watching(world, pos)
            .forEach(x -> {
                Network.send(x, msg);
            });

        ClientSidePacketRegistry.INSTANCE.register();

        PacketDistributor.TargetPoint point = new PacketDistributor.TargetPoint(
            pos.getX(), pos.getY(), pos.getZ(), 50, world.getDimension()
            .getType());

        Network.send(PacketDistributor.NEAR.with(() -> point), msg);

    }

    public static <MSG> void sendToClient(MSG msg, ServerPlayerEntity player) {

        if (player != null && msg != null) {
            Network.sendTo(msg, player.networkHandler.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
        }
    }

    public static <MSG> void sendToServer(MSG msg) {

        Network.sendToServer(msg);
    }

    public void onloadComplete(FMLLoadCompleteEvent evt) {

    }

}
