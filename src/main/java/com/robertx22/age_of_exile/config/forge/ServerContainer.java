package com.robertx22.age_of_exile.config.forge;

import java.util.Arrays;
import java.util.List;

public class ServerContainer {

    public boolean ALLOW_EXILE_MOBS_DAY_SPAWNS = true;
    public boolean ALL_PLAYERS_ARE_TEAMED_PVE_MODE = true;
    public boolean ALLOW_OBSIDIAN_BREAKING_MEGA_EXPLOSIONS = true;
    public boolean LOG_REGISTRY_ENTRIES = false;
    public boolean GET_STARTER_ITEMS = false;
    public boolean ALWAYS_SCALE_MOB_LEVEL_TO_PLAYER = false;

    public double REPAIR_FUEL_NEEDED_MULTI = 1;
    public double REGEN_HUNGER_COST = 10;
    public double EXP_LOSS_ON_DEATH = 0.05F;
    public double LEVEL_DISTANCE_PENALTY_PER_LVL = 0.02F;
    public double EXTRA_MOB_STATS_PER_LEVEL = 0.01F;
    public double STAT_POINTS_PER_LVL = 1;
    public double VANILLA_MOB_DMG_AS_EXILE_DMG = 0.5F;

    public List<String> IGNORED_ENTITIES = Arrays.asList("minecraft:bat");

    public int MAX_LEVEL = 50;
    public int LVL_WHEN_MOBS_START_SPAWNING_IN_DAYLIGHT = 10;

}
