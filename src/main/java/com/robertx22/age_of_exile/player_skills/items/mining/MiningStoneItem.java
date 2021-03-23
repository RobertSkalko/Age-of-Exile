package com.robertx22.age_of_exile.player_skills.items.mining;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.TieredItem;
import com.robertx22.age_of_exile.player_skills.items.backpacks.IGatheringMat;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;

public class MiningStoneItem extends TieredItem implements IGatheringMat, IShapelessRecipe {

    public MiningStoneItem(SkillItemTier tier) {
        super(tier, new Settings().group(CreativeTabs.Professions));
    }

    @Override
    public String locNameForLangFile() {
        return tier.word + " Stone";
    }

    @Override
    public String GUID() {
        return "stone/" + tier.tier;
    }

    @Override
    public ShapelessRecipeJsonFactory getRecipe() {
        if (this.tier.higherTier() == null) {
            return null;
        }
        ShapelessRecipeJsonFactory fac = ShapelessRecipeJsonFactory.create(this, 2);
        fac.input(ModRegistry.TIERED.STONE_TIER_MAP.get(tier.higherTier()), 1);
        return fac.criterion("player_level", trigger());
    }
}

