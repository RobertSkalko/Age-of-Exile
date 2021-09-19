package com.robertx22.age_of_exile.database.data.stats.types.professions.all;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public enum BonusRequirement {

    NONE("none", "None") {
        @Override
        public boolean isAllowed(World world, BlockPos pos) {
            return true;
        }
    },
    RAINING("rain", "Raining") {
        @Override
        public boolean isAllowed(World world, BlockPos pos) {
            return world.isRaining();
        }
    },
    NIGHT("night", "Night Time") {
        @Override
        public boolean isAllowed(World world, BlockPos pos) {
            return world.isNight();
        }
    },
    DAY("day", "Day Time") {
        @Override
        public boolean isAllowed(World world, BlockPos pos) {
            return world.isDay();
        }
    },
    MOUNTAIN_BIOME("mountain_biome", "Mountain Biome") {
        @Override
        public boolean isAllowed(World world, BlockPos pos) {
            return world.getBiome(pos)
                .getBiomeCategory() == Biome.BiomeCategory.EXTREME_HILLS;
        }
    },
    SWAMP_BIOME("swamp_biome", "Swamp Biome") {
        @Override
        public boolean isAllowed(World world, BlockPos pos) {
            return world.getBiome(pos)
                .getBiomeCategory() == Biome.BiomeCategory.SWAMP;
        }
    },
    COLD_BIOME("cold_biome", "Cold Biome") {
        @Override
        public boolean isAllowed(World world, BlockPos pos) {
            return world.getBiome(pos)
                .getTemperature(pos) <= 0.15F; // 0.15F is temp that allows for snow
        }
    },
    HOT_BIOME("hot_biome", "Hot Biome") {
        @Override
        public boolean isAllowed(World world, BlockPos pos) {
            return world.getBiome(pos)
                .getTemperature(pos) >= 0.95F; // 0.95 means no rain
        }
    };

    public String id;
    public String name;

    BonusRequirement(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public abstract boolean isAllowed(World world, BlockPos pos);
}
