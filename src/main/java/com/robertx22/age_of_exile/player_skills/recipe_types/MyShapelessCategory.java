package com.robertx22.age_of_exile.player_skills.recipe_types;

import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.plugin.crafting.DefaultCraftingCategory;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class MyShapelessCategory extends DefaultCraftingCategory {
    Identifier id;
    Item item;

    public MyShapelessCategory(Identifier id, Item item) {

        this.id = id;
        this.item = item;
    }

    @Override
    @NotNull
    public EntryStack getLogo() {
        return EntryStack.create(item);
    }

    @NotNull
    @Override
    public Identifier getIdentifier() {
        return id;
    }

}
