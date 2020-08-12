package com.robertx22.age_of_exile.database.registrators;

import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class GearSlots implements ISlashRegistryInit {

    public static GearSlot SWORD = new GearSlot("sword", 1000);
    public static GearSlot AXE = new GearSlot("axe", 1000);
    public static GearSlot WAND = new GearSlot("wand", 1250);
    public static GearSlot BOW = new GearSlot("bow", 750);
    public static GearSlot CROSBOW = new GearSlot("crossbow", 750);

    public static GearSlot BOOTS = new GearSlot("boots", 1000);
    public static GearSlot PANTS = new GearSlot("pants", 1000);
    public static GearSlot CHEST = new GearSlot("chest", 1000);
    public static GearSlot HELMET = new GearSlot("helmet", 1000);

    public static GearSlot SHIELD = new GearSlot("shield", 1000);

    public static GearSlot RING = new GearSlot("helmet", 750);
    public static GearSlot NECKLACE = new GearSlot("helmet", 750);

    @Override
    public void registerAll() {

        SWORD.addToSerializables();
        AXE.addToSerializables();
        WAND.addToSerializables();
        BOW.addToSerializables();

        BOOTS.addToSerializables();
        PANTS.addToSerializables();
        CHEST.addToSerializables();
        HELMET.addToSerializables();

        SHIELD.addToSerializables();

        NECKLACE.addToSerializables();
        RING.addToSerializables();

    }
}
