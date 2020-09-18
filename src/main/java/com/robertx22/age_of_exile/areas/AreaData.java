package com.robertx22.age_of_exile.areas;

import com.robertx22.age_of_exile.areas.area_modifiers.AreaModifier;
import com.robertx22.age_of_exile.areas.area_modifiers.AreaModifiers;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.world.biome.Biome;

@Storable
public class AreaData {

    public static AreaData EMPTY = new AreaData();

    @Store
    public String area_modifier = "";

    public AreaModifier getAreaModifier() {

        AreaModifier area = AreaModifiers.INSTANCE.MAP.get(area_modifier);

        if (area != null) {
            return area;
        }

        return AreaModifiers.INSTANCE.PLAIN;
    }

    public static AreaData random(Biome biome) {
        AreaData data = new AreaData();

        AreaModifier mod = AreaModifiers.INSTANCE.getRandomFor(biome);
        if (mod != null) {
            data.area_modifier = mod.GUID();
        }
        return data;

    }

}
