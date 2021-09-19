package com.robertx22.age_of_exile.player_skills.items.fishing;

import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.TieredItem;
import com.robertx22.age_of_exile.player_skills.items.backpacks.IGatheringMat;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import net.minecraft.data.ShapelessRecipeBuilder;

public class ScribeInkItem extends TieredItem implements IGatheringMat, IShapelessRecipe {

    public ScribeInkItem(SkillItemTier tier) {
        super(tier);
    }

    @Override
    public String locNameForLangFile() {
        return tier.word + " Ink";
    }

    @Override
    public String GUID() {
        return "ink/" + tier.tier;
    }

    @Override
    public ShapelessRecipeBuilder getRecipe() {
        if (this.tier.higherTier() == null) {
            return null;
        }
        ShapelessRecipeBuilder fac = ShapelessRecipeBuilder.shapeless(this, 2);
        fac.requires(ModRegistry.TIERED.INK_TIER_MAP.get(tier.higherTier()), 1);
        return fac.unlockedBy("player_level", trigger());
    }
}

