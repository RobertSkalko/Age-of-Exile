package com.robertx22.age_of_exile.player_skills.ingredient;

import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;

public class IngredientItem extends AutoItem {

    public String id;

    public IngredientItem(String id) {
        super(new Properties());
        this.id = id;
    }

    @Override
    public String locNameForLangFile() {
        return "Ingredient";
    }

    @Override
    public String GUID() {
        return id;
    }
}
