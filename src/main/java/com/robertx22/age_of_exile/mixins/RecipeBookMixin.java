package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.database.data.runewords.RuneWordItem;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.vanilla_mc.blocks.MNSCraftingTableBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.client.util.ClientRecipeBook;
import net.minecraft.client.util.RecipeBookCategories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.stream.Collectors;

@Mixin(ClientRecipeBook.class)
public class RecipeBookMixin {

    @Inject(method = "getCategory", cancellable = true, at = @At(value = "HEAD"))
    private static void ssddsdd2525(IRecipe<?> recipe, CallbackInfoReturnable<RecipeBookCategories> cir) {
        if (!MNSCraftingTableBlock.isPlayerUsing(Minecraft.getInstance().player)) {
            return;
        }
        ItemStack item = recipe.getResultItem();
        if (item.getItem() instanceof RuneWordItem) {
            cir.setReturnValue(RecipeBookCategories.CRAFTING_EQUIPMENT);
        }
    }

    @Inject(method = "getCollection", cancellable = true, at = @At(value = "HEAD"))
    private void cantargetmixin(RecipeBookCategories cat, CallbackInfoReturnable<List<RecipeList>> cir) {
        try {

            if (!MNSCraftingTableBlock.isPlayerUsing(Minecraft.getInstance().player)) {
                return;
            }

            ClientRecipeBookAccessor acc = (ClientRecipeBookAccessor) this;

            if (cat == RecipeBookCategories.CRAFTING_SEARCH) {

                List<RecipeList> list = acc.getCollectionsByTab()
                    .get(RecipeBookCategories.CRAFTING_SEARCH);

                list = list.stream()
                    .filter(e -> e.getRecipes()
                        .stream()
                        .allMatch(x -> x.getId()
                            .getNamespace()
                            .equals(SlashRef.MODID)))
                    .collect(Collectors.toList());
                if (!list.isEmpty()) {
                    cir.setReturnValue(list);
                }
            }
            if (cat == RecipeBookCategories.CRAFTING_EQUIPMENT) {

                List<RecipeList> list = acc.getCollectionsByTab()
                    .get(RecipeBookCategories.CRAFTING_SEARCH);

                list = list.stream()
                    .filter(e -> e.getRecipes()
                        .stream()
                        .allMatch(x -> x.getResultItem()
                            .getItem() instanceof RuneWordItem))
                    .collect(Collectors.toList());

                if (!list.isEmpty()) {
                    cir.setReturnValue(list);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
