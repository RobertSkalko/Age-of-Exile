package com.robertx22.mine_and_slash.uncommon.testing.tests;

import com.robertx22.mine_and_slash.database.data.IGUID;
import com.robertx22.exiled_lib.registry.ISlashRegistryEntry;
import net.minecraft.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class ValidateGuids {
    public static void validate() {

        for (Item item : ForgeRegistries.ITEMS) {

            if (item instanceof ISlashRegistryEntry) {
                IGUID guid = (IGUID) item;

                if (!item.getRegistryName().getPath().equals(guid.GUID())) {
                    System.out.println("Guid doesn't match: " + item.getRegistryName()
                            .toString());
                }

            }

        }

    }
}
