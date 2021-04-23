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

    public double REGEN_HUNGER_COST = 10;
    public double EXP_LOSS_ON_DEATH = 0.1F;
    public double EXP_GAIN_MULTI = 1F;
    public double PARTY_RADIUS = 200;

    public double CHANCE_TO_CORRUPT_ITEM_AT_MAX_LEVEL = 20;
    public double START_CORRUPT_AT_LVL = 30;

    public double LEVEL_DISTANCE_PENALTY_PER_LVL = 0.15F;
    public double LEVEL_DISTANCE_PENALTY_MIN_MULTI = 0.01F;
    public int LEVELS_NEEDED_TO_START_LVL_PENALTY = 5;

    public int MAX_RUNEWORD_GEARS_ON_PLAYER = 1;
    public int MAX_UNIQUE_GEARS_ON_PLAYER = 10;
    public int MAX_CHARACTERS = 8;

    public double EXTRA_MOB_STATS_PER_LEVEL = 0.02F;

    public double VANILLA_MOB_DMG_AS_EXILE_DMG = 0.75F;
    public double VANILLA_MOB_DMG_AS_EXILE_DMG_AT_MAX_LVL = 1F;

    public double PVP_DMG_MULTI = 1F;

    public List<String> IGNORED_ENTITIES = Arrays.asList("minecraft:bat");

}
