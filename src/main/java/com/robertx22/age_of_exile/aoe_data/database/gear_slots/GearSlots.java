package com.robertx22.age_of_exile.aoe_data.database.gear_slots;

import com.robertx22.age_of_exile.aoe_data.base.DataGenKey;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class GearSlots implements ISlashRegistryInit {

    public static String PICKAXE_ID = "pickaxe";
    public static String FISHING_ROD_ID = "fishing_rod";
    public static String HOE_ID = "hoe";

    public static DataGenKey<GearSlot> SWORD = new DataGenKey<GearSlot>("sword");
    public static DataGenKey<GearSlot> AXE = new DataGenKey<GearSlot>("axe");
    public static DataGenKey<GearSlot> WAND = new DataGenKey<GearSlot>("wand");
    public static DataGenKey<GearSlot> BOW = new DataGenKey<GearSlot>("bow");
    public static DataGenKey<GearSlot> CROSBOW = new DataGenKey<GearSlot>("crossbow");

    public static DataGenKey<GearSlot> BOOTS = new DataGenKey<GearSlot>("boots");
    public static DataGenKey<GearSlot> PANTS = new DataGenKey<GearSlot>("pants");
    public static DataGenKey<GearSlot> CHEST = new DataGenKey<GearSlot>("chest");
    public static DataGenKey<GearSlot> HELMET = new DataGenKey<GearSlot>("helmet");

    public static DataGenKey<GearSlot> SHIELD = new DataGenKey<GearSlot>("shield");

    public static DataGenKey<GearSlot> RING = new DataGenKey<GearSlot>("ring");
    public static DataGenKey<GearSlot> NECKLACE = new DataGenKey<GearSlot>("necklace");

    public static DataGenKey<GearSlot> PICKAXE = new DataGenKey<GearSlot>(PICKAXE_ID);
    public static DataGenKey<GearSlot> FISHING_ROD = new DataGenKey<GearSlot>(FISHING_ROD_ID);
    public static DataGenKey<GearSlot> HOE = new DataGenKey<GearSlot>(HOE_ID);

    @Override
    public void registerAll() {

        new GearSlot(SWORD, 1000).addToSerializables();
        new GearSlot(AXE, 1000).addToSerializables();
        new GearSlot(WAND, 1250).addToSerializables();
        new GearSlot(BOW, 750).addToSerializables();
        new GearSlot(CROSBOW, 750).addToSerializables();

        new GearSlot(BOOTS, 1000).addToSerializables();
        new GearSlot(PANTS, 1000).addToSerializables();
        new GearSlot(CHEST, 1000).addToSerializables();
        new GearSlot(HELMET, 1000).addToSerializables();

        new GearSlot(SHIELD, 1000).addToSerializables();

        new GearSlot(RING, 750).addToSerializables();
        new GearSlot(NECKLACE, 750).addToSerializables();

        new GearSlot(PICKAXE, 0).addToSerializables();
        new GearSlot(FISHING_ROD, 0).addToSerializables();
        new GearSlot(HOE, 0).addToSerializables();

    }
}
