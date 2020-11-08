package com.robertx22.age_of_exile.config.forge;

import com.robertx22.age_of_exile.config.AllLevelRewardsConfig;
import com.robertx22.age_of_exile.config.forge.parts.AutoCompatibleItemConfig;
import com.robertx22.age_of_exile.config.forge.parts.DropRatesContainer;
import com.robertx22.age_of_exile.config.forge.parts.FoodEffectsConfig;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

@Config(name = "age_of_exile")
public class ModConfig implements ConfigData {

    @ConfigEntry.Gui.CollapsibleObject
    public ClientConfigs client = new ClientConfigs();

    @ConfigEntry.Gui.CollapsibleObject
    public ServerContainer Server = new ServerContainer();

    @ConfigEntry.Gui.CollapsibleObject
    public FavorConfig Favor = new FavorConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public AllLevelRewardsConfig LevelRewards = new AllLevelRewardsConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public ItemSealingConfig ItemSealing = new ItemSealingConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public DropRatesContainer DropRates = new DropRatesContainer();

    @ConfigEntry.Gui.CollapsibleObject
    public AutoCompatibleItemConfig autoCompatibleItems = new AutoCompatibleItemConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public StatScaleConfigs statScalings = new StatScaleConfigs();

    @ConfigEntry.Gui.CollapsibleObject
    public FoodEffectsConfig foodEffects = new FoodEffectsConfig();

    public static ModConfig get() {
        return AutoConfig.getConfigHolder(ModConfig.class)
            .getConfig();
    }

}
