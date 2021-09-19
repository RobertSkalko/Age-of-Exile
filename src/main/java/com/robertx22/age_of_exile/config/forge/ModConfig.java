package com.robertx22.age_of_exile.config.forge;

public class ModConfig {

    public ClientConfigs client = new ClientConfigs();

    public ServerContainer Server = new ServerContainer();

    public static ModConfig get() {
        return new ModConfig();// TODO
    }

}
