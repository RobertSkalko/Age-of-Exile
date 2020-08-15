package com.robertx22.age_of_exile.areas.base_areas;

import net.minecraft.world.biome.Biome.Category;

public class BaseArea {

    public String id;
    public String locname;
    public Category category;

    public BaseArea(String id, String locname, Category category) {
        this.id = id;
        this.locname = locname;
        this.category = category;
    }

}
