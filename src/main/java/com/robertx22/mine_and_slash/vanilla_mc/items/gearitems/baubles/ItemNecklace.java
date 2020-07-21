package com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.baubles;

import com.robertx22.mine_and_slash.a_libraries.curios.interfaces.INecklace;
import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.bases.BaseBaublesItem;

public class ItemNecklace extends BaseBaublesItem implements INecklace {

    public ItemNecklace(int rar) {
        super(rar);
    }

    @Override
    public String locNameForLangFile() {
        Rarity rar = Rarities.Gears.get(rarity);
        return rar.textFormatting() + "Necklace";
    }

}
