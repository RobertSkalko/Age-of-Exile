package com.robertx22.mine_and_slash.event_hooks.data_gen.providers;

import com.robertx22.mine_and_slash.database.data.currency.base.IShapedRecipe;
import com.robertx22.mine_and_slash.vanilla_mc.items.SimpleMatItem;
import net.minecraft.advancement.criterion.EnchantedItemCriterion;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.server.RecipesProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

public class SlashRecipeProvider extends RecipesProvider {

    public SlashRecipeProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    protected void generate(Consumer<RecipeJsonProvider> c) {

        shaped(SimpleMatItem.INFUSED_IRON)
            .input('#', ModRegistry.ITEMS.MAGIC_ESSENCE.get())
            .input('i', Items.IRON_INGOT)
            .pattern(" # ")
            .pattern("#i#")
            .pattern(" # ")
            .criterion("player_level", EnchantedItemCriterion.Conditions.any())
            .offerTo(c);

        shaped(SimpleMatItem.GOLDEN_ORB)
            .input('#', ModRegistry.ITEMS.RARE_MAGIC_ESSENCE.get())
            .input('i', Items.GOLD_INGOT)
            .pattern(" # ")
            .pattern("#i#")
            .pattern(" # ")
            .criterion("player_level", EnchantedItemCriterion.Conditions.any())
            .offerTo(c);

        shaped(SimpleMatItem.CRYSTALLIZED_ESSENCE)
            .input('#', ModRegistry.ITEMS.RARE_MAGIC_ESSENCE.get())
            .input('i', Items.DIAMOND)
            .pattern(" # ")
            .pattern("#i#")
            .pattern(" # ")
            .criterion("player_level", EnchantedItemCriterion.Conditions.any())
            .offerTo(c);

        shaped(SimpleMatItem.MYTHIC_ESSENCE)
            .input('#', ModRegistry.ITEMS.RARE_MAGIC_ESSENCE.get())
            .input('b', Items.BOWL)
            .pattern("###")
            .pattern("###")
            .pattern(" b ")
            .criterion("player_level", EnchantedItemCriterion.Conditions.any())
            .offerTo(c);

        ForgeRegistries.ITEMS.forEach(x -> {
            if (x instanceof IShapedRecipe) {
                IShapedRecipe rec = (IShapedRecipe) x;
                rec.getRecipe()
                    .offerTo(c);
            }
        });

    }

    private ShapedRecipeJsonFactory shaped(ItemConvertible pro) {
        return ShapedRecipeJsonFactory.create(pro, 1);
    }
}