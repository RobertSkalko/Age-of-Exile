package com.robertx22.age_of_exile.vanilla_mc.items;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.minecraft.block.Block;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class PlantSeedItem extends AliasedBlockItem implements IAutoLocName, IShapelessRecipe, IAutoModel {

    String locname;
    Item recipeItem;

    public PlantSeedItem(Block block, Item recipeItem, String locname) {
        super(block, new Settings().group(CreativeTabs.Professions));
        this.recipeItem = recipeItem;
        this.locname = locname;
    }

    @Override
    public ShapelessRecipeJsonFactory getRecipe() {
        ShapelessRecipeJsonFactory fac = ShapelessRecipeJsonFactory.create(this, 3);
        fac.input(ModRegistry.MISC_ITEMS.T1_DUST, 3);
        fac.input(recipeItem);
        return fac.criterion("player_level", trigger());
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
        return locname;
    }

    @Override
    public String GUID() {
        return "";
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }
}
