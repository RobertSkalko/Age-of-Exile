package com.robertx22.age_of_exile.database.registry;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.auto_comp.ItemAutoPowerLevels;
import com.robertx22.age_of_exile.database.IByteBuf;
import com.robertx22.age_of_exile.uncommon.testing.Watch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegistryPackets {

    private static HashMap<SlashRegistryType, List<JsonObject>> map = new HashMap<>();

    public static void add(SlashRegistryType type, JsonObject entry) {

        if (!map.containsKey(type)) {
            map.put(type, new ArrayList<>());
        }
        List<JsonObject> list = map.get(type);
        list.add(entry);
    }

    public static void registerAll(SyncTime sync) {
        Watch watch = new Watch();

        SlashRegistryType.getInRegisterOrder(sync)
            .forEach(type -> {

                if (type.getLoader() != null && type.ser instanceof IByteBuf == false) {

                    SlashRegistryContainer reg = Database.getRegistry(type);

                    reg.unregisterAllEntriesFromDatapacks();

                    List<JsonObject> list = map.get(type);

                    if (list == null || list.isEmpty()) {
                        throw new RuntimeException("Registry list sent from server is empty! " + type.id);
                    }

                    list.forEach(x -> {

                        try {
                            ISerializedRegistryEntry entry = (ISerializedRegistryEntry) type.getSerializer()
                                .fromJson(x);
                            entry.registerToSlashRegistry();

                        } catch (JsonSyntaxException e) {
                            System.out.println("Failed to parse Age of Exile registry Json!!!");
                            e.printStackTrace();
                        }

                    });

                    if (reg
                        .isEmpty()) {
                        throw new RuntimeException("Mine and Slash Registry of type " + reg.getType() + " is EMPTY after datapack loading!");
                    } else {
                        System.out.println(type.name() + " registry load on client succeeded with: " + reg.getSize() + " entries.");
                    }
                }
            });

        watch.print("Registering registry from gson packets on client");

        if (sync == SyncTime.ON_LOGIN) {
            ItemAutoPowerLevels.setupHashMaps();
        }

        map.clear();
    }

}
