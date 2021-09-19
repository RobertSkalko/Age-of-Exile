package com.robertx22.age_of_exile.player_skills.items.inscribing;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Items;

public class EssencePaperItem extends AutoItem implements IShapelessRecipe {

    public EssencePaperItem() {
        super(new Properties().tab(CreativeTabs.Professions));
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
    public ShapelessRecipeBuilder getRecipe() {
        ShapelessRecipeBuilder fac = ShapelessRecipeBuilder.shapeless(this, 1);
        fac.requires(Items.STRING);
        fac.requires(Items.PAPER);
        fac.requires(ModRegistry.TIERED.SALVAGED_ESSENCE_MAP.get(SkillItemTier.TIER1), 2);
        return fac.unlockedBy("player_level", trigger());
    }
}
