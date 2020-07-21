package com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.armor.plate;

import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.bases.BaseArmorItem;
import net.minecraft.entity.EquipmentSlot;

public class PlatePantsItem extends BaseArmorItem {

    public PlatePantsItem(int rarity) {
        super(Type.PLATE, rarity, EquipmentSlot.LEGS);

    }

    @Override
    public String locNameForLangFile() {
        Rarity rar = Rarities.Gears.get(rarity);
        return rar.textFormatting() + "Plate Greaves";
    }
}
