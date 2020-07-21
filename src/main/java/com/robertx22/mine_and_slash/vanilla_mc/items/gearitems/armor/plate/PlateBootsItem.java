package com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.armor.plate;

import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.bases.BaseArmorItem;
import net.minecraft.entity.EquipmentSlot;

public class PlateBootsItem extends BaseArmorItem {

    public PlateBootsItem(int rarity) {
        super(Type.PLATE, rarity, EquipmentSlot.FEET);

    }

    @Override
    public String locNameForLangFile() {
        Rarity rar = Rarities.Gears.get(rarity);
        return rar.textFormatting() + "Plate Boots";
    }
}
