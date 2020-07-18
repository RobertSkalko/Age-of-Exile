package com.robertx22.mine_and_slash.config.forge;

import com.robertx22.mine_and_slash.config.forge.parts.DmgParticleConfig;
import com.robertx22.mine_and_slash.uncommon.enumclasses.PlayerGUIs;

public class ClientConfigs {

    public static final String NAME = "CLIENT";
    public static final ClientConfigs INSTANCE = new ClientConfigs();

    public DmgParticleConfig dmgParticleConfig = new DmgParticleConfig();

    public boolean RENDER_MOB_HEALTH_GUI = true;
    public boolean SHOW_LOW_ENERGY_MANA_WARNING = true;
    public boolean RENDER_SIMPLE_MOB_BAR = true;
    public int REMOVE_EMPTY_TOOLTIP_LINES_IF_MORE_THAN_X_LINES = 32;

    public int LEFT_VANILLA_LIKE_BARS_Y__POS_ADJUST = 0;
    public int RIGHT_VANILLA_LIKE_BARS_Y__POS_ADJUST = 0;

    public PlayerGUIs PLAYER_GUI_TYPE = PlayerGUIs.Vanilla;

}
