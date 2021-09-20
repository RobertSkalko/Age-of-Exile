package com.robertx22.age_of_exile.player_skills.items.alchemy;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.ProfessionItems;
import com.robertx22.age_of_exile.player_skills.items.TieredItem;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.minecraft.data.ShapelessRecipeBuilder;

public class CondensedEssenceItem extends TieredItem implements IAutoLocName, IAutoModel, IShapelessRecipe {

    public CondensedEssenceItem(SkillItemTier tier) {
        super(tier);
    }

    @Override
    public ShapelessRecipeBuilder getRecipe() {
        ShapelessRecipeBuilder fac = ShapelessRecipeBuilder.shapeless(this, 1);
        fac.requires(ProfessionItems.SMELTED_ESSENCE.get(tier)
            .get(), 3);
        return fac.unlockedBy("player_level", trigger());
    }

    @Override
    public String locNameForLangFile() {
        return "Condensed " + tier.word + " Essence";
    }

    @Override
    public String GUID() {
        return "mat/condensed_salvage/c" + tier.tier;
    }
}
