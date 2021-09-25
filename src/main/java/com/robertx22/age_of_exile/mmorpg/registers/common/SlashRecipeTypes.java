package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class SlashRecipeTypes {

    public static void init() {

    }

    static <T extends IRecipe<?>> IRecipeType<T> register(final String string) {
        return Registry.register(Registry.RECIPE_TYPE, (ResourceLocation) SlashRef.id(string), new IRecipeType<T>() {
            @Override
            public String toString() {
                return string;
            }
        });
    }
}
