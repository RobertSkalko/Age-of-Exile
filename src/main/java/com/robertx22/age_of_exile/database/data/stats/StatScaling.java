package com.robertx22.age_of_exile.database.data.stats;

import com.robertx22.age_of_exile.config.forge.ModConfig;

public enum StatScaling {
    NONE {
        @Override
        public float scale(float val, int lvl) {
            return val;
        }
    },
    SCALING {
        @Override
        public float scale(float val, int lvl) {
            return (float) (val + (val * ModConfig.get().statScalings.LINEAR_SCALING.PERCENT_ADDED_PER_LEVEL
                * lvl));
        }
    },
    LINEAR {
        @Override
        public float scale(float val, int lvl) {
            return val * lvl;
        }
    },
    SLOW_SCALING {
        @Override
        public float scale(float val, int lvl) {
            return (float) (val + (val * ModConfig.get().statScalings.SLOW_LINEAR_SCALING.PERCENT_ADDED_PER_LEVEL
                * lvl));
        }
    };

    StatScaling() {

    }

    public abstract float scale(float val, int lvl);
}
