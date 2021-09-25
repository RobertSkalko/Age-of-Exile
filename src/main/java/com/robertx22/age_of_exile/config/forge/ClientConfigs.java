package com.robertx22.age_of_exile.config.forge;

import com.robertx22.age_of_exile.gui.overlays.GuiPosition;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayerGUIs;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ClientConfigs {

    public static final ForgeConfigSpec clientSpec;
    public static final ClientConfigs CLIENT;

    static {
        final Pair<ClientConfigs, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfigs::new);
        clientSpec = specPair.getRight();
        CLIENT = specPair.getLeft();
    }

    ClientConfigs(ForgeConfigSpec.Builder b) {
        b.comment("Client Configs")
            .push("general");

        SHOW_LOW_ENERGY_MANA_WARNING = b.define("show_low_mana_warning", true);
        RENDER_SIMPLE_MOB_BAR = b.define("render_mob_bar", true);
        RENDER_DEATH_STATISTICS = b.define("render_death_stats", true);
        RENDER_ITEM_RARITY_BACKGROUND = b.define("render_item_rarity_background", true);
        SHOW_DURABILITY = b.define("show_durability", true);
        RENDER_MOB_HEALTH_GUI = b.define("render_mob_hp_gui", true);
        ONLY_RENDER_MOB_LOOKED_AT = b.define("only_render_mob_looked_at", true);
        SHOW_DURABILITY = b.define("show_durability", true);
        ENABLE_FLOATING_DMG = b.define("render_floating_damage", true);

        b.pop();
    }

    public ForgeConfigSpec.BooleanValue SHOW_LOW_ENERGY_MANA_WARNING;
    public ForgeConfigSpec.BooleanValue ENABLE_FLOATING_DMG;
    public ForgeConfigSpec.BooleanValue RENDER_SIMPLE_MOB_BAR;
    public ForgeConfigSpec.BooleanValue RENDER_DEATH_STATISTICS;
    public ForgeConfigSpec.BooleanValue RENDER_ITEM_RARITY_BACKGROUND;
    public ForgeConfigSpec.BooleanValue SHOW_DURABILITY;
    public ForgeConfigSpec.BooleanValue RENDER_MOB_HEALTH_GUI;
    public ForgeConfigSpec.BooleanValue ONLY_RENDER_MOB_LOOKED_AT;

    public GlintType ITEM_RARITY_BACKGROUND_TYPE = GlintType.FULL;
    public GuiPosition GUI_POSITION = GuiPosition.TOP_LEFT;
    public PlayerGUIs PLAYER_GUI_TYPE = PlayerGUIs.RPG;

    public enum GlintType {
        BORDER, FULL;
    }

    public float ITEM_RARITY_OPACITY = 0.75F;
    public int REMOVE_EMPTY_TOOLTIP_LINES_IF_MORE_THAN_X_LINES = 35;

    public static ClientConfigs getConfig() {
        return CLIENT;
    }

}
