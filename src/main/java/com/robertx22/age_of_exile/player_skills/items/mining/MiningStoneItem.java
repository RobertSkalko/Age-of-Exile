package com.robertx22.age_of_exile.player_skills.items.mining;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.TieredItem;
import com.robertx22.age_of_exile.player_skills.items.backpacks.IGatheringMat;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import net.minecraft.data.ShapelessRecipeBuilder;

public class MiningStoneItem extends TieredItem implements IGatheringMat, IShapelessRecipe {

    public MiningStoneItem(SkillItemTier tier) {
        super(tier, new Properties().tab(CreativeTabs.Professions));
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
    public ShapelessRecipeBuilder getRecipe() {
        if (this.tier.higherTier() == null) {
            return null;
        }
        ShapelessRecipeBuilder fac = ShapelessRecipeBuilder.shapeless(this, 2);
        fac.requires(ModRegistry.TIERED.STONE_TIER_MAP.get(tier.higherTier()), 1);
        return fac.unlockedBy("player_level", trigger());
    }
}

