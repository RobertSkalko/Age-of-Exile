package com.robertx22.mine_and_slash.datapacks.generators;

import com.robertx22.mine_and_slash.database.data.compatible_item.CompatibleItem;
import com.robertx22.mine_and_slash.mmorpg.Ref;

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
