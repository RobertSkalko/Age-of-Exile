package com.robertx22.age_of_exile.config.forge;

import java.util.Arrays;
import java.util.List;

public class ServerContainer {

    public boolean ALL_PLAYERS_ARE_TEAMED_PVE_MODE = true;
    public boolean LOG_REGISTRY_ENTRIES = false;
    public boolean GET_STARTER_ITEMS = true;
    public boolean ALWAYS_SCALE_MOB_LEVEL_TO_PLAYER = false;
    public boolean SAVE_GEAR_AND_HOTBAR_ON_DEATH = false;
    public boolean ENABLE_LOOT_ANNOUNCEMENTS = true;

    //public double REPAIR_FUEL_NEEDED_MULTI = 1;
    public double REGEN_HUNGER_COST = 10;
    public double EXP_LOSS_ON_DEATH = 0.05F;
    public double LEVEL_DISTANCE_PENALTY_PER_LVL = 0.1F;
    public double LEVEL_DISTANCE_PENALTY_MAX = 0.95F;
    public int LEVELS_NEEDED_TO_START_LVL_PENALTY = 5;
    public double EXTRA_MOB_STATS_PER_LEVEL = 0.01F;

    public double STAT_POINTS_PER_LVL = 1;
    public double TALENT_POINTS_AT_MAX_LEVEL = 100;
    public double STARTING_TALENT_POINTS = 1;
    public double SPELL_POINTS_AT_MAX_LEVEL = 20;
    public double STARTING_SPELL_POINTS = 2;

    public double VANILLA_MOB_DMG_AS_EXILE_DMG = 0.5F;
    public double PVP_DMG_MULTI = 1F;

    public List<String> IGNORED_ENTITIES = Arrays.asList("minecraft:bat");

    public int MAX_LEVEL = 50;

}
