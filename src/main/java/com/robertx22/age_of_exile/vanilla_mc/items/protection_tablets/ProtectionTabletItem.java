package com.robertx22.age_of_exile.vanilla_mc.items.protection_tablets;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class ProtectionTabletItem extends Item implements IAutoLocName, IAutoModel, IShapelessRecipe {

    TabletTypes type;

    public ProtectionTabletItem(TabletTypes type) {
        super(new Settings().group(CreativeTabs.Tablets));
        this.type = type;
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
        return type.word + " Tablet";
    }

    @Override
    public String GUID() {
        return "tablet/" + type.id;
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }

    @Override
    public ShapelessRecipeJsonFactory getRecipe() {
        ShapelessRecipeJsonFactory fac = ShapelessRecipeJsonFactory.create(this);

        this.type.getRecipeItems()
            .forEach(x -> fac.input(x));

        return fac.criterion("player_level", trigger());
    }
}
