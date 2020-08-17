package com.robertx22.age_of_exile.areas.area_modifiers;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AreaRequirement {

    private Set<Category> catWhitelist = new HashSet<>();
    private Set<Category> catBlacklist = new HashSet<>(Arrays.asList(Category.RIVER, Category.OCEAN)); // by default none accept these

    private float minTemperature = Integer.MIN_VALUE;
    private float maxTemperature = Integer.MAX_VALUE;

    public enum WhitelistType {
        REQUIRE, ALLOW_ALSO
    }

    WhitelistType tempType = WhitelistType.REQUIRE;

    private boolean whitelistAll = false;

    public AreaRequirement temp(float min, float max, WhitelistType type) {
        minTemperature = min;
        maxTemperature = max;
        this.tempType = type;
        return this;
    }

    public AreaRequirement whitelistAll() {
        this.whitelistAll = true;
        return this;
    }

    public AreaRequirement whitelist(Category... cats) {
        this.catWhitelist.addAll(Arrays.asList(cats));
        return this;
    }

    public AreaRequirement blacklist(Category... cats) {
        this.catBlacklist.addAll(Arrays.asList(cats));
        return this;
    }

    public boolean isBiomeAcceptable(Biome biome) {

        if (biome.getTemperature() <= minTemperature || biome.getTemperature() >= maxTemperature) {
            if (tempType == WhitelistType.REQUIRE) {
                return false;
            }
        } else {
            if (tempType == WhitelistType.ALLOW_ALSO) {
                return true;
            }
        }

        if (catBlacklist.stream()
            .anyMatch(x -> x == biome.getCategory())) {
            return false;
        }
        if (whitelistAll || catWhitelist.stream()
            .anyMatch(x -> x == biome.getCategory())) {
            return true;
        }

        return false;

    }

}
