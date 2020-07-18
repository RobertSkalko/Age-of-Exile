package com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.weapons;

import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.bases.BaseArmorItem;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;
import com.robertx22.mine_and_slash.uncommon.interfaces.IGearItem;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ItemUtils;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;

import java.util.HashMap;

public class ItemBow extends BowItem implements IAutoLocName, IGearItem {
    public static HashMap<Integer, Item> Items = new HashMap<Integer, Item>();

    public ItemBow(int rar) {
        super(ItemUtils.getDefaultGearProperties()
            .maxCount(1)
            .maxDamageIfAbsent(BaseArmorItem.GetMat(BaseArmorItem.Type.PLATE, rar)
                .getDurability()));
        this.rarity = rar;
    }

    public int rarity = 0;

    @Override
    public String locNameForLangFile() {
        Rarity rar = Rarities.Gears.get(rarity);
        return rar.textFormatting() + "Bow";
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Gear_Items;
    }

    @Override
    public String locNameLangFileGUID() {
        return this.getRegistryName()
            .toString();
    }

    @Override
    public String GUID() {
        return "";
    }

}
