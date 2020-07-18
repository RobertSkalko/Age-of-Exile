package com.robertx22.mine_and_slash.config.forge;

import com.robertx22.mine_and_slash.config.forge.parts.AutoCompatibleItemConfig;
import com.robertx22.mine_and_slash.config.forge.parts.DropRatesContainer;
import com.robertx22.mine_and_slash.config.forge.parts.RarityDropratesConfig;

public class ModConfig {

    public RarityDropratesConfig RarityWeightConfig = new RarityDropratesConfig();
    public ServerContainer Server = new ServerContainer();
    public DropRatesContainer DropRates = new DropRatesContainer();
    public AutoCompatibleItemConfig autoCompatibleItems = new AutoCompatibleItemConfig();
    public StatScaleConfigs statScalings = new StatScaleConfigs();

    public static final String NAME = "SERVER";
    public static final ModConfig INSTANCE = new ModConfig();

}
