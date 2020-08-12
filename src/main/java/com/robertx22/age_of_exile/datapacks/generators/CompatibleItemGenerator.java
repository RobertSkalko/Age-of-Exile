package com.robertx22.age_of_exile.datapacks.generators;

import com.robertx22.age_of_exile.database.data.compatible_item.CompatibleItem;
import com.robertx22.age_of_exile.mmorpg.Ref;

import java.nio.file.Path;
import java.util.List;

public class CompatibleItemGenerator extends SlashDatapackGenerator<CompatibleItem> {

    public CompatibleItemGenerator(List<CompatibleItem> list, String category) {
        super(list, category);
    }

    @Override
    public Path resolve(Path path, CompatibleItem object) {
        return path.resolve(
            "data/" + Ref.MODID + "/" + category + "/" + object.datapackFolder() + object.getFileName() +
                ".json");
    }

}
