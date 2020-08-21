package com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases;

import com.robertx22.age_of_exile.uncommon.localization.Words;

public enum GearItemEnum {

    NORMAL() {
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

}
