package com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases;

import com.robertx22.mine_and_slash.config.forge.ModConfig;
import com.robertx22.mine_and_slash.uncommon.interfaces.IWeighted;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.RandomUtils;

import java.util.Arrays;

public enum GearItemEnum implements IWeighted {

    NORMAL() {
        @Override
        public int Weight() {
            return (int) (ModConfig.INSTANCE.DropRates.GEAR_DROPRATE.get()
                .floatValue() * 100); // cus some config values are < 1 like 0.1F
        }

        @Override
        public boolean canGetAffixes() {
            return true;
        }

        @Override
        public boolean canRerollNumbers() {
            return true;
        }

        @Override
        public Words word() {
            return Words.Normal_Gear;
        }
    },

    UNIQUE() {
        @Override
        public int Weight() {
            return (int) (ModConfig.INSTANCE.DropRates.UNIQUE_DROPRATE.get()
                .floatValue() * 100); // cus some config values are < 1 like 0.1F
        }

        @Override
        public boolean canRerollNumbers() {
            return true;
        }

        @Override
        public boolean canGetAffixes() {
            return false;
        }

        @Override
        public Words word() {
            return Words.Unique_Gear;
        }
    };

    public abstract boolean canRerollNumbers();

    public abstract boolean canGetAffixes();

    public abstract Words word();

    public static GearItemEnum random() {
        return RandomUtils.weightedRandom(Arrays.asList(GearItemEnum.values()));
    }

}
