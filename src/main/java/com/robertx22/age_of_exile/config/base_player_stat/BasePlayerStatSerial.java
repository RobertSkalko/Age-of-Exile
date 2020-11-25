package com.robertx22.age_of_exile.config.base_player_stat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.robertx22.age_of_exile.config.base.ISerializedConfig;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.mmorpg.registers.common.ConfigRegister;
import com.robertx22.age_of_exile.uncommon.utilityclasses.SerializationUtils;

import java.util.HashSet;

public class BasePlayerStatSerial implements ISerializedConfig<BasePlayerStatContainer> {
    public static BasePlayerStatSerial INSTANCE = new BasePlayerStatSerial();

    @Override
    public String fileName() {
        return "BasePlayerStats.txt";
    }

    @Override
    public BasePlayerStatContainer getDefaultObject() {
        return BasePlayerStatContainer.defaultStats();
    }

    @Override
    public BasePlayerStatContainer loadFromString(String string) {
        return new Gson().fromJson(string, BasePlayerStatContainer.class);
    }

    @Override
    public void autoFixProblems() {

        BasePlayerStatContainer currentConfig = loadFromString(getJsonFromFile(getPath()));

        BasePlayerStatContainer defaultConfig = getDefaultObject();

        new HashSet<>(currentConfig.NON_SCALED.entrySet()).stream()
            .filter(x -> !SlashRegistry.Stats()
                .isRegistered(x.getKey()))
            .forEach(p -> {
                currentConfig.NON_SCALED.remove(p.getKey());
            });

        defaultConfig.NON_SCALED.entrySet()
            .forEach(x -> {
                if (!currentConfig.NON_SCALED.containsKey(x.getKey())) {
                    currentConfig.NON_SCALED.put(x.getKey(), x.getValue());
                }
            });

        Gson gson = new GsonBuilder().setPrettyPrinting()
            .create();
        String json = gson.toJson(currentConfig);
        SerializationUtils.makeFileAndDirAndWrite(folder(), fileName(), json, true);
    }

    @Override
    public ConfigRegister.Config getConfigType() {
        return ConfigRegister.Config.BASE_PLAYER_STATS;
    }
}
