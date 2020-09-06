package com.robertx22.age_of_exile.vanilla_mc.items.misc;

import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public abstract class AutoItem extends Item implements IShapedRecipe, IAutoLocName {

    public AutoItem(Settings settings) {
        super(settings);
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public String locNameLangFileGUID() {
        return Registry.ITEM.getId(this)
            .toString();
    }
}
