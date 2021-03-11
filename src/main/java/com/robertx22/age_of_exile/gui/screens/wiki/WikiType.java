package com.robertx22.age_of_exile.gui.screens.wiki;

import com.robertx22.age_of_exile.database.data.DimensionConfig;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.gui.screens.wiki.entries.DimensionsEntry;
import com.robertx22.age_of_exile.gui.screens.wiki.entries.UniqueGearEntry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import net.minecraft.util.Identifier;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public enum WikiType {

    UNIQUE_GEARS("unique_gear") {
        @Override
        public List<WikiEntry> getAllEntries() {
            return Database.UniqueGears()
                .getList()
                .stream()
                .map(x -> new UniqueGearEntry(x))
                .collect(Collectors.toList());
        }
    },
    DIMENSIONS("dimension") {
        @Override
        public List<WikiEntry> getAllEntries() {

            List<DimensionConfig> configs = Database.DimensionConfigs()
                .getList();
            configs.add((DimensionConfig) Database.getRegistry(SlashRegistryType.DIMENSION_CONFIGS)
                .getDefault());

            configs.sort(Comparator.comparingInt(x -> x.min_lvl));

            return configs.stream()
                .map(x -> new DimensionsEntry(x))
                .collect(Collectors.toList());
        }
    };

    private String icon;

    WikiType(String icon) {
        this.icon = icon;
    }

    public abstract List<WikiEntry> getAllEntries();

    public Identifier getIconLoc() {
        return new Identifier(Ref.MODID, "textures/gui/wiki/buttons/" + icon + ".png");
    }
}
