package com.robertx22.mine_and_slash.config.forge;

import com.robertx22.mine_and_slash.config.forge.parts.DmgParticleConfig;
import com.robertx22.mine_and_slash.uncommon.enumclasses.PlayerGUIs;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.*;
import net.minecraftforge.common.ForgeConfigSpec.EnumValue;
import org.apache.commons.lang3.tuple.Pair;

boolean;

public class ClientConfigs {

    public static final String NAME = "CLIENT";
    public static final ForgeConfigSpec spec;
    public static final ClientConfigs INSTANCE;

    static {
        final Pair<ClientConfigs, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(
            ClientConfigs::new);
        spec = specPair.getRight();
        INSTANCE = specPair.getLeft();

    }

    public DmgParticleConfig dmgParticleConfig;

    public boolean RENDER_ITEM_NAMES_ON_GROUND;
    public boolean RENDER_MOB_HEALTH_GUI;
    public boolean SHOW_LOW_ENERGY_MANA_WARNING;
    public boolean RENDER_SIMPLE_MOB_BAR;
    public ForgeConfigSpec .
    int REMOVE_EMPTY_TOOLTIP_LINES_IF_MORE_THAN_X_LINES;

    public ForgeConfigSpec .
    int LEFT_VANILLA_LIKE_BARS_Y__POS_ADJUST;
    public ForgeConfigSpec .
    int RIGHT_VANILLA_LIKE_BARS_Y__POS_ADJUST;

    public EnumValue<PlayerGUIs> PLAYER_GUI_TYPE;

    ClientConfigs(ForgeConfigSpec.Builder builder) {
        builder.comment("Client Settings")
            .push(NAME);

        LEFT_VANILLA_LIKE_BARS_Y__POS_ADJUST = builder.comment(".")
            .defineInRange("LEFT_VANILLA_LIKE_BARS_Y__POS_ADJUST", 0, -10000, 10000);
        RIGHT_VANILLA_LIKE_BARS_Y__POS_ADJUST = builder.comment(".")
            .defineInRange("RIGHT_VANILLA_LIKE_BARS_Y__POS_ADJUST", 0, -10000, 10000);

        dmgParticleConfig = builder.configure(DmgParticleConfig::new)
            .getLeft();

        REMOVE_EMPTY_TOOLTIP_LINES_IF_MORE_THAN_X_LINES = builder.comment(".")
            .defineInRange("REMOVE_EMPTY_TOOLTIP_LINES_IF_MORE_THAN_X_LINES", 35, 0, 1000);

        RENDER_SIMPLE_MOB_BAR = builder.comment(".")
            .translation("mmorpg.config.")
            .define("RENDER_SIMPLE_MOB_BAR", true);

        RENDER_ITEM_NAMES_ON_GROUND = builder.comment(".")
            .define("RENDER_ITEM_NAMES_ON_GROUND", true);

        RENDER_MOB_HEALTH_GUI = builder.comment(".")
            .translation("mmorpg.config.mob_health_bar")
            .define("RENDER_MOB_HEALTH_GUI", true);

        SHOW_LOW_ENERGY_MANA_WARNING = builder.comment(".")
            .translation("mmorpg.config.low_resource_warnings")
            .define("SHOW_LOW_ENERGY_MANA_WARNING", true);

        PLAYER_GUI_TYPE = builder.comment(".")
            .translation("mmorpg.config.player_gui_overlay_type")
            .defineEnum("PLAYER_GUI_TYPE", PlayerGUIs.Vanilla);

        builder.pop();
    }

}
