package com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.weapons;

import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.bases.BaseWeaponItem;

public class ItemHammer extends BaseWeaponItem {

    public ItemHammer(int rar) {
        super(rar);
        this.attackSpeed = -2.8F;
    }

    @Override
    public String locNameForLangFile() {
        Rarity rar = Rarities.Gears.get(rarity);
        return rar.textFormatting() + "Hammer";
    }

}
