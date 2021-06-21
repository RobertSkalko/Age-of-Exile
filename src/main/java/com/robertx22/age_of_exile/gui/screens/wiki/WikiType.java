package com.robertx22.age_of_exile.gui.screens.wiki;

import com.robertx22.age_of_exile.database.data.DimensionConfig;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.gui.screens.wiki.entries.*;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.library_of_exile.registry.Database;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public enum WikiType {

    UNIQUE_GEARS("unique_gear") {
        @Override
        public List<WikiEntry> getAllEntries() {
            return ExileDB.UniqueGears()
                .getList()
                .stream()
                .map(x -> new UniqueGearEntry(x))
                .collect(Collectors.toList());
        }
    },

    SPELLS("spell") {
        @Override
        public List<WikiEntry> getAllEntries() {
            return ExileDB.Spells()
                .getList()
                .stream()
                .filter(x -> x.Weight() > 0)
                .map(x -> new SpellEntry(x))
                .collect(Collectors.toList());
        }
    },
    EFFECTS("effect") {
        @Override
        public List<WikiEntry> getAllEntries() {
            return ExileDB.ExileEffects()
                .getList()
                .stream()
                .map(x -> new EffectEntry(x))
                .collect(Collectors.toList());
        }
    },
    SETS("set") {
        @Override
        public List<WikiEntry> getAllEntries() {
            return ExileDB.Sets()
                .getList()
                .stream()
                .map(x -> new SetEntry(x))
                .collect(Collectors.toList());
        }
    },

    COOKING_EXP("cooking") {
        @Override
        public List<WikiEntry> getAllEntries() {
            return craftExp(PlayerSkillEnum.COOKING);
        }

        @Override
        public boolean showsInWiki() {
            return false; // only by linking from professions page
        }
    },
    ALCHEMY_EXP("alchemy") {
        @Override
        public List<WikiEntry> getAllEntries() {
            return craftExp(PlayerSkillEnum.ALCHEMY);
        }

        @Override
        public boolean showsInWiki() {
            return false; // only by linking from professions page
        }
    },
    SCRIBE_EXP("scribe") {
        @Override
        public List<WikiEntry> getAllEntries() {
            return craftExp(PlayerSkillEnum.INSCRIBING);
        }

        @Override
        public boolean showsInWiki() {
            return false; // only by linking from professions page
        }
    },
    BLACKSMITH_EXP("smith") {
        @Override
        public List<WikiEntry> getAllEntries() {
            return craftExp(PlayerSkillEnum.BLACKSMITHING);
        }

        @Override
        public boolean showsInWiki() {
            return false; // only by linking from professions page
        }
    },
    RUNEWORDS("runeword") {
        @Override
        public List<WikiEntry> getAllEntries() {
            return ExileDB.Runewords()
                .getList()
                .stream()
                .map(x -> new RuneWordEntry(x))
                .collect(Collectors.toList());
        }
    },

    MINING_BLOCK_EXP("mining_exp") {
        @Override
        public boolean showsInWiki() {
            return false; // only by linking from professions page
        }

        @Override
        public List<WikiEntry> getAllEntries() {
            List<WikiEntry> list = new ArrayList<>();

            ExileDB.PlayerSkills()
                .get(PlayerSkillEnum.MINING.id).block_break_exp.forEach(x -> {
                    list.add(new MiningBlockExpEntry(x.getBlock(), (int) x.exp));
                });
            ExileDB.PlayerSkills()
                .get(PlayerSkillEnum.MINING.id).item_smelt_exp.forEach(x -> {
                    list.add(new MiningSmeltEntry(x.getItem(), x.exp));
                });
            return list;
        }
    }, FARMING_EXP("farming_exp") {
        @Override
        public boolean showsInWiki() {
            return false; // only by linking from professions page
        }

        @Override
        public List<WikiEntry> getAllEntries() {
            List<WikiEntry> list = new ArrayList<>();
            ExileDB.PlayerSkills()
                .get(PlayerSkillEnum.FARMING.id).block_break_exp.forEach(x -> {
                    list.add(new MiningBlockExpEntry(x.getBlock(), (int) x.exp));
                });
            return list;
        }
    },
    DIMENSIONS("dimension") {
        @Override
        public List<WikiEntry> getAllEntries() {

            List<DimensionConfig> configs = ExileDB.DimensionConfigs()
                .getList();
            configs.add((DimensionConfig) Database.getRegistry(ExileRegistryTypes.DIMENSION_CONFIGS)
                .getDefault());

            configs.sort(Comparator.comparingInt(x -> x.min_lvl));

            return configs.stream()
                .map(x -> new DimensionsEntry(x))
                .collect(Collectors.toList());
        }
    };

    List<WikiEntry> craftExp(PlayerSkillEnum skill) {
        List<WikiEntry> list = new ArrayList<>();
        ExileDB.PlayerSkills()
            .get(skill.id).item_craft_exp.forEach(x -> {
                list.add(new CraftingExpEntry(x.getItem(), x.exp));
            });
        return list;
    }

    private String icon;

    WikiType(String icon) {
        this.icon = icon;
    }

    public boolean showsInWiki() {
        return true;
    }

    public abstract List<WikiEntry> getAllEntries();

    public Identifier getIconLoc() {
        return new Identifier(Ref.MODID, "textures/gui/wiki/buttons/" + icon + ".png");
    }
}
