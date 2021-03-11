package com.robertx22.age_of_exile.database.data.stats.types.professions.all;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IGenerated;

import java.util.ArrayList;
import java.util.List;

public class BonusYield extends Stat implements IGenerated<Stat> {

    public BonusRequirement req;

    public BonusYield(BonusRequirement req) {
        this.req = req;
    }

    @Override
    public String locDescForLangFile() {
        return "";
    }

    private BonusYield() {
        this.base_val = 0;
        this.min_val = 0;
        this.scaling = StatScaling.SLOW;
        this.statGroup = StatGroup.Misc;
    }

    @Override
    public String GUID() {
        return "bonus_" + req.id + "_yield";
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return req.name + " Yield";
    }

    @Override
    public List<Stat> generateAllPossibleStatVariations() {
        List<Stat> list = new ArrayList<>();
        for (BonusRequirement r : BonusRequirement.values()) {
            list.add(new BonusYield(r));
        }
        return list;
    }
}
