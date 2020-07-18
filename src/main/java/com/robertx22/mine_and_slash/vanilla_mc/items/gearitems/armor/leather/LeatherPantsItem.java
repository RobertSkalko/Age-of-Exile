package com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.armor.leather;

import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.bases.BaseArmorItem;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;

import java.util.HashMap;

public class LeatherPantsItem extends BaseArmorItem {
    public static HashMap<Integer, Item> Items = new HashMap<Integer, Item>();

    public LeatherPantsItem(int rarity) {
        super(Type.LEATHER, rarity, EquipmentSlot.LEGS);

    }

    @Override
    public String locNameForLangFile() {
        Rarity rar = Rarities.Gears.get(rarity);
        return rar.textFormatting() + "Leather Pants";
    }
}