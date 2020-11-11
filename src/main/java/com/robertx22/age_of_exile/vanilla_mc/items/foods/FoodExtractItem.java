package com.robertx22.age_of_exile.vanilla_mc.items.foods;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class FoodExtractItem extends Item implements IAutoLocName, IAutoModel {

    FoodExileEffect.EffectColor color;

    public FoodExtractItem(FoodExileEffect.EffectColor color) {
        super(new Settings().group(CreativeTabs.Foods));
        this.color = color;
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
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
        return color.word + " Extract";
    }

    @Override
    public String GUID() {
        return "food/extract/" + color.id;
    }

}
