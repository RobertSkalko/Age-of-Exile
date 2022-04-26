package com.robertx22.age_of_exile.aoe_data.database.gear_slots;

import com.robertx22.age_of_exile.database.all_keys.GearSlotKeys;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gear_types.bases.SlotFamily;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class GearSlots implements ExileRegistryInit {

    @Override
    public void registerAll() {

        new GearSlot(GearSlotKeys.SWORD, "Sword", SlotFamily.Weapon, 1, 1000).addToSerializables();
        new GearSlot(GearSlotKeys.STAFF, "Staff", SlotFamily.Weapon, 3, 1500).addToSerializables();
        new GearSlot(GearSlotKeys.AXE, "Axe", SlotFamily.Weapon, 4, 1000).addToSerializables();
        new GearSlot(GearSlotKeys.BOW, "Bow", SlotFamily.Weapon, 5, 750).addToSerializables();
        new GearSlot(GearSlotKeys.CROSBOW, "Crossbow", SlotFamily.Weapon, 6, 750).addToSerializables();

        new GearSlot(GearSlotKeys.BOOTS, "Boots", SlotFamily.Armor, 7, 1000).addToSerializables();
        new GearSlot(GearSlotKeys.PANTS, "Pants", SlotFamily.Armor, 8, 1000).addToSerializables();
        new GearSlot(GearSlotKeys.CHEST, "Chest", SlotFamily.Armor, 9, 1000).addToSerializables();
        new GearSlot(GearSlotKeys.HELMET, "Helmet", SlotFamily.Armor, 10, 1000).addToSerializables();

        new GearSlot(GearSlotKeys.SHIELD, "Shield", SlotFamily.OffHand, 11, 1000).addToSerializables();

        new GearSlot(GearSlotKeys.RING, "Ring", SlotFamily.Jewelry, 12, 750).addToSerializables();
        new GearSlot(GearSlotKeys.NECKLACE, "Necklace", SlotFamily.Jewelry, 13, 750).addToSerializables();

    }
}
