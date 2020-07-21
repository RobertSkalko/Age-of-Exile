package com.robertx22.mine_and_slash.mmorpg.mod_menu;

import com.robertx22.mine_and_slash.config.forge.ModConfig;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (screen) -> {
            return AutoConfig.getConfigScreen(ModConfig.class, screen)
                .get();
        };
    }

    @Override
    public String getModId() {
        return Ref.MODID;
    }
}
