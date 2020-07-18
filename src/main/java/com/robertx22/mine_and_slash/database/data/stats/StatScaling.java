package com.robertx22.mine_and_slash.database.data.stats;

import com.robertx22.mine_and_slash.config.forge.ModConfig;

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
            return val + (val * ModConfig.INSTANCE.statScalings.LINEAR_SCALING.PERCENT_ADDED_PER_LEVEL.get()
                .floatValue() * lvl);
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
            return val + (val * ModConfig.INSTANCE.statScalings.SLOW_LINEAR_SCALING.PERCENT_ADDED_PER_LEVEL.get()
                .floatValue() * lvl);
        }
    };

    StatScaling() {

    }

    public abstract float scale(float val, int lvl);
}
