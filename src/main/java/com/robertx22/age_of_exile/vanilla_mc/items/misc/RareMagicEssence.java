package com.robertx22.age_of_exile.vanilla_mc.items.misc;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.interfaces.IWeighted;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class RareMagicEssence extends Item implements IAutoLocName, IWeighted, IAutoModel {

    public RareMagicEssence() {
        super(new Settings().maxCount(64)
            .group(CreativeTabs.MyModTab));
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

    @Override
    public String locNameForLangFile() {
        return "Rare Magic Essence";
    }

    @Override
    public String GUID() {
        return "mat/rare_magic_essence";
    }

    @Override
    public int Weight() {
        return 100;
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }
}
