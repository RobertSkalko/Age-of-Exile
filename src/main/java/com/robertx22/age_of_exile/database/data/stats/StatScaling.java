package com.robertx22.age_of_exile.database.data.stats;

import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;

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
            return val * GameBalanceConfig.get().NORMAL_STAT_SCALING.getMultiFor(lvl);
        }
    },
    STAT_REQ {
        @Override
        public float scale(float val, int lvl) {
            return val * GameBalanceConfig.get().STAT_REQ_SCALING.getMultiFor(lvl);
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
            return val * GameBalanceConfig.get().SLOW_STAT_SCALING.getMultiFor(lvl);
        }
    };

    StatScaling() {

    }

    public abstract float scale(float val, int lvl);
}
