package com.robertx22.age_of_exile.player_skills.items.inscribing;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.item.Items;

public class EssencePaperItem extends AutoItem implements IShapelessRecipe {

    public EssencePaperItem() {
        super(new Settings().group(CreativeTabs.Professions));
    }

    @Override
    public String locNameForLangFile() {
        return "Essence Filled Paper";
    }

    @Override
    public String GUID() {
        return "";
    }

    @Override
    public ShapelessRecipeJsonFactory getRecipe() {
        ShapelessRecipeJsonFactory fac = ShapelessRecipeJsonFactory.create(this, 1);
        fac.input(Items.STRING);
        fac.input(Items.PAPER);
        fac.input(ModRegistry.TIERED.SALVAGED_ESSENCE_MAP.get(SkillItemTier.TIER1), 2);
        return fac.criterion("player_level", trigger());
    }
}
