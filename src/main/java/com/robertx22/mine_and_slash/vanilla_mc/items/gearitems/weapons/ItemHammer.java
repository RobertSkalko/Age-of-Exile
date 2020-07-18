package com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.weapons;

import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.bases.BaseWeaponItem;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;
import net.minecraft.item.Item;

import java.util.HashMap;

public class ItemHammer extends BaseWeaponItem {
    public static HashMap<Integer, Item> Items = new HashMap<Integer, Item>();

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
