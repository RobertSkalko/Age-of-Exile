package com.robertx22.age_of_exile.player_skills.items.foods;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;

public class FoodExtractItem extends Item implements IAutoLocName, IAutoModel, IShapelessRecipe {

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

    @Override
    public ShapelessRecipeJsonFactory getRecipe() {
        ShapelessRecipeJsonFactory fac = ShapelessRecipeJsonFactory.create(this);
        fac.input(this.color.essenceItem.get(), 3);
        fac.input(Items.BOWL);
        return fac.criterion("player_level", trigger());
    }
}
