package com.robertx22.age_of_exile.aoe_data;

import com.robertx22.age_of_exile.aoe_data.database.affixes.Prefixes;
import com.robertx22.age_of_exile.aoe_data.database.affixes.Suffixes;
import com.robertx22.age_of_exile.aoe_data.database.affixes.adders.dungeon.DungeonAffixAdder;
import com.robertx22.age_of_exile.aoe_data.database.affixes.adders.tools.ToolAffixes;
import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.BaseGearsRegister;
import com.robertx22.age_of_exile.aoe_data.database.base_stats.BaseStatsAdder;
import com.robertx22.age_of_exile.aoe_data.database.craft_req.CraftReqAdder;
import com.robertx22.age_of_exile.aoe_data.database.dim_configs.DimConfigs;
import com.robertx22.age_of_exile.aoe_data.database.dun_mob_list.DungeonMobListAdder;
import com.robertx22.age_of_exile.aoe_data.database.entity_configs.EntityConfigs;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.ExileEffects;
import com.robertx22.age_of_exile.aoe_data.database.favor.FavorAdder;
import com.robertx22.age_of_exile.aoe_data.database.gear_rarities.GearRaritiesAdder;
import com.robertx22.age_of_exile.aoe_data.database.gear_slots.GearSlots;
import com.robertx22.age_of_exile.aoe_data.database.gems.Gems;
import com.robertx22.age_of_exile.aoe_data.database.groups.GearRarityGroupAdder;
import com.robertx22.age_of_exile.aoe_data.database.mob_affixes.MobAffixes;
import com.robertx22.age_of_exile.aoe_data.database.mob_rarities.MobRaritiesAdder;
import com.robertx22.age_of_exile.aoe_data.database.perks.AllPerks;
import com.robertx22.age_of_exile.aoe_data.database.player_skills.PlayerSkillsAdder;
import com.robertx22.age_of_exile.aoe_data.database.races.Races;
import com.robertx22.age_of_exile.aoe_data.database.random_skill_gem_stats.RandomSkillGemStatsAdder;
import com.robertx22.age_of_exile.aoe_data.database.runes.Runes;
import com.robertx22.age_of_exile.aoe_data.database.runewords.Runewords;
import com.robertx22.age_of_exile.aoe_data.database.salvage_outputs.SalvageOutputsAdder;
import com.robertx22.age_of_exile.aoe_data.database.salvage_recipes.SalvageRecipes;
import com.robertx22.age_of_exile.aoe_data.database.scroll_buffs.ScrollBuffsAdder;
import com.robertx22.age_of_exile.aoe_data.database.sets.GearSetsAdder;
import com.robertx22.age_of_exile.aoe_data.database.skill_gem_rarity.SkillGemRarityAdder;
import com.robertx22.age_of_exile.aoe_data.database.skill_gems.SkillGemsAdder;
import com.robertx22.age_of_exile.aoe_data.database.spells.Spells;
import com.robertx22.age_of_exile.aoe_data.database.stats.AutoDatapackStats;
import com.robertx22.age_of_exile.aoe_data.database.stats.DatapackStatAdder;
import com.robertx22.age_of_exile.aoe_data.database.stats.SpellDependentDatapackStatAdder;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearReg;
import com.robertx22.age_of_exile.aoe_data.database.value_calc.ValueCalcAdder;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.registrators.Tiers;

public class GeneratedData {

    // as these only add serizables.
    // They shouldn't be needed at all to play the game.
    // If it errors without them, then that means i hardcoded something i shouldn't have
    public static void addAllObjectsToGenerate() {

        new ValueCalcAdder().registerAll();
        new DatapackStatAdder().registerAll();

        new SkillGemRarityAdder().registerAll();
        new GearRaritiesAdder().registerAll();
        new GearRarityGroupAdder().registerAll(); // after gear rarities
        new MobRaritiesAdder().registerAll();

        new GearSlots().registerAll();
        new BaseGearsRegister().registerAll();
        new UniqueGearReg().registerAll();

        new ExileEffects().registerAll();

        new DungeonMobListAdder().registerAll();

        new Tiers().registerAll();

        new Spells().registerAll();

        new SpellDependentDatapackStatAdder().registerAll();

        new DungeonAffixAdder().registerAll();
        new Prefixes().registerAll();
        new Suffixes().registerAll();
        new ToolAffixes().registerAll();

        new MobAffixes().registerAll();
        new DimConfigs().registerAll();
        new EntityConfigs().registerAll();

        new Gems().registerAll();
        new Runes().registerAll();
        new Runewords().registerAll();

        new AllPerks().registerAll();

        new AutoDatapackStats().registerAll();

        new FavorAdder().registerAll();
        new SalvageOutputsAdder().registerAll();
        new PlayerSkillsAdder().registerAll();
        new BaseStatsAdder().registerAll();
        new SalvageRecipes().registerAll();

        new SkillGemsAdder().registerAll();
        new RandomSkillGemStatsAdder().registerAll();
        new Races().registerAll();

        new GameBalanceConfig().addToSerializables();
        new ScrollBuffsAdder().registerAll();
        new CraftReqAdder().registerAll();
        new GearSetsAdder().registerAll();

    }
}
