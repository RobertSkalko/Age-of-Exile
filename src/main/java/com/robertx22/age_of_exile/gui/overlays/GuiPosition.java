package com.robertx22.age_of_exile.gui.overlays;

import com.robertx22.age_of_exile.saveclasses.PointData;
import net.minecraft.client.Minecraft;

public enum GuiPosition {

    BOTTOM_CENTER {
        @Override
        public PointData getPos() {
            return new PointData(
                mc().getWindow()
                    .getGuiScaledWidth() / 2,
                mc().getWindow()
                    .getGuiScaledHeight());
        }
    },
    TOP_LEFT {
        @Override
        public PointData getPos() {
            return new PointData(0, 0);
        }
    };

    public abstract PointData getPos();

    static Minecraft mc() {
        return Minecraft.getInstance();
    }
}
