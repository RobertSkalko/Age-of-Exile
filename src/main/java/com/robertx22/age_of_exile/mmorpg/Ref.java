package com.robertx22.age_of_exile.mmorpg;

import net.minecraft.util.Identifier;

public class Ref {

    public static Identifier id(String id) {
        return new Identifier(MODID, id);
    }

    public static final String MODID = "mmorpg";
    public static final String VERSION = "1.0.0";
    public static final String MOD_NAME = "Age of Exile";
    public static final String DESC = "Turn Minecraft into a hack and slash looter!";

}
