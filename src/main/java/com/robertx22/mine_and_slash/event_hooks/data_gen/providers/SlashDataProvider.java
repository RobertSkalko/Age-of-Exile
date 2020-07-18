package com.robertx22.mine_and_slash.event_hooks.data_gen.providers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.robertx22.mine_and_slash.database.data.IGUID;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.event_hooks.data_gen.ISerializable;
import com.robertx22.mine_and_slash.event_hooks.data_gen.ISerializedRegistryEntry;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class SlashDataProvider<T extends ISerializedRegistryEntry & IGUID & ISerializable<T>> implements DataProvider {

    private final Logger LOGGER = LogManager.getLogger();
    public static final Gson GSON = (new GsonBuilder()).setPrettyPrinting()
        .create();
    public DataGenerator generator;
    public String category;
    public List<T> list;

    public SlashDataProvider(DataGenerator gen, List<T> list, String category) {
        this.generator = gen;
        this.list = list;
        this.category = category;
    }

    @Override
    public void run(DataCache cache) throws IOException {
        generateAll(cache);
    }

    @Override
    public String getName() {
        return category;
    }

    public Path resolve(Path path, T object) {
        return path.resolve(
            "data/" + Ref.MODID + "/" + category + "/" + object.datapackFolder() + object.formattedGUID()
                .replaceAll(":", "_") +
                ".json");
    }

    protected void generateAll(DataCache cache) {

        Path path = this.generator.getOutput();

        for (T entry : list) {
            Path target = resolve(path, entry);
            try {
                DataProvider.writeToPath(GSON, cache, entry.toJson(), target);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
