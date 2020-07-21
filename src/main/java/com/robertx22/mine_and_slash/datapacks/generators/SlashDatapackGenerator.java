package com.robertx22.mine_and_slash.datapacks.generators;

import com.robertx22.mine_and_slash.database.data.IGUID;
import com.robertx22.mine_and_slash.datapacks.bases.ISerializable;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataProvider;

import java.io.IOException;
import java.nio.file.Path;
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
            "data/" + Ref.MODID + "/" + category + "/" + object.datapackFolder() + object.formattedGUID()
                .replaceAll(":", "_") +
                ".json");
    }

    protected void generateAll(DataCache cache) {

        Path path = getBasePath();

        for (T entry : list) {

            Path target = movePath(resolve(path, entry));

            try {
                DataProvider.writeToPath(GSON, cache, entry.toJson(), target);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
