package com.robertx22.mine_and_slash.uncommon.interfaces.data_items;

import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.saveclasses.item_classes.JewelData;
import com.robertx22.mine_and_slash.saveclasses.item_classes.SkillGemData;
import com.robertx22.mine_and_slash.uncommon.localization.Words;

public enum DataItemType {

    GEAR("gear", Words.Gears) {
        @Override
        public boolean isType(ICommonDataItem data) {
            return data instanceof GearItemData;
        }
    },
    JEWEL("jewel", Words.Gears) {
        @Override
        public boolean isType(ICommonDataItem data) {
            return data instanceof JewelData;
        }
    },
    SKILL_GEM("gear", Words.Gears) {
        @Override
        public boolean isType(ICommonDataItem data) {
            return data instanceof SkillGemData;
        }
    };

    DataItemType(String nbtGUID, Words word) {
        this.nbtGUID = nbtGUID;
        this.word = word;
    }

    public abstract boolean isType(ICommonDataItem data);

    public Words word;
    public String nbtGUID;
}
