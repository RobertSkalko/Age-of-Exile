package com.robertx22.age_of_exile.mmorpg;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.uncommon.error_checks.base.ErrorChecks;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.Cached;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ReloadableResourceManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataLoading {

    public static final ExecutorService ASYNC_EXECUTOR_LOADER = Executors.newSingleThreadScheduledExecutor(r -> new Thread(r, "Age-Of-Exile-Thread"));

    public static void registerLoaders(ReloadableResourceManager manager) {
        SlashRegistryType.getAllInRegisterOrder()
            .forEach(x -> {
                if (x.getLoader() != null) {
                    manager.registerListener(x.getLoader());
                }
            });

        manager.registerListener(new JsonDataLoader(new Gson(), "nothing") {
            @Override
            protected void apply(Map<Identifier, JsonElement> loader, ResourceManager manager, Profiler profiler) {

                try {
                    Cached.reset();

                    Database.backup();

                    Database.checkGuidValidity();

                    ErrorChecks.getAll()
                        .forEach(x -> x.check());

                    Database.unregisterInvalidEntries();

                    // todo does this help at all?
                    Database.getAllRegistries()
                        .forEach(x -> x.onAllDatapacksLoaded());

                    //Database.getAllRegistries()
                    //  .forEach(x -> x.onAllDatapacksLoaded());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }
}
