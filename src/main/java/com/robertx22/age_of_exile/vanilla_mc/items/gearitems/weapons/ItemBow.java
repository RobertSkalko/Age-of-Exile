package com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons;

import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ItemUtils;
import net.minecraft.item.BowItem;
import net.minecraft.util.registry.Registry;

public class ItemBow extends BowItem implements IAutoLocName {

    public ItemBow(String locname) {
        super(ItemUtils.getDefaultGearProperties()
            .maxCount(1)
            .maxDamageIfAbsent(500));
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
