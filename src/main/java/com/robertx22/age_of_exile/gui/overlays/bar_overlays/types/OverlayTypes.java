package com.robertx22.age_of_exile.gui.overlays.bar_overlays.types;

import com.robertx22.age_of_exile.config.GuiPartConfig;
import com.robertx22.age_of_exile.gui.overlays.BarGuiType;
import com.robertx22.age_of_exile.gui.overlays.GuiPosition;
import com.robertx22.age_of_exile.saveclasses.PointData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OverlayTypes {

    public static HashMap<GuiPosition, List<GuiPartConfig>> map = new HashMap<>();

    static {

        List<GuiPartConfig> topleft = new ArrayList<>();
        topleft.add(new GuiPartConfig(BarGuiType.HEALTH, new PointData(5, 5)));
        topleft.add(new GuiPartConfig(BarGuiType.MANA, new PointData(5, 16)));
        topleft.add(new GuiPartConfig(BarGuiType.EXP, new PointData(5, 27)));
        map.put(GuiPosition.TOP_LEFT, topleft);

        List<GuiPartConfig> middle = new ArrayList<>();
        middle.add(new GuiPartConfig(BarGuiType.HEALTH, new PointData(-198, -22)));
        middle.add(new GuiPartConfig(BarGuiType.EXP, new PointData(-198, -11)));

        middle.add(new GuiPartConfig(BarGuiType.MANA, new PointData(90, -22)));
        //  middle.add(new GuiPartConfig(BarGuiType.ENERGY, new PointData(90, -11)));
        map.put(GuiPosition.BOTTOM_CENTER, middle);

    }
}
