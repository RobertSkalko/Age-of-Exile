package com.robertx22.age_of_exile.database.data.salvage_recipes;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemIngredient {

    public List<ItemRequirement> req = new ArrayList();

    public boolean matches(ItemStack stack) {
        return req.stream()
            .allMatch(x -> x.matches(stack));
    }

    public static ItemIngredient of(ItemRequirement... req) {
        ItemIngredient i = new ItemIngredient();
        i.req.addAll(Arrays.asList(req));
        return i;
    }
}
