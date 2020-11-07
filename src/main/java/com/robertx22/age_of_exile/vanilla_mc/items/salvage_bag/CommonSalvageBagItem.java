package com.robertx22.age_of_exile.vanilla_mc.items.salvage_bag;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.item.Items;

import java.util.Arrays;
import java.util.List;

public class CommonSalvageBagItem extends SalvageBagItem {

    @Override
    public List<String> salvagableRaritiesInternal() {
        return Arrays.asList(IRarity.COMMON_ID);
    }

    @Override
    public float chanceToDestroyOutput() {
        return 25;
    }

    @Override
    public ShapedRecipeJsonFactory getRecipe() {
        return shaped(this)
            .input('t', Items.COAL)
            .input('v', ModRegistry.MISC_ITEMS.GEAR_SALVAGE)
            .input('o', Items.LEATHER)
            .pattern("t")
            .pattern("v")
            .pattern("o")
            .criterion("player_level", trigger());
    }

    @Override
    public String locNameForLangFile() {
        return "Common Salvage Bag";
    }

}
