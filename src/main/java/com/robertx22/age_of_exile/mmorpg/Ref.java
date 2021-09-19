package com.robertx22.age_of_exile.mmorpg;

import net.minecraft.util.ResourceLocation;

public class Ref {

    public static ResourceLocation id(String id) {
        return new ResourceLocation(MODID, id);
    }

    public static ResourceLocation guiId(String id) {
        return new ResourceLocation(Ref.MODID, "textures/gui/" + id + ".png");
    }

    public static final String MODID = "mmorpg";
    public static final String MOD_NAME = "Age of Exile";

    public static String WORLD_OF_EXILE_ID = "world_of_exile";
}
