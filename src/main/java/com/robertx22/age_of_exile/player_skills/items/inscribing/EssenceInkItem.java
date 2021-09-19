package com.robertx22.age_of_exile.player_skills.items.inscribing;

import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.ISkillRequirement;
import com.robertx22.age_of_exile.player_skills.items.SkillRequirement;
import com.robertx22.age_of_exile.player_skills.items.TieredItem;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Items;

public class EssenceInkItem extends TieredItem implements IShapelessRecipe, ISkillRequirement {

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
    public ShapelessRecipeBuilder getRecipe() {
        ShapelessRecipeBuilder fac = ShapelessRecipeBuilder.shapeless(this, 1);
        fac.requires(ModRegistry.TIERED.INK_TIER_MAP.get(this.tier));
        fac.requires(Items.GLASS_BOTTLE);
        fac.requires(ModRegistry.TIERED.CONDENSED_ESSENCE_MAP.get(tier));
        return fac.unlockedBy("player_level", trigger());
    }

    @Override
    public SkillRequirement getSkillRequirement() {
        return new SkillRequirement(tier.levelRange.getMinLevel(), PlayerSkillEnum.INSCRIBING);
    }
}
