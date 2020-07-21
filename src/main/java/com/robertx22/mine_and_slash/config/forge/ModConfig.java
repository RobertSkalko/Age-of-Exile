package com.robertx22.mine_and_slash.config.forge;

import com.robertx22.mine_and_slash.config.forge.parts.AutoCompatibleItemConfig;
import com.robertx22.mine_and_slash.config.forge.parts.DropRatesContainer;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

@Config(name = "axe_of_exile")
public class ModConfig implements ConfigData {

    @ConfigEntry.Gui.CollapsibleObject
    ClientConfigs client = new ClientConfigs();

    @ConfigEntry.Gui.CollapsibleObject
    public ServerContainer Server = new ServerContainer();

    @ConfigEntry.Gui.CollapsibleObject
    public DropRatesContainer DropRates = new DropRatesContainer();

    @ConfigEntry.Gui.CollapsibleObject
    public AutoCompatibleItemConfig autoCompatibleItems = new AutoCompatibleItemConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public StatScaleConfigs statScalings = new StatScaleConfigs();

    public static final ModConfig INSTANCE = new ModConfig();

}
