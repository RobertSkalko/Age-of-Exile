package com.robertx22.age_of_exile.player_skills.items.inscribing;

import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.TieredItem;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.item.Items;

public class EssenceInkItem extends TieredItem implements IShapelessRecipe {

    public EssenceInkItem(SkillItemTier tier) {
        super(tier);
    }

    @Override
    public String locNameForLangFile() {
        return tier.word + " Essence Ink";
    }

    @Override
    public String GUID() {
        return "essence_ink/" + tier.tier;
    }

    @Override
    public ShapelessRecipeJsonFactory getRecipe() {
        ShapelessRecipeJsonFactory fac = ShapelessRecipeJsonFactory.create(this, 1);
        fac.input(ModRegistry.TIERED.INK_TIER_MAP.get(this.tier));
        fac.input(Items.GLASS_BOTTLE);
        fac.input(ModRegistry.TIERED.CONDENSED_ESSENCE_MAP.get(tier));
        return fac.criterion("player_level", trigger());
    }
}
