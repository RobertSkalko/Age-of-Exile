package com.robertx22.age_of_exile.config;

import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

import java.util.ArrayList;
import java.util.List;

public class OverlayGuiConfig {

    @ConfigEntry.Gui.CollapsibleObject
    public List<GuiPartConfig> parts = new ArrayList<>();

    public OverlayGuiConfig() {
    }
}
