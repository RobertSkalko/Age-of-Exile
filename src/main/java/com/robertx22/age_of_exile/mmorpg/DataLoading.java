package com.robertx22.age_of_exile.mmorpg;

import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.AttributeStat;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.error_checks.base.ErrorChecks;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.Cached;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import net.minecraft.resource.ReloadableResourceManager;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.ArrayList;

public class DataLoading {

    //public static final ExecutorService ASYNC_EXECUTOR_LOADER = Executors.newSingleThreadScheduledExecutor(r -> new Thread(r, "Age-Of-Exile-Thread"));

    private static void setupStatsThatAffectVanillaStatsList() {
        Cached.VANILLA_STAT_UIDS_TO_CLEAR_EVERY_STAT_CALC = new ArrayList<>();

        ExileDB.Stats()
            .getFilterWrapped(x -> x instanceof AttributeStat).list.forEach(x -> {
                AttributeStat attri = (AttributeStat) x;
                Cached.VANILLA_STAT_UIDS_TO_CLEAR_EVERY_STAT_CALC.add(ImmutablePair.of(attri.attribute, attri.uuid));
            });
    }

    public static void registerLoaders(ReloadableResourceManager manager) {

        ExileRegistryType.registerJsonListeners(manager, Ref.MODID);

        ExileEvents.AFTER_DATABASE_LOADED.register(new EventConsumer<ExileEvents.AfterDatabaseLoaded>() {
            @Override
            public void accept(ExileEvents.AfterDatabaseLoaded event) {
                Cached.reset();
                setupStatsThatAffectVanillaStatsList();
                ErrorChecks.getAll()
                    .forEach(x -> x.check());
            }
        });

    }
}
