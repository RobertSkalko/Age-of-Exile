package com.robertx22.age_of_exile.uncommon.testing.tests;

import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;

public class CountUniqueGearTypes {

    public static void count() {

        System.out.println("[UNIQUES PER SLOT");

        for (BaseGearType slot : SlashRegistry.GearTypes().getList()) {

            int amount = SlashRegistry.UniqueGears()
                    .getWrapped()
                    .ofSpecificGearType(slot.GUID()).list.size();

            System.out.println(slot.GUID() + " has " + amount + " uniques");

        }
        System.out.println("[UNIQUES PER SLOT END");

    }

}
