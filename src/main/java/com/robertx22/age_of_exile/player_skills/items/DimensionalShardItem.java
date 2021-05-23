package com.robertx22.age_of_exile.player_skills.items;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.item.Items;

public class DimensionalShardItem extends TieredItem implements IShapelessRecipe {

    public DimensionalShardItem(SkillItemTier tier) {
        super(tier, new Settings().group(CreativeTabs.MyModTab)
            .maxCount(64));
    }

    @Override
    public String locNameForLangFile() {
        return tier.word + " Dimensional Shard";
    }

    @Override
    public String GUID() {
        return "dim_shard/" + tier.tier;
    }

    @Override
    public ShapelessRecipeJsonFactory getRecipe() {
        ShapelessRecipeJsonFactory fac = ShapelessRecipeJsonFactory.create(this, 16);
        fac.input(Items.GOLD_INGOT);
        fac.input(Items.DIAMOND);
        fac.input(ModRegistry.TIERED.SMELTED_ESSENCE.get(tier), 3);
        return fac.criterion("player_level", trigger());
    }
}
