package com.robertx22.mine_and_slash.database.data.stats.tooltips;

public enum StatTooltipType {

    NORMAL(new NormalStatTooltip()),
    BASE_LOCAL_STATS(new BaseLocalStatTooltip());

    StatTooltipType(IStatTooltipType impl) {
        this.impl = impl;
    }

    public IStatTooltipType impl;

}