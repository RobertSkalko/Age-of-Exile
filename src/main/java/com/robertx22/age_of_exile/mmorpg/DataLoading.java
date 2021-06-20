package com.robertx22.age_of_exile.mmorpg;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.AttributeStat;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.uncommon.error_checks.base.ErrorChecks;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.Cached;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ReloadableResourceManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.ArrayList;
import java.util.Map;

public class DataLoading {

    //public static final ExecutorService ASYNC_EXECUTOR_LOADER = Executors.newSingleThreadScheduledExecutor(r -> new Thread(r, "Age-Of-Exile-Thread"));

    private static void setupStatsThatAffectVanillaStatsList() {
        Cached.VANILLA_STAT_UIDS_TO_CLEAR_EVERY_STAT_CALC = new ArrayList<>();

        Database.Stats()
            .getFilterWrapped(x -> x instanceof AttributeStat).list.forEach(x -> {
            AttributeStat attri = (AttributeStat) x;
            Cached.VANILLA_STAT_UIDS_TO_CLEAR_EVERY_STAT_CALC.add(ImmutablePair.of(attri.attribute, attri.uuid));
        });
    }

    public static void registerLoaders(ReloadableResourceManager manager) {
        ExileRegistryTypes.getAllInRegisterOrder()
            .forEach(x -> {
                if (x.getLoader() != null) {
                    manager.registerReloader(x.getLoader());
                }
            });

        manager.registerReloader(new JsonDataLoader(new Gson(), "nothing") {
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

                    setupStatsThatAffectVanillaStatsList();

                    //Database.getAllRegistries()
                    //  .forEach(x -> x.onAllDatapacksLoaded());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }
}
