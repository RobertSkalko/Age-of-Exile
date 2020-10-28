package com.robertx22.age_of_exile.mmorpg;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.uncommon.error_checks.base.ErrorChecks;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.Cached;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ReloadableResourceManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.Map;

public class DataLoading {

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

                Cached.reset();

                SlashRegistry.backup();

                SlashRegistry.checkGuidValidity();
                ErrorChecks.getAll()
                    .forEach(x -> x.check());
                SlashRegistry.unregisterInvalidEntries();

                SlashRegistry.getAllRegistries()
                    .forEach(x -> x.onAllDatapacksLoaded());

            }
        });

    }
}
