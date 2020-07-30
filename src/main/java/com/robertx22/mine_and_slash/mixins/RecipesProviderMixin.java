package com.robertx22.mine_and_slash.mixins;

import com.robertx22.mine_and_slash.database.data.currency.base.IShapedRecipe;
import net.minecraft.data.server.RecipesProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import java.util.function.Consumer;

@Mixin(RecipesProvider.class)
public class RecipesProviderMixin {
    @Inject(method = "generate", at = @At(value = "HEAD"), cancellable = true)
    private void hookGenerate(Consumer<RecipeJsonProvider> consumer) {
        for (Item item : Registry.ITEM) {
            if (item instanceof IShapedRecipe) {
                IShapedRecipe ir = (IShapedRecipe) item;
                ir.getRecipe()
                    .offerTo(consumer);

            }
        }
    }
}
