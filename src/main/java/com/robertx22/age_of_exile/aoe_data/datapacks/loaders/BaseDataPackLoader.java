package com.robertx22.age_of_exile.aoe_data.datapacks.loaders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.aoe_data.datapacks.generators.SlashDatapackGenerator;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryEntry;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.database.registry.SlashRegistryContainer;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.Cached;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public abstract class BaseDataPackLoader<T extends ISlashRegistryEntry> extends JsonDataLoader {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().create();

    public String id;
    Function<JsonObject, T> serializer;
    public SlashRegistryType registryType;

    public BaseDataPackLoader(SlashRegistryType registryType, String id, Function<JsonObject, T> serializer) {
        super(GSON, id);
        Objects.requireNonNull(registryType);
        this.id = id;
        this.serializer = serializer;
        this.registryType = registryType;
    }

    public abstract SlashDatapackGenerator getDataPackGenerator();

    @Override
    protected Map<Identifier, JsonElement> prepare(ResourceManager resourceManager, Profiler profiler) {

        if (MMORPG.RUN_DEV_TOOLS) {
            this.getDataPackGenerator()
                .run(); // first generate, then load. so no errors in dev enviroment
        }
        return super.prepare(resourceManager, profiler);

    }

    static String ENABLED = "enabled";

    @Override
    protected void apply(Map<Identifier, JsonElement> mapToLoad, ResourceManager manager, Profiler profilerIn) {

        Cached.reset();

        SlashRegistryContainer reg = SlashRegistry.getRegistry(registryType);

        reg.unregisterAllEntriesFromDatapacks();

        for (Map.Entry<Identifier, JsonElement> entry : mapToLoad.entrySet()) {
            try {
                JsonObject json = entry.getValue()
                    .getAsJsonObject();

                if (json.has(ENABLED)) {
                    if (!json.get(ENABLED)
                        .getAsBoolean()) {
                        continue;
                    }
                }
                T object = serializer.apply(json);
                object.registerToSlashRegistry();
            } catch (Exception exception) {
                LOGGER.error("Couldn't parse " + id + " {}", entry.getKey()
                    .toString(), exception);
            }

        }

        if (reg
            .isEmpty()) {
            throw new RuntimeException("Mine and Slash Registry of type " + registryType.id + " is EMPTY after datapack loading!");
        } else {
            System.out.println(registryType.name() + " Registry succeeded loading: " + reg.getSize() + " datapack entries.");
        }

    }

}