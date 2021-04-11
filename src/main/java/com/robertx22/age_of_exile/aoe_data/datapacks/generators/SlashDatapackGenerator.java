package com.robertx22.age_of_exile.aoe_data.datapacks.generators;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializable;
import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.mmorpg.Ref;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataProvider;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SlashDatapackGenerator<T extends IGUID & ISerializable<T>> extends BaseDatapackGenerator<T> {

    public SlashDatapackGenerator(List<T> list, String category) {
        super(list, category);
    }

    @Override
    public void run() {
        generateAll(cache);
    }

    public Path resolve(Path path, T object) {
        return path.resolve(
            "data/" + Ref.MODID + "/" + category + "/" + object.datapackFolder() + object.getFileName()
                .replaceAll(":", "_") +
                ".json");
    }

    protected void generateAll(DataCache cache) {

        Path path = gameDirPath();

        for (T entry : list) {

            if (!entry.shouldGenerateJson()) {
                continue;
            }

            Path target = movePath(resolve(path, entry));

            target = Paths.get(target.toString()
                .replace("\\.\\", "\\"));

            try {
                DataProvider.writeToPath(GSON, cache, entry.toJson(), target);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
