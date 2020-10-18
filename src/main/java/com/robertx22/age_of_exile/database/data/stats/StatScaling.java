package com.robertx22.age_of_exile.database.data.stats;

import com.robertx22.age_of_exile.config.forge.ModConfig;

public enum StatScaling {

    NONE {
        @Override
        public float scale(float val, int lvl) {
            return val;
        }
    },
    NORMAL {
        @Override
        public float scale(float val, int lvl) {
            return val * ModConfig.get().statScalings.NORMAL_STAT_SCALING.getMultiFor(lvl);
        }
    },
    LINEAR {
        @Override
        public float scale(float val, int lvl) {
            return val * lvl;
        }
    },
    SLOW {
        @Override
        public float scale(float val, int lvl) {
            return val * ModConfig.get().statScalings.SLOW_STAT_SCALING.getMultiFor(lvl);
        }
    };

    StatScaling() {

    }

    public abstract float scale(float val, int lvl);
}
