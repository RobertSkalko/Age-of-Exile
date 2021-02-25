package com.robertx22.age_of_exile.config.forge;

import com.robertx22.age_of_exile.config.GuiPartConfig;
import com.robertx22.age_of_exile.config.OverlayGuiConfig;
import com.robertx22.age_of_exile.config.forge.parts.DmgParticleConfig;
import com.robertx22.age_of_exile.gui.overlays.BarGuiType;
import com.robertx22.age_of_exile.gui.overlays.bar_overlays.types.GuiConstants;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayerGUIs;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

import static com.robertx22.age_of_exile.config.GuiPartConfig.IconRenderer;

public class ClientConfigs {

    @ConfigEntry.Gui.CollapsibleObject
    public DmgParticleConfig dmgParticleConfig = new DmgParticleConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public OverlayGuiConfig OVERLAY_GUI = new OverlayGuiConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public GuiPartConfig AREA_LVL_OVERLAY = new GuiPartConfig();

    public boolean RENDER_MOB_HEALTH_GUI = true;
    public boolean ONLY_RENDER_MOB_LOOKED_AT = true;

    public boolean SHOW_LOW_ENERGY_MANA_WARNING = true;
    public boolean RENDER_SIMPLE_MOB_BAR = true;
    public boolean RENDER_DEATH_STATISTICS = true;
    public boolean RENDER_ITEM_RARITY_BACKGROUND = true;
    public GlintType ITEM_RARITY_BACKGROUND_TYPE = GlintType.FULL;

    public enum GlintType {
        BORDER, FULL;
    }

    public float ITEM_RARITY_OPACITY = 0.75F;

    public int REMOVE_EMPTY_TOOLTIP_LINES_IF_MORE_THAN_X_LINES = 32;

    public PlayerGUIs PLAYER_GUI_TYPE = PlayerGUIs.RPG;

    public ClientConfigs() {

        if (OVERLAY_GUI.parts.isEmpty()) {

            OVERLAY_GUI.parts.add(new GuiPartConfig(BarGuiType.HEALTH, new PointData(-91, -39), IconRenderer.LEFT));
            OVERLAY_GUI.parts.add(new GuiPartConfig(BarGuiType.MANA, new PointData(-91, -49), IconRenderer.LEFT));

            OVERLAY_GUI.parts.add(new GuiPartConfig(BarGuiType.EXP,
                new PointData(91 - GuiConstants.BAR_WIDTH, -39), IconRenderer.RIGHT));
            OVERLAY_GUI.parts.add(new GuiPartConfig(BarGuiType.HUNGER,
                new PointData(91 - GuiConstants.BAR_WIDTH, -49), IconRenderer.RIGHT));
            OVERLAY_GUI.parts.add(new GuiPartConfig(BarGuiType.AIR,
                new PointData(91 - GuiConstants.BAR_WIDTH, -59), IconRenderer.RIGHT));

            AREA_LVL_OVERLAY = new GuiPartConfig(BarGuiType.NONE, new PointData(160, -25), IconRenderer.NONE);

        }

    }
}
