package com.robertx22.mine_and_slash.database.data.rarities;

import com.robertx22.mine_and_slash.database.data.MinMax;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;

public interface SpellRarity extends Rarity, SalvagableItem {

    MinMax SpellBasePercents();

    MinMax SpellScalingPercents();

}
