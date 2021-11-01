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
import com.robertx22.age_of_exile.database.data.ingredient.SlashIngredient;
import com.robertx22.age_of_exile.database.data.mob_affixes.MobAffix;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.data.runes.Rune;
import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.data.set.GearSet;
import com.robertx22.age_of_exile.database.data.spell_school.SpellSchool;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.BaseDatapackStat;
import com.robertx22.age_of_exile.database.data.synergy.Synergy;
import com.robertx22.age_of_exile.database.data.talent_tree.TalentTree;
import com.robertx22.age_of_exile.database.data.tiers.base.Difficulty;
import com.robertx22.age_of_exile.database.data.transc_affix.TranscendentAffix;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.database.empty_entries.EmptyAffix;
import com.robertx22.age_of_exile.dimension.database.dungeon_mob_lists.DungeonMobList;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.action.StatEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition.StatCondition;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.SyncTime;
import com.robertx22.library_of_exile.registry.loaders.BaseDataPackLoader;
import net.minecraft.util.text.TextFormatting;

public class ExileRegistryTypes {

    public static ExileRegistryType GEAR_RARITY = ExileRegistryType.register(SlashRef.MODID, "gear_rarity", 0, GearRarity.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType MOB_RARITY = ExileRegistryType.register(SlashRef.MODID, "mob_rarity", 0, MobRarity.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType STAT = ExileRegistryType.register(SlashRef.MODID, "stat", 1, BaseDatapackStat.MAIN_SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType GEAR_SLOT = ExileRegistryType.register(SlashRef.MODID, "gear_slot", 3, GearSlot.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType EXILE_EFFECT = ExileRegistryType.register(SlashRef.MODID, "exile_effect", 3, ExileEffect.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType GEAR_TYPE = ExileRegistryType.register(SlashRef.MODID, "base_gear_types", 4, BaseGearType.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType TIER = ExileRegistryType.register(SlashRef.MODID, "tier", 5, Difficulty.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType GEM = ExileRegistryType.register(SlashRef.MODID, "gems", 6, Gem.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType RUNE = ExileRegistryType.register(SlashRef.MODID, "runes", 7, Rune.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType MOB_AFFIX = ExileRegistryType.register(SlashRef.MODID, "mob_affix", 8, new MobAffix("empty", "empty", TextFormatting.AQUA), SyncTime.ON_LOGIN);
    public static ExileRegistryType AFFIX = ExileRegistryType.register(SlashRef.MODID, "affixes", 10, EmptyAffix.getInstance(), SyncTime.ON_LOGIN);
    public static ExileRegistryType UNIQUE_GEAR = ExileRegistryType.register(SlashRef.MODID, "unique_gears", 11, UniqueGear.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType RUNEWORDS = ExileRegistryType.register(SlashRef.MODID, "runeword", 12, RuneWord.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType CURRENCY_ITEMS = ExileRegistryType.register(new ExileRegistryType(SlashRef.MODID, "currency_item", 12, null, SyncTime.NEVER) {
        @Override
        public BaseDataPackLoader getLoader() {
            return null;
        }
    });
    public static ExileRegistryType DIMENSION_CONFIGS = ExileRegistryType.register(SlashRef.MODID, "dimension_config", 13, DimensionConfig.EMPTY, SyncTime.ON_LOGIN);
    public static ExileRegistryType ENTITY_CONFIGS = ExileRegistryType.register(SlashRef.MODID, "entity_config", 14, Serializers.ENTITY_CONFIG_SER, SyncTime.NEVER);
    public static ExileRegistryType SPELL = ExileRegistryType.register(SlashRef.MODID, "spells", 17, Spell.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType PERK = ExileRegistryType.register(SlashRef.MODID, "perk", 18, Perk.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType TALENT_TREE = ExileRegistryType.register(SlashRef.MODID, "talent_tree", 19, TalentTree.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType GEAR_RARITY_GROUP = ExileRegistryType.register(SlashRef.MODID, "gear_rarity_group", 20, GearRarityGroup.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType PLAYER_SKILLS = ExileRegistryType.register(SlashRef.MODID, "player_skills", 23, PlayerSkill.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType FAVOR_RANK = ExileRegistryType.register(SlashRef.MODID, "favor_rank", 21, FavorRank.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType BASE_STATS = ExileRegistryType.register(SlashRef.MODID, "base_stats", 22, BaseStatsConfig.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType CRAFTING_REQ = ExileRegistryType.register(SlashRef.MODID, "crafting_req", 30, CraftingReq.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType VALUE_CALC = ExileRegistryType.register(SlashRef.MODID, "value_calc", 40, ValueCalculation.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType GEAR_SET = ExileRegistryType.register(SlashRef.MODID, "set", 31, GearSet.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType DUNGEON_MOB_LIST = ExileRegistryType.register(SlashRef.MODID, "dungeon_mob_list", 32, DungeonMobList.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType STAT_EFFECT = ExileRegistryType.register(SlashRef.MODID, "stat_effect", 32, StatEffect.SERIALIZER, SyncTime.NEVER);
    public static ExileRegistryType STAT_CONDITION = ExileRegistryType.register(SlashRef.MODID, "stat_condition", 32, StatCondition.SERIALIZER, SyncTime.NEVER);
    public static ExileRegistryType GAME_BALANCE = ExileRegistryType.register(SlashRef.MODID, "game_balance", 26, GameBalanceConfig.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType SPELL_SCHOOL = ExileRegistryType.register(SlashRef.MODID, "spell_school", 26, SpellSchool.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType SYNERGY = ExileRegistryType.register(SlashRef.MODID, "synergy", 26, Synergy.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType INGREDIENT = ExileRegistryType.register(SlashRef.MODID, "ingredient", 26, SlashIngredient.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType TRANSC_AFFIXES = ExileRegistryType.register(SlashRef.MODID, "transcendent_affix", 26, TranscendentAffix.SERIALIZER, SyncTime.ON_LOGIN);

}
