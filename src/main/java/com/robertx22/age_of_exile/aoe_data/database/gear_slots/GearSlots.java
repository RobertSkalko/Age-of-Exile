package com.robertx22.age_of_exile.aoe_data.database.gear_slots;

import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.ids.GearSlotIds;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class GearSlots implements ExileRegistryInit {

    public static String SWORD = new String("sword");
    public static String AXE = new String("axe");
    public static String SCEPTER = new String("scepter");
    public static String BOW = new String("bow");
    public static String CROSBOW = new String("crossbow");
    public static String SCYTHE = new String("scythe");
    public static String SPEAR = new String("spear");
    public static String MACE = new String("mace");
    public static String HAMMER = new String("hammer");
    public static String GLOVE = new String("glove");
    public static String STAFF = new String("staff");
    public static String DAGGER = new String("dagger");

    public static String BOOTS = new String("boots");
    public static String PANTS = new String("pants");
    public static String CHEST = new String("chest");
    public static String HELMET = new String("helmet");

    public static String SHIELD = new String("shield");

    public static String RING = new String("ring");
    public static String NECKLACE = new String("necklace");

    public static String PICKAXE = new String(GearSlotIds.PICKAXE_ID);
    public static String FISHING_ROD = new String(GearSlotIds.FISHING_ROD_ID);
    public static String HOE = new String(GearSlotIds.HOE_ID);

    @Override
    public void registerAll() {

        new GearSlot(SWORD, "Sword", 1, 1000).addToSerializables();
        new GearSlot(SCEPTER, "Scepter", 2, 1000).addToSerializables();
        new GearSlot(STAFF, "Staff", 3, 1500).addToSerializables();
        new GearSlot(AXE, "Axe", 4, 1000).addToSerializables();
        new GearSlot(BOW, "Bow", 5, 750).addToSerializables();
        new GearSlot(CROSBOW, "Crossbow", 6, 750).addToSerializables();

        new GearSlot(BOOTS, "Boots", 7, 1000).addToSerializables();
        new GearSlot(PANTS, "Pants", 8, 1000).addToSerializables();
        new GearSlot(CHEST, "Chest", 9, 1000).addToSerializables();
        new GearSlot(HELMET, "Helmet", 10, 1000).addToSerializables();

        new GearSlot(SHIELD, "Shield", 11, 1000).addToSerializables();

        new GearSlot(RING, "Ring", 12, 750).addToSerializables();
        new GearSlot(NECKLACE, "Necklace", 13, 750).addToSerializables();

        new GearSlot(PICKAXE, "Pickaxe", 14, 0).addToSerializables();
        new GearSlot(FISHING_ROD, "Fishing Rod", 15, 0).addToSerializables();
        new GearSlot(HOE, "Hoe", 16, 0).addToSerializables();

        new GearSlot(DAGGER, "Dagger", 17, 1000).addToSerializables();
        new GearSlot(GLOVE, "Glove", 18, 1000).addToSerializables();
        new GearSlot(MACE, "Mace", 19, 1000).addToSerializables();
        new GearSlot(HAMMER, "Hammer", 20, 1000).addToSerializables();
        new GearSlot(SCYTHE, "Scythe", 21, 1000).addToSerializables();
        new GearSlot(SPEAR, "Spear", 22, 1000).addToSerializables();

    }
}
