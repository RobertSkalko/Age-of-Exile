package com.robertx22.age_of_exile.config.forge;

import com.robertx22.age_of_exile.config.forge.parts.DmgParticleConfig;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayerGUIs;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

public class ClientConfigs {

    @ConfigEntry.Gui.CollapsibleObject
    public DmgParticleConfig dmgParticleConfig = new DmgParticleConfig();

    public boolean RENDER_MOB_HEALTH_GUI = true;
    public boolean ONLY_RENDER_MOB_LOOKED_AT = true;

    public boolean SHOW_LOW_ENERGY_MANA_WARNING = true;
    public boolean RENDER_SIMPLE_MOB_BAR = true;
    public boolean RENDER_AREA_INDICATOR = true;

    public int REMOVE_EMPTY_TOOLTIP_LINES_IF_MORE_THAN_X_LINES = 32;

    public int LEFT_VANILLA_LIKE_BARS_Y__POS_ADJUST = 0;
    public int RIGHT_VANILLA_LIKE_BARS_Y__POS_ADJUST = 0;

    public PlayerGUIs PLAYER_GUI_TYPE = PlayerGUIs.RPG;

}
