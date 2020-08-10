package com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.weapons;

import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;
import com.robertx22.mine_and_slash.uncommon.interfaces.IGearItem;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ItemUtils;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.bases.BaseArmorItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.BowItem;
import net.minecraft.util.registry.Registry;

public class ItemBow extends BowItem implements IAutoLocName, IGearItem {

    public ItemBow(String locname) {
        super(ItemUtils.getDefaultGearProperties()
            .maxCount(1)
            .maxDamageIfAbsent(BaseArmorItem.GetMat(BaseArmorItem.Type.PLATE, false)
                .getDurability(EquipmentSlot.MAINHAND)));
        this.locname = locname;
    }

    String locname;

    @Override
    public String locNameForLangFile() {
        return "Bow";
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Gear_Items;
    }

    @Override
    public String locNameLangFileGUID() {
        return Registry.ITEM.getId(this)
            .toString();
    }

    @Override
    public String GUID() {
        return "";
    }

}
