package com.robertx22.age_of_exile.config.forge;

import java.util.Arrays;
import java.util.List;

public class ServerContainer {

    public boolean ENABLE_FAVOR_SYSTEM = true;
    public float FAVOR_GAIN_PER_CHEST_LOOTED = 10;
    public int STARTING_FAVOR = 100;

    public boolean ALL_PLAYERS_ARE_TEAMED_PVE_MODE = false;
    public boolean GET_STARTER_ITEMS = true;
    public boolean ALWAYS_SCALE_MOB_LEVEL_TO_PLAYER = false;
    public boolean ENABLE_LOOT_ANNOUNCEMENTS = true;
    public boolean REQUIRE_TEAM_FOR_TEAM_DUNGEONS = true;
    public boolean DONT_SYNC_DATA_OF_AMBIENT_MOBS = true;

    public double REGEN_HUNGER_COST = 10;
    public double EXP_LOSS_ON_DEATH = 0.1F;
    public double EXP_GAIN_MULTI = 1F;
    public double PARTY_RADIUS = 200;
    public double MAX_INSTABILITY = 1000D;

    public double LEVEL_DISTANCE_PENALTY_PER_TIER = 0.5F;
    public double LEVEL_DISTANCE_PENALTY_MIN_MULTI = 0.01F;

    public int MAX_UNIQUE_GEARS_WORN = 99;

    public double EXTRA_MOB_STATS_PER_LEVEL = 0.02F;

    public double VANILLA_MOB_DMG_AS_EXILE_DMG = 1F;
    public double VANILLA_MOB_DMG_AS_EXILE_DMG_AT_MAX_LVL = 1F;

    public double PVP_DMG_MULTI = 1F;

    public List<String> BLACKLIST_SPELLS_IN_DIMENSIONS = Arrays.asList("modid:testdim");

    public double GEAR_DROPRATE = 7;
    public double GEM_DROPRATE = 0.5D;
    public double INGREDIENT_DROPRATE = 5;
    public double RUNE_DROPRATE = 0.05D;
    public double CURRENCY_DROPRATE = 0.2D;
}
