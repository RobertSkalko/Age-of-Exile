package com.robertx22.age_of_exile.config.forge;

import com.robertx22.age_of_exile.config.GuiPartConfig;
import com.robertx22.age_of_exile.gui.overlays.GuiPosition;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayerGUIs;

public class ClientConfigs {

    BooleanV

    public boolean ENABLE_FLOATING_DMG = true;

    public GuiPartConfig AREA_LVL_OVERLAY = new GuiPartConfig();

    public boolean RENDER_MOB_HEALTH_GUI = true;
    public boolean ONLY_RENDER_MOB_LOOKED_AT = true;

    public boolean SHOW_LOW_ENERGY_MANA_WARNING = true;
    public boolean ALWAYS_RENDER_LEVEL_OVERLAY = false;
    public boolean RENDER_SIMPLE_MOB_BAR = true;
    public boolean RENDER_DEATH_STATISTICS = true;
    public boolean RENDER_ITEM_RARITY_BACKGROUND = true;
    public boolean SHOW_DURABILITY = true;
    public GlintType ITEM_RARITY_BACKGROUND_TYPE = GlintType.FULL;
    public GuiPosition GUI_POSITION = GuiPosition.TOP_LEFT;

    public enum GlintType {
        BORDER, FULL;
    }

    public float ITEM_RARITY_OPACITY = 0.75F;

    public int REMOVE_EMPTY_TOOLTIP_LINES_IF_MORE_THAN_X_LINES = 35;

    public PlayerGUIs PLAYER_GUI_TYPE = PlayerGUIs.RPG;

    public ClientConfigs() {

    }
}
