package com.robertx22.age_of_exile.gui.screens.wiki;

import com.robertx22.age_of_exile.database.data.DimensionConfig;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.gui.screens.wiki.entries.DimensionsEntry;
import com.robertx22.age_of_exile.gui.screens.wiki.entries.MiningBlockExpEntry;
import com.robertx22.age_of_exile.gui.screens.wiki.entries.UniqueGearEntry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
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
    MINING_BLOCK_EXP("mining_exp") {
        @Override
        public List<WikiEntry> getAllEntries() {
            List<WikiEntry> list = new ArrayList<>();

            Database.PlayerSkills()
                .get(PlayerSkillEnum.MINING.id).block_break_exp.forEach(x -> {
                list.add(new MiningBlockExpEntry(x.getBlock(), (int) x.exp));
            });

            return list;

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
