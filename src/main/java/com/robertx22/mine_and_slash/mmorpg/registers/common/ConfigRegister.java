package com.robertx22.mine_and_slash.mmorpg.registers.common;

import com.robertx22.mine_and_slash.config.base.ISerializedConfig;
import com.robertx22.mine_and_slash.config.base_player_stat.BasePlayerStatSerial;
import com.robertx22.mine_and_slash.config.forge.ClientContainer;
import com.robertx22.mine_and_slash.config.forge.CommonConfig;
import com.robertx22.mine_and_slash.config.forge.ModConfig;
import com.robertx22.exiled_lib.registry.ISlashRegistryEntry;
import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.exiled_lib.registry.SlashRegistryContainer;

import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig.Type;

import java.util.HashMap;
import java.util.List;

public class ConfigRegister {

    public static HashMap<Config, ISerializedConfig> CONFIGS = new HashMap<>();
    public static HashMap<Config, List<String>> SAVED_JSONS = new HashMap<>();

    public enum Config {
        BASE_PLAYER_STATS
    }

    public static void registerCustomConfigs() {

        CONFIGS.put(Config.BASE_PLAYER_STATS, BasePlayerStatSerial.INSTANCE);

        unregisterFlaggedEntries(); // call first

        generateIfEmpty();

        CONFIGS.values()
            .forEach(x -> x.autoFixProblems());

        load();

    }

    // should be called only on server, then packets sent to client
    private static void load() {
        CONFIGS.values()
            .forEach(x -> x.loadOnServer());
    }

    private static void generateIfEmpty() {
        CONFIGS.values()
            .forEach(x -> x.generateIfEmpty());
    }

    public static void unregisterFlaggedEntries() {

        for (SlashRegistryContainer container : SlashRegistry.getAllRegistries()) {

            List<ISlashRegistryEntry> list = container.getList();

            for (ISlashRegistryEntry entry : list) {
                if (entry.unregisterBeforeConfigsLoad()) {
                    container.unRegister(entry);
                }
            }

        }

    }

    // MUST BE CALLED IN MAIN CLASS
    public static void registerForgeConfigs() {

        ModLoadingContext ctx = ModLoadingContext.get();

        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            ctx.registerConfig(Type.CLIENT, ClientContainer.spec, "MineAndSlash-Client.toml");
        });

        ctx.registerConfig(Type.SERVER, ModConfig.spec, "MineAndSlash-Server.toml");
        ctx.registerConfig(Type.COMMON, CommonConfig.spec, "MineAndSlash-Common.toml");
    }

}
