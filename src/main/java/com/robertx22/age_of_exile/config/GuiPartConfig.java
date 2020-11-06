package com.robertx22.age_of_exile.config;

import com.robertx22.age_of_exile.gui.overlays.BarGuiType;
import com.robertx22.age_of_exile.gui.overlays.GuiPosition;
import com.robertx22.age_of_exile.saveclasses.PointData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

public class GuiPartConfig {

    public BarGuiType type = BarGuiType.HEALTH;

    GuiPosition position = GuiPosition.BOTTOM_CENTER;
    @ConfigEntry.Gui.CollapsibleObject
    PointData position_offset = new PointData(0, 0);
    public boolean enabled = true;
    public IconRenderer icon_renderer = IconRenderer.LEFT;

    public PointData getPosition() {
        return new PointData(position.getPos().x + position_offset.x, position.getPos().y + position_offset.y);
    }

    public GuiPartConfig(BarGuiType type, PointData position_offset, IconRenderer iconRenderer) {
        this.type = type;
        this.position_offset = position_offset;
        this.icon_renderer = iconRenderer;
    }

    public GuiPartConfig() {
    }

    public enum IconRenderer {
        NONE, LEFT, RIGHT
    }
}
