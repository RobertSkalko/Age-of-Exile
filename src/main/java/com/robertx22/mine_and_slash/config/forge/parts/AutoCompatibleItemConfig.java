package com.robertx22.mine_and_slash.config.forge.parts;

import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;

public class AutoCompatibleItemConfig {

    public boolean ENABLE_AUTOMATIC_COMPATIBLE_ITEMS = false;
    public int MAX_SINGLE_STAT_VALUE = 50;
    public int MAX_TOTAL_STATS = 200;

    public AutoConfigItemType HORRIBLE = new AutoConfigItemType(0F, 20, IRarity.Common, IRarity.Common);
    public AutoConfigItemType TRASH = new AutoConfigItemType(0.03F, 40, IRarity.Common, IRarity.Magical);
    public AutoConfigItemType NORMAL = new AutoConfigItemType(0.3F, 80, IRarity.Magical, IRarity.Magical);
    public AutoConfigItemType BEST = new AutoConfigItemType(0.8F, Integer.MAX_VALUE, IRarity.Magical, IRarity.Rare);

}
