package com.robertx22.age_of_exile.config.forge;

import java.util.Arrays;
import java.util.List;

public class ServerContainer {

    public boolean ALL_PLAYERS_ARE_TEAMED_PVE_MODE = false;
    public boolean LOG_REGISTRY_ENTRIES = false;
    public boolean GET_STARTER_ITEMS = true;
    public boolean ALWAYS_SCALE_MOB_LEVEL_TO_PLAYER = false;
    public boolean ENABLE_LOOT_ANNOUNCEMENTS = true;
    public boolean SAVE_GEAR_TO_CHARACTERS = true;
    public boolean REQUIRE_TEAM_FOR_TEAM_DUNGEONS = true;
    public boolean DONT_SYNC_DATA_OF_AMBIENT_MOBS = true;

    public double REGEN_HUNGER_COST = 10;
    public double EXP_LOSS_ON_DEATH = 0.1F;
    public double EXP_GAIN_MULTI = 1F;
    public double PARTY_RADIUS = 200;
    public double RANDOM_SKILL_GEM_SALVAGE_CHANCE = 30;

    public double CHANCE_TO_CORRUPT_ITEM_AT_MAX_LEVEL = 20;
    public double START_CORRUPT_AT_LVL = 30;

    public double LEVEL_DISTANCE_PENALTY_PER_LVL = 0.15F;
    public double LEVEL_DISTANCE_PENALTY_MIN_MULTI = 0.01F;
    public double DUNGEON_RANDOM_ITEM_CHANCE = 10F;
    public int LEVELS_NEEDED_TO_START_LVL_PENALTY = 5;
    public int SWITCHING_LOADOUT_COOLDOWN_TICKS = 6000;

    public int MAX_RUNEWORD_GEARS_WORN = 1;
    public int MAX_UNIQUE_GEARS_WORN = 99;
    public int MAX_LOADOUTS = 3;

    public double EXTRA_MOB_STATS_PER_LEVEL = 0.02F;

    public double VANILLA_MOB_DMG_AS_EXILE_DMG = 1F;
    public double VANILLA_MOB_DMG_AS_EXILE_DMG_AT_MAX_LVL = 1F;

    public double PVP_DMG_MULTI = 1F;

    public List<String> BLACKLIST_SPELLS_IN_DIMENSIONS = Arrays.asList("modid:testdim");

}
