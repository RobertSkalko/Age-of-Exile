package com.robertx22.age_of_exile.uncommon.testing.tests;

import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.registry.Database;

public class CountUniqueGearTypes {

    public static void count() {

        System.out.println("[UNIQUES PER SLOT");

        for (BaseGearType slot : Database.GearTypes()
            .getList()) {

            int amount = Database.UniqueGears()
                .getFilterWrapped(x -> x.getPossibleGearTypes()
                    .contains(slot))
                .list.size();

            System.out.println(slot.GUID() + " has " + amount + " uniques");

        }
        System.out.println("[UNIQUES PER SLOT END");

    }

}
