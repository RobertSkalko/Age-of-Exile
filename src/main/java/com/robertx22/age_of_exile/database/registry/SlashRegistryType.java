package com.robertx22.age_of_exile.database.registry;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.aoe_data.database.compat_items.CompatibleItems;
import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializable;
import com.robertx22.age_of_exile.aoe_data.datapacks.generators.SlashDatapackGenerator;
import com.robertx22.age_of_exile.aoe_data.datapacks.loaders.BaseDataPackLoader;
import com.robertx22.age_of_exile.database.data.DimensionConfig;
import com.robertx22.age_of_exile.database.data.EntityConfig;
import com.robertx22.age_of_exile.database.data.base_stats.BaseStatsConfig;
import com.robertx22.age_of_exile.database.data.compatible_item.CompatibleItem;
import com.robertx22.age_of_exile.database.data.crafting_req.CraftingReq;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.favor.FavorRank;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.gems.Gem;
import com.robertx22.age_of_exile.database.data.groups.GearRarityGroup;
import com.robertx22.age_of_exile.database.data.mob_affixes.MobAffix;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.data.races.PlayerRace;
import com.robertx22.age_of_exile.database.data.random_skill_gem_stats.RandomSkillGemStats;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.data.rarities.SkillGemRarity;
import com.robertx22.age_of_exile.database.data.runes.Rune;
import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.data.salvage_outputs.SalvageOutput;
import com.robertx22.age_of_exile.database.data.salvage_recipes.SalvageRecipe;
import com.robertx22.age_of_exile.database.data.scroll_buff.ScrollBuff;
import com.robertx22.age_of_exile.database.data.set.GearSet;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGem;
import com.robertx22.age_of_exile.database.data.spell_schools.SpellSchool;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.DatapackStat;
import com.robertx22.age_of_exile.database.data.tiers.base.Tier;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.database.registry.empty_entries.EmptyAffix;
import com.robertx22.age_of_exile.dimension.database.dungeon_mob_lists.DungeonMobList;
import net.minecraft.util.Formatting;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public enum SlashRegistryType {

    GEAR_RARITY("gear_rarity", 0, GearRarity.SERIALIZER, SyncTime.ON_LOGIN),
    MOB_RARITY("mob_rarity", 0, MobRarity.SERIALIZER, SyncTime.ON_LOGIN),
    STAT("stat", 1, DatapackStat.MAIN_SERIALIZER, SyncTime.ON_LOGIN),
    GEAR_SLOT("gear_slot", 3, GearSlot.SERIALIZER, SyncTime.ON_LOGIN),
    EXILE_EFFECT("exile_effect", 3, ExileEffect.SERIALIZER, SyncTime.ON_LOGIN),
    GEAR_TYPE("base_gear_types", 4, BaseGearType.SERIALIZER, SyncTime.ON_LOGIN),
    TIER("tier", 5, Tier.SERIALIZER, SyncTime.ON_LOGIN),
    GEM("gems", 6, Gem.SERIALIZER, SyncTime.ON_LOGIN),
    RUNE("runes", 7, Rune.SERIALIZER, SyncTime.ON_LOGIN),
    MOB_AFFIX("mob_affix", 8, new MobAffix("empty", "empty", Formatting.AQUA), SyncTime.ON_LOGIN),
    RUNEWORD("runewords", 9, RuneWord.SERIALIZER, SyncTime.ON_LOGIN),
    AFFIX("affixes", 10, EmptyAffix.getInstance(), SyncTime.ON_LOGIN),
    UNIQUE_GEAR("unique_gears", 11, UniqueGear.SERIALIZER, SyncTime.ON_LOGIN),
    CURRENCY_ITEMS("currency_item", 12, null, SyncTime.NEVER) {
        @Override
        public BaseDataPackLoader getLoader() {
            return null;
        }
    },
    DIMENSION_CONFIGS("dimension_config", 13, DimensionConfig.EMPTY, SyncTime.ON_LOGIN),
    ENTITY_CONFIGS("entity_config", 14, EntityConfig.EMPTY, SyncTime.NEVER),
    COMPATIBLE_ITEM("compatible_items", 15, CompatibleItem.EMPTY, SyncTime.ON_LOGIN) {
        public List getAllForSerialization() {
            return CompatibleItems.getAllForSerialization();
        }
    },
    SPELL("spells", 17, Spell.SERIALIZER, SyncTime.ON_LOGIN),
    PERK("perk", 18, Perk.SERIALIZER, SyncTime.ON_LOGIN),
    SPELL_SCHOOL("spell_school", 19, SpellSchool.SERIALIZER, SyncTime.ON_LOGIN),
    GEAR_RARITY_GROUP("gear_rarity_group", 20, GearRarityGroup.SERIALIZER, SyncTime.ON_LOGIN),
    SALVAGE_OUTPUT("salvage_output", 22, SalvageOutput.SERIALIZER, SyncTime.ON_LOGIN),
    PLAYER_SKILLS("player_skills", 23, PlayerSkill.SERIALIZER, SyncTime.ON_LOGIN),
    FAVOR_RANK("favor_rank", 21, FavorRank.SERIALIZER, SyncTime.ON_LOGIN),
    BASE_STATS("base_stats", 22, BaseStatsConfig.SERIALIZER, SyncTime.ON_LOGIN),
    SKILL_GEM("spell_gem", 23, SkillGem.SERIALIZER, SyncTime.ON_LOGIN),
    SKILL_GEM_RARITY("skill_gem_rarity", 24, SkillGemRarity.SERIALIZER, SyncTime.ON_LOGIN),
    RANDOM_SKILL_GEM_STATS("random_skill_gem_stats", 25, RandomSkillGemStats.SERIALIZER, SyncTime.ON_LOGIN),
    SALVAGE_RECIPE("salvage_recipe", 27, SalvageRecipe.SERIALIZER, SyncTime.ON_LOGIN),
    RACES("races", 28, PlayerRace.SERIALIZER, SyncTime.ON_LOGIN),
    CRAFTING_REQ("crafting_req", 30, CraftingReq.SERIALIZER, SyncTime.ON_LOGIN),
    SCROLL_BUFFS("scroll_buffs", 29, ScrollBuff.SERIALIZER, SyncTime.ON_LOGIN),
    VALUE_CALC("value_calc", 40, ValueCalculation.SERIALIZER, SyncTime.ON_LOGIN),
    GEAR_SET("set", 31, GearSet.SERIALIZER, SyncTime.ON_LOGIN),
    DUNGEON_MOB_LIST("dungeon_mob_list", 32, DungeonMobList.SERIALIZER, SyncTime.ON_LOGIN),
    GAME_BALANCE("game_balance", 26, GameBalanceConfig.SERIALIZER, SyncTime.ON_LOGIN);

    public String id;
    ISerializable ser;
    int order;
    public SyncTime syncTime;

    SlashRegistryType(String id, int order, ISerializable ser, SyncTime synctime) {
        this.id = id;
        this.order = order;
        this.ser = ser;
        this.syncTime = synctime;
    }

    public static List<SlashRegistryType> getInRegisterOrder(SyncTime sync) {
        List<SlashRegistryType> list = Arrays.stream(SlashRegistryType.values())
            .filter(x -> x.syncTime == sync)
            .collect(Collectors.toList());
        list.sort(Comparator.comparingInt(x -> x.order));
        return list;

    }

    public static List<SlashRegistryType> getAllInRegisterOrder() {
        List<SlashRegistryType> list = Arrays.stream(SlashRegistryType.values())
            .collect(Collectors.toList());
        list.sort(Comparator.comparingInt(x -> x.order));
        return list;

    }

    public static void init() {

    }

    public BaseDataPackLoader getLoader() {
        return new BaseDataPackLoader(this, this.id, x -> this.ser.fromJson((JsonObject) x)) {
            @Override
            public SlashDatapackGenerator getDataPackGenerator() {
                return new SlashDatapackGenerator<>(getAllForSerialization(), this.id);
            }
        };
    }

    public List getAllForSerialization() {
        return Database.getRegistry(this)
            .getSerializable();
    }

    public final ISerializable getSerializer() {
        return ser;
    }

    public static SlashRegistryType getFromString(String str) {
        for (SlashRegistryType type : values()) {
            if (str.equals(type.id)) {
                return type;
            }
        }
        return null;
    }

}
