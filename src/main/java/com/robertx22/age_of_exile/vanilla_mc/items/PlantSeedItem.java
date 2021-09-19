package com.robertx22.age_of_exile.vanilla_mc.items;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.minecraft.block.Block;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.util.registry.Registry;

public class PlantSeedItem extends BlockNamedItem implements IAutoLocName, IShapelessRecipe, IAutoModel {

    SkillItemTier tier;

    public PlantSeedItem(SkillItemTier tier, Block block) {
        super(block, new Properties().tab(CreativeTabs.Professions));
        this.tier = tier;
    }

    @Override
    public ShapelessRecipeBuilder getRecipe() {
        ShapelessRecipeBuilder fac = ShapelessRecipeBuilder.shapeless(this, 9);
        fac.requires(ModRegistry.TIERED.SALVAGED_ESSENCE_MAP.get(tier), 3);
        fac.requires(ModRegistry.TIERED.CONDENSED_ESSENCE_MAP.get(tier));
        return fac.unlockedBy("player_level", trigger());
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public String locNameLangFileGUID() {
        return Registry.ITEM.getKey(this)
            .toString();
    }

    @Override
    public String locNameForLangFile() {
        return tier.word + " Wheat Seed";
    }

    @Override
    public String GUID() {
        return "";
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }
}
