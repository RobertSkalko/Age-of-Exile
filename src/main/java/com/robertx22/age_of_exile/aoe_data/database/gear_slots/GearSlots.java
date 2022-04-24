package com.robertx22.age_of_exile.aoe_data.database.gear_slots;

import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gear_types.bases.SlotFamily;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class GearSlots implements ExileRegistryInit {

    public static String SWORD = new String("sword");
    public static String AXE = new String("axe");
    public static String SCEPTER = new String("scepter");
    public static String BOW = new String("bow");
    public static String CROSBOW = new String("crossbow");
    public static String STAFF = new String("staff");

    public static String BOOTS = new String("boots");
    public static String PANTS = new String("pants");
    public static String CHEST = new String("chest");
    public static String HELMET = new String("helmet");

    public static String SHIELD = new String("shield");

    public static String RING = new String("ring");
    public static String NECKLACE = new String("necklace");

    @Override
    public void registerAll() {

        new GearSlot(SWORD, "Sword", SlotFamily.Weapon, 1, 1000).addToSerializables();
        new GearSlot(SCEPTER, "Scepter", SlotFamily.Weapon, 2, 1000).addToSerializables();
        new GearSlot(STAFF, "Staff", SlotFamily.Weapon, 3, 1500).addToSerializables();
        new GearSlot(AXE, "Axe", SlotFamily.Weapon, 4, 1000).addToSerializables();
        new GearSlot(BOW, "Bow", SlotFamily.Weapon, 5, 750).addToSerializables();
        new GearSlot(CROSBOW, "Crossbow", SlotFamily.Weapon, 6, 750).addToSerializables();

        new GearSlot(BOOTS, "Boots", SlotFamily.Armor, 7, 1000).addToSerializables();
        new GearSlot(PANTS, "Pants", SlotFamily.Armor, 8, 1000).addToSerializables();
        new GearSlot(CHEST, "Chest", SlotFamily.Armor, 9, 1000).addToSerializables();
        new GearSlot(HELMET, "Helmet", SlotFamily.Armor, 10, 1000).addToSerializables();

        new GearSlot(SHIELD, "Shield", SlotFamily.OffHand, 11, 1000).addToSerializables();

        new GearSlot(RING, "Ring", SlotFamily.Jewelry, 12, 750).addToSerializables();
        new GearSlot(NECKLACE, "Necklace", SlotFamily.Jewelry, 13, 750).addToSerializables();

    }
}
