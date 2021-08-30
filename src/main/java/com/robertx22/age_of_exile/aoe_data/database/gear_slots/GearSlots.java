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

        new GearSlot(SWORD, "Sword", 1000).addToSerializables();
        new GearSlot(SCEPTER, "Scepter", 1000).addToSerializables();
        new GearSlot(STAFF, "Staff", 1500).addToSerializables();
        new GearSlot(DAGGER, "Dagger", 1000).addToSerializables();
        new GearSlot(GLOVE, "Glove", 1000).addToSerializables();
        new GearSlot(MACE, "Mace", 1000).addToSerializables();
        new GearSlot(HAMMER, "Hammer", 1000).addToSerializables();
        new GearSlot(SCYTHE, "Scythe", 1000).addToSerializables();
        new GearSlot(SPEAR, "Spear", 1000).addToSerializables();
        new GearSlot(AXE, "Axe", 1000).addToSerializables();
        new GearSlot(BOW, "Bow", 750).addToSerializables();
        new GearSlot(CROSBOW, "Crossbow", 750).addToSerializables();

        new GearSlot(BOOTS, "Boots", 1000).addToSerializables();
        new GearSlot(PANTS, "Pants", 1000).addToSerializables();
        new GearSlot(CHEST, "Chest", 1000).addToSerializables();
        new GearSlot(HELMET, "Helmet", 1000).addToSerializables();

        new GearSlot(SHIELD, "Shield", 1000).addToSerializables();

        new GearSlot(RING, "Ring", 750).addToSerializables();
        new GearSlot(NECKLACE, "Necklace", 750).addToSerializables();

        new GearSlot(PICKAXE, "Pickaxe", 0).addToSerializables();
        new GearSlot(FISHING_ROD, "Fishing Rod", 0).addToSerializables();
        new GearSlot(HOE, "Hoe", 0).addToSerializables();

    }
}
