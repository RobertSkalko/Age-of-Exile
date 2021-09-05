package com.robertx22.age_of_exile.config;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.gui.overlays.BarGuiType;
import com.robertx22.age_of_exile.saveclasses.PointData;

public class GuiPartConfig {

    public BarGuiType type = BarGuiType.HEALTH;
    PointData position_offset = new PointData(0, 0);
    public boolean enabled = true;
    public IconRenderer icon_renderer = IconRenderer.NONE;

    public PointData getPosition() {
        PointData pos = ModConfig.get().client.GUI_POSITION.getPos();

        return new PointData(pos.x + position_offset.x, pos.y + position_offset.y);
    }

    public GuiPartConfig(BarGuiType type, PointData position_offset) {
        this.type = type;
        this.position_offset = position_offset;
    }

    public GuiPartConfig() {
    }

    public enum IconRenderer {
        NONE, LEFT, RIGHT
    }
}
