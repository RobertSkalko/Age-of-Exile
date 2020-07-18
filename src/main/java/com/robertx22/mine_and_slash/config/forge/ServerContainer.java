package com.robertx22.mine_and_slash.config.forge;

import java.util.Arrays;
import java.util.List;

public class ServerContainer {

    public boolean USE_COMPATIBILITY_ITEMS = false;
    public boolean LOG_REGISTRY_ENTRIES = false;

    public double REPAIR_FUEL_NEEDED_MULTI = 1;
    public double REGEN_HUNGER_COST = 20;
    public double EXP_LOSS_ON_DEATH = 0.05F;
    public double STAT_POINTS_PER_LVL = 1;
    public double VANILLA_ARMOR_EFFECTIVENESS = 0;

    public List<String> IGNORED_ENTITIES = Arrays.asList("minecraft:bat");

    public int MAX_LEVEL = 50;

}
