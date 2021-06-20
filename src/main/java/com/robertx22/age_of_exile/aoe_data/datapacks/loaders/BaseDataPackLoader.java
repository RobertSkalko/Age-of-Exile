package com.robertx22.age_of_exile.aoe_data.datapacks.loaders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.aoe_data.datapacks.generators.ExileDatapackGenerator;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.database.registry.ExileRegistry;
import com.robertx22.age_of_exile.database.registry.ExileRegistryContainer;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.uncommon.testing.Watch;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public abstract class BaseDataPackLoader<T extends ExileRegistry> extends JsonDataLoader {
    private static final Gson GSON = new GsonBuilder().create();

    public String id;
    Function<JsonObject, T> serializer;
    public ExileRegistryTypes registryType;

    public BaseDataPackLoader(ExileRegistryTypes registryType, String id, Function<JsonObject, T> serializer) {
        super(GSON, id);
        Objects.requireNonNull(registryType);
        this.id = id;
        this.serializer = serializer;
        this.registryType = registryType;
    }

    public abstract ExileDatapackGenerator getDataPackGenerator();

    @Override
    protected Map<Identifier, JsonElement> prepare(ResourceManager resourceManager, Profiler profiler) {

        if (MMORPG.RUN_DEV_TOOLS) {

            /* // use this to test if shit breaks
            if (registryType == SlashRegistryType.UNIQUE_GEAR) {
                return super.prepare(resourceManager, profiler);
            }             */
            this.getDataPackGenerator()
                .run(); // first generate, then load. so no errors in dev enviroment
        }

        return super.prepare(resourceManager, profiler);
    }

    static String ENABLED = "enabled";

    @Override
    protected void apply(Map<Identifier, JsonElement> mapToLoad, ResourceManager manager, Profiler profilerIn) {

        try {
            ExileRegistryContainer reg = Database.getRegistry(registryType);

            Watch normal = new Watch();
            normal.min = 50000;
            reg.unregisterAllEntriesFromDatapacks();

            mapToLoad.forEach((key, value) -> {
                try {
                    JsonObject json = value
                        .getAsJsonObject();

                    if (!json.has(ENABLED) || json.get(ENABLED)
                        .getAsBoolean()) {
                        T object = serializer.apply(json);
                        object.registerToExileRegistry();
                    }
                } catch (Exception exception) {
                    System.out.println(id + " is a broken datapack entry.");
                    exception.printStackTrace();
                }
            });

            normal.print("Loading " + registryType.name() + " jsons ");

            if (reg
                .isEmpty()) {
                throw new RuntimeException("Exile Registry of type " + registryType.id + " is EMPTY after datapack loading!");
            } else {
                // System.out.println(registryType.name() + " Registry succeeded loading: " + reg.getSize() + " datapack entries.");
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

}