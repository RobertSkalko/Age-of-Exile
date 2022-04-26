package com.robertx22.age_of_exile.aoe_data;

import com.robertx22.age_of_exile.aoe_data.database.affixes.Prefixes;
import com.robertx22.age_of_exile.aoe_data.database.affixes.Suffixes;
import com.robertx22.age_of_exile.aoe_data.database.affixes.adders.dungeon.DungeonAffixAdder;
import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.BaseGearsAdder;
import com.robertx22.age_of_exile.aoe_data.database.base_stats.BaseStatsAdder;
import com.robertx22.age_of_exile.aoe_data.database.craft_req.CraftReqAdder;
import com.robertx22.age_of_exile.aoe_data.database.dim_configs.DimConfigs;
import com.robertx22.age_of_exile.aoe_data.database.dun_mob_list.DungeonMobListAdder;
import com.robertx22.age_of_exile.aoe_data.database.entity_configs.EntityConfigs;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.ExileEffects;
import com.robertx22.age_of_exile.aoe_data.database.favor.FavorAdder;
import com.robertx22.age_of_exile.aoe_data.database.gear_rarities.GearRaritiesAdder;
import com.robertx22.age_of_exile.aoe_data.database.gear_slots.GearSlots;
import com.robertx22.age_of_exile.aoe_data.database.groups.GearRarityGroupAdder;
import com.robertx22.age_of_exile.aoe_data.database.mob_affixes.MobAffixes;
import com.robertx22.age_of_exile.aoe_data.database.mob_rarities.MobRaritiesAdder;
import com.robertx22.age_of_exile.aoe_data.database.perks.AllPerks;
import com.robertx22.age_of_exile.aoe_data.database.player_skills.PlayerSkillsAdder;
import com.robertx22.age_of_exile.aoe_data.database.reforge.ReforgesAdder;
import com.robertx22.age_of_exile.aoe_data.database.runes.Runes;
import com.robertx22.age_of_exile.aoe_data.database.sets.GearSetsAdder;
import com.robertx22.age_of_exile.aoe_data.database.spell_schools.SpellSchoolsAdder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.aoe_data.database.spells.Spells;
import com.robertx22.age_of_exile.aoe_data.database.stat_conditions.StatConditions;
import com.robertx22.age_of_exile.aoe_data.database.stat_effects.StatEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.AutoDatapackStats;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.aoe_data.database.tiers.DifficultyAdders;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearReg;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;

public class GeneratedData {

    // as these only add serizables.
    // They shouldn't be needed at all to play the game.
    // If it errors without them, then that means i hardcoded something i shouldn't have
    public static void addAllObjectsToGenerate() {

        new StatEffects().registerAll();
        new StatConditions().registerAll();
        new Stats().registerAll();
        new DatapackStats().registerAll();

        new GearRaritiesAdder().registerAll();
        new GearRarityGroupAdder().registerAll(); // after gear rarities
        new MobRaritiesAdder().registerAll();

        SpellCalcs.init();
        new Spells().registerAll();
        new SpellSchoolsAdder().registerAll();

        new GearSlots().registerAll();
        new BaseGearsAdder().registerAll();
        new UniqueGearReg().registerAll();

        new ExileEffects().registerAll();
        new DungeonMobListAdder().registerAll();
        new DifficultyAdders().registerAll();

        new DungeonAffixAdder().registerAll();
        new Prefixes().registerAll();
        new Suffixes().registerAll();

        new MobAffixes().registerAll();
        new DimConfigs().registerAll();
        new EntityConfigs().registerAll();

        new Runes().registerAll();

        new AllPerks().registerAll();

        new AutoDatapackStats().registerAll();

        new FavorAdder().registerAll();
        new PlayerSkillsAdder().registerAll();
        new BaseStatsAdder().registerAll();

        GameBalanceConfig c = new GameBalanceConfig();
        c.addToSerializables();

        new CraftReqAdder().registerAll();
        new GearSetsAdder().registerAll();

        new ReforgesAdder().registerAll();

    }
}
