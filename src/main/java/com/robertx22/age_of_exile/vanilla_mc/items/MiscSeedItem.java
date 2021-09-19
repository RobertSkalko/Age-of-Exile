package com.robertx22.age_of_exile.vanilla_mc.items;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.minecraft.block.Block;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;

public class MiscSeedItem extends BlockNamedItem implements IAutoLocName, IShapelessRecipe, IAutoModel {

    public MiscSeedItem(String name, Item recipeItem, Block block) {
        super(block, new Item.Properties().tab(CreativeTabs.Professions));
        this.name = name;
        this.recipeItem = recipeItem;
    }

    String name;
    Item recipeItem;

    @Override
    public ShapelessRecipeBuilder getRecipe() {
        ShapelessRecipeBuilder fac = ShapelessRecipeBuilder.shapeless(this, 6);
        fac.requires(recipeItem);
        fac.requires(Items.WHEAT_SEEDS, 2);
        return fac.unlockedBy("player_level", trigger());
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public String locNameLangFileGUID() {
        return Registry.ITEM.getKey(this)
            .toString();
    }

    @Override
    public String locNameForLangFile() {
        return name;
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
