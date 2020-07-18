package com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.armor.cloth;

import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.bases.BaseArmorItem;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;

import java.util.HashMap;

public class ClothPantsItem extends BaseArmorItem {
    public static HashMap<Integer, Item> Items = new HashMap<Integer, Item>();

    public ClothPantsItem(int rarity) {
        super(Type.CLOTH, rarity, EquipmentSlot.LEGS);

    }

    @Override
    public String locNameForLangFile() {
        Rarity rar = Rarities.Gears.get(rarity);
        return rar.textFormatting() + "Cloth Pants";
    }
}
