package com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.armor.cloth;

import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.bases.BaseArmorItem;
import net.minecraft.entity.EquipmentSlot;

public class ClothHelmetItem extends BaseArmorItem {
    public ClothHelmetItem(int rarity) {
        super(Type.CLOTH, rarity, EquipmentSlot.HEAD);

    }

    @Override
    public String locNameForLangFile() {
        Rarity rar = Rarities.Gears.get(rarity);
        return rar.textFormatting() + "Cloth Hat";
    }
}
