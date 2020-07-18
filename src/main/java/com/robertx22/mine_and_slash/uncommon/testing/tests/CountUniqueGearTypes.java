package com.robertx22.mine_and_slash.uncommon.testing.tests;

import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.exiled_lib.registry.SlashRegistry;

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
