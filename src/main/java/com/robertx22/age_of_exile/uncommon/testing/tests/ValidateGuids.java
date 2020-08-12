package com.robertx22.age_of_exile.uncommon.testing.tests;

import com.robertx22.age_of_exile.database.registry.ISlashRegistryEntry;
import com.robertx22.age_of_exile.database.data.IGUID;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class ValidateGuids {
    public static void validate() {

        for (Item item : Registry.ITEM) {

            if (item instanceof ISlashRegistryEntry) {
                IGUID guid = (IGUID) item;

                if (!Registry.ITEM.getId(item)
                    .getPath()
                    .equals(guid.GUID())) {
                    System.out.println("Guid doesn't match: " + Registry.ITEM.getId(item)
                        .toString());
                }

            }

        }

    }
}
