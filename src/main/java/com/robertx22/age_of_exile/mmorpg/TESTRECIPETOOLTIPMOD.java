package com.robertx22.age_of_exile.mmorpg;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public class TESTRECIPETOOLTIPMOD {

    // could be dumb or really niche idea. idk. Useful at least in my case though..

    public static HashMap<Item, Set<ResourceLocation>> ingredientAndPossiblecraftsMap = new HashMap<>();

    static void onRecipesLoaded(ClientWorld world) {

        ingredientAndPossiblecraftsMap.clear();

        List<ItemStack> itemsThatShouldHaveTooltipsShowingWhatCanBeCraftedOutOfThem = new ArrayList<>();

        Collection<IRecipe<?>> recipes = world
            .getRecipeManager()
            .getRecipes();

        itemsThatShouldHaveTooltipsShowingWhatCanBeCraftedOutOfThem.forEach(s -> {
            recipes
                .forEach(x -> {
                    if (x.getIngredients()
                        .stream()
                        .anyMatch(e -> e.test(s))) {

                        if (!ingredientAndPossiblecraftsMap.containsKey(s.getItem())) {
                            ingredientAndPossiblecraftsMap.put(s.getItem(), new HashSet<>());
                        }
                        ingredientAndPossiblecraftsMap.get(s.getItem())
                            .add(x.getId());
                    }
                });
        });
    }
}
