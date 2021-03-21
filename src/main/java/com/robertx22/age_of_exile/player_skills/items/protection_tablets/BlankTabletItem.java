package com.robertx22.age_of_exile.player_skills.items.protection_tablets;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.backpacks.IGatheringMat;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class BlankTabletItem extends Item implements IAutoLocName, IAutoModel, IGatheringMat, IShapelessRecipe {
    BlankTabletTier tier;
    SkillItemTier stier;

    public BlankTabletItem(SkillItemTier stier, BlankTabletTier tier) {
        super(new Settings().group(CreativeTabs.Professions));
        this.tier = tier;
        this.stier = stier;
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public String locNameLangFileGUID() {
        return Registry.ITEM.getId(this)
            .toString();
    }

    @Override
    public String locNameForLangFile() {
        return tier.prefixName + "Blank Tablet";
    }

    @Override
    public String GUID() {
        return "";
    }

    @Override
    public ShapelessRecipeJsonFactory getRecipe() {
        return ShapelessRecipeJsonFactory.create(this)
            .input(ModRegistry.TIERED.STONE_TIER_MAP.get(stier), 3)
            .criterion("player_level", trigger());
    }

}
