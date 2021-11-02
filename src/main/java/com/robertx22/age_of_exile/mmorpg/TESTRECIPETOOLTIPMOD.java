package com.robertx22.age_of_exile.mmorpg;

import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.library_of_exile.main.ForgeEvents;
import com.robertx22.library_of_exile.utils.Watch;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class TESTRECIPETOOLTIPMOD {

    // could be dumb or really niche idea. idk. Useful at least in my case though..

    public static HashMap<Item, Set<ResourceLocation>> ingredientAndPossiblecraftsMap = new HashMap<>();

    static int MAX_ON_TOOLTIP = 5;

    public static void registerEvent() {
        ForgeEvents.registerForgeEvent(ItemTooltipEvent.class, event -> {

            if (true) {
                return; // seems kinda useless
            }

            if (event.getPlayer().tickCount % 20 == 0) {
                if (ingredientAndPossiblecraftsMap.isEmpty()) {
                    onRecipesLoaded(event.getPlayer().level);
                }
            }

            try {
                Item item = event.getItemStack()
                    .getItem();

                if (ingredientAndPossiblecraftsMap.containsKey(item)) {
                    event.getToolTip()
                        .add(new StringTextComponent("Can be used to craft:"));
                    ingredientAndPossiblecraftsMap.get(item)
                        .forEach(x -> {

                            Optional<? extends IRecipe<?>> recipe = event.getPlayer().level.getRecipeManager()
                                .byKey(x);

                            event.getToolTip()
                                .add(recipe.get()
                                    .getResultItem()
                                    .getDisplayName());

                        });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    // must be client
    private static void onRecipesLoaded(World world) {

        Watch watch = new Watch();

        ingredientAndPossiblecraftsMap.clear();

        List<Item> items = new ArrayList<>();
        items.add(SlashItems.SOURCE_OF_STRENGTH.get());

        ForgeRegistries.ITEMS.forEach(x -> {
            if (x instanceof ICurrencyItemEffect) {
                items.add(x);
            }
        });

        List<ItemStack> itemsThatShouldHaveTooltipsShowingWhatCanBeCraftedOutOfThem = new ArrayList<>();
        items.forEach(x -> itemsThatShouldHaveTooltipsShowingWhatCanBeCraftedOutOfThem.add(new ItemStack(x)));

        Collection<IRecipe<?>> recipes = world
            .getRecipeManager()
            .getRecipes();

        if (recipes.isEmpty()) {
            throw new RuntimeException("Called method before recipes were loaded");
        }

        itemsThatShouldHaveTooltipsShowingWhatCanBeCraftedOutOfThem.forEach(s -> {
            recipes
                .forEach(x -> {
                    if (x.getIngredients()
                        .stream()
                        .anyMatch(e -> e.test(s))) {

                        if (!ingredientAndPossiblecraftsMap.containsKey(s.getItem())) {
                            ingredientAndPossiblecraftsMap.put(s.getItem(), new HashSet<>());
                        }

                        if (ingredientAndPossiblecraftsMap.get(s.getItem())
                            .size() < MAX_ON_TOOLTIP) {
                            ingredientAndPossiblecraftsMap.get(s.getItem())
                                .add(x.getId());
                        }
                    }
                });
        });

        watch.print("Setting up crafting tooltips");
    }
}
