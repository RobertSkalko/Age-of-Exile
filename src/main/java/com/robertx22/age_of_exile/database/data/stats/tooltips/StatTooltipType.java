package com.robertx22.age_of_exile.database.data.stats.tooltips;

public enum StatTooltipType {

    NORMAL(new NormalStatTooltip());

    StatTooltipType(IStatTooltipType impl) {
        this.impl = impl;
    }

    public IStatTooltipType impl;

}