package com.robertx22.age_of_exile.database.registry;

import com.robertx22.age_of_exile.database.Serializers;
import com.robertx22.age_of_exile.database.data.DimensionConfig;
import com.robertx22.age_of_exile.database.data.base_stats.BaseStatsConfig;
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
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.BaseDatapackStat;
import com.robertx22.age_of_exile.database.data.tiers.base.Difficulty;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.database.empty_entries.EmptyAffix;
import com.robertx22.age_of_exile.dimension.database.dungeon_mob_lists.DungeonMobList;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.action.StatEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition.StatCondition;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.SyncTime;
import com.robertx22.library_of_exile.registry.loaders.BaseDataPackLoader;
import net.minecraft.util.Formatting;

public class ExileRegistryTypes {

    public static ExileRegistryType GEAR_RARITY = ExileRegistryType.register(Ref.MODID, "gear_rarity", 0, GearRarity.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType MOB_RARITY = ExileRegistryType.register(Ref.MODID, "mob_rarity", 0, MobRarity.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType STAT = ExileRegistryType.register(Ref.MODID, "stat", 1, BaseDatapackStat.MAIN_SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType GEAR_SLOT = ExileRegistryType.register(Ref.MODID, "gear_slot", 3, GearSlot.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType EXILE_EFFECT = ExileRegistryType.register(Ref.MODID, "exile_effect", 3, ExileEffect.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType GEAR_TYPE = ExileRegistryType.register(Ref.MODID, "base_gear_types", 4, BaseGearType.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType TIER = ExileRegistryType.register(Ref.MODID, "tier", 5, Difficulty.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType GEM = ExileRegistryType.register(Ref.MODID, "gems", 6, Gem.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType RUNE = ExileRegistryType.register(Ref.MODID, "runes", 7, Rune.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType MOB_AFFIX = ExileRegistryType.register(Ref.MODID, "mob_affix", 8, new MobAffix("empty", "empty", Formatting.AQUA), SyncTime.ON_LOGIN);
    public static ExileRegistryType RUNEWORD = ExileRegistryType.register(Ref.MODID, "runewords", 9, RuneWord.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType AFFIX = ExileRegistryType.register(Ref.MODID, "affixes", 10, EmptyAffix.getInstance(), SyncTime.ON_LOGIN);
    public static ExileRegistryType UNIQUE_GEAR = ExileRegistryType.register(Ref.MODID, "unique_gears", 11, UniqueGear.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType CURRENCY_ITEMS = ExileRegistryType.register(new ExileRegistryType(Ref.MODID, "currency_item", 12, null, SyncTime.NEVER) {
        @Override
        public BaseDataPackLoader getLoader() {
            return null;
        }
    });
    public static ExileRegistryType DIMENSION_CONFIGS = ExileRegistryType.register(Ref.MODID, "dimension_config", 13, DimensionConfig.EMPTY, SyncTime.ON_LOGIN);
    public static ExileRegistryType ENTITY_CONFIGS = ExileRegistryType.register(Ref.MODID, "entity_config", 14, Serializers.ENTITY_CONFIG_SER, SyncTime.NEVER);
    public static ExileRegistryType SPELL = ExileRegistryType.register(Ref.MODID, "spells", 17, Spell.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType PERK = ExileRegistryType.register(Ref.MODID, "perk", 18, Perk.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType SPELL_SCHOOL = ExileRegistryType.register(Ref.MODID, "spell_school", 19, SpellSchool.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType GEAR_RARITY_GROUP = ExileRegistryType.register(Ref.MODID, "gear_rarity_group", 20, GearRarityGroup.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType SALVAGE_OUTPUT = ExileRegistryType.register(Ref.MODID, "salvage_output", 22, SalvageOutput.SERIALIZER, SyncTime.NEVER);
    public static ExileRegistryType PLAYER_SKILLS = ExileRegistryType.register(Ref.MODID, "player_skills", 23, PlayerSkill.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType FAVOR_RANK = ExileRegistryType.register(Ref.MODID, "favor_rank", 21, FavorRank.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType BASE_STATS = ExileRegistryType.register(Ref.MODID, "base_stats", 22, BaseStatsConfig.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType SKILL_GEM = ExileRegistryType.register(Ref.MODID, "spell_gem", 23, SkillGem.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType SKILL_GEM_RARITY = ExileRegistryType.register(Ref.MODID, "skill_gem_rarity", 24, SkillGemRarity.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType RANDOM_SKILL_GEM_STATS = ExileRegistryType.register(Ref.MODID, "random_skill_gem_stats", 25, RandomSkillGemStats.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType SALVAGE_RECIPE = ExileRegistryType.register(Ref.MODID, "salvage_recipe", 27, SalvageRecipe.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType RACES = ExileRegistryType.register(Ref.MODID, "races", 28, PlayerRace.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType CRAFTING_REQ = ExileRegistryType.register(Ref.MODID, "crafting_req", 30, CraftingReq.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType SCROLL_BUFFS = ExileRegistryType.register(Ref.MODID, "scroll_buffs", 29, ScrollBuff.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType VALUE_CALC = ExileRegistryType.register(Ref.MODID, "value_calc", 40, ValueCalculation.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType GEAR_SET = ExileRegistryType.register(Ref.MODID, "set", 31, GearSet.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType DUNGEON_MOB_LIST = ExileRegistryType.register(Ref.MODID, "dungeon_mob_list", 32, DungeonMobList.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType STAT_EFFECT = ExileRegistryType.register(Ref.MODID, "stat_effect", 32, StatEffect.SERIALIZER, SyncTime.NEVER);
    public static ExileRegistryType STAT_CONDITION = ExileRegistryType.register(Ref.MODID, "stat_condition", 32, StatCondition.SERIALIZER, SyncTime.NEVER);
    public static ExileRegistryType GAME_BALANCE = ExileRegistryType.register(Ref.MODID, "game_balance", 26, GameBalanceConfig.SERIALIZER, SyncTime.ON_LOGIN);
}
