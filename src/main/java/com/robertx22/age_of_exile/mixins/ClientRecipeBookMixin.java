package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.mmorpg.Ref;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.recipe.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.CancellationException;

@Mixin(ClientRecipeBook.class)
public class ClientRecipeBookMixin {

    @Inject(method = "getGroupForRecipe(Lnet/minecraft/recipe/Recipe;)Lnet/minecraft/client/recipebook/RecipeBookGroup;", at = @At(value = "HEAD"), cancellable = true)
    private static void stopUnknownRecipeSpam(Recipe<?> recipe, CallbackInfoReturnable<RecipeBookGroup> ci) {
        try {
            if (recipe.getId()
                .getNamespace()
                .equals(Ref.MODID)) {
                ci.setReturnValue(RecipeBookGroup.UNKNOWN);
            }
        } catch (CancellationException e) {
            e.printStackTrace();
        }
    }
}
