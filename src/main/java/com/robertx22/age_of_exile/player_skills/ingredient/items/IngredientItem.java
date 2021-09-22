package com.robertx22.age_of_exile.player_skills.ingredient.items;

import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;

public class IngredientItem extends AutoItem {

    public String id;
    public String locname;

    public IngredientItem(String id, String locname) {
        super(new Properties());
        this.id = id;
        this.locname = locname;
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }

    @Override
    public String GUID() {
        return id;
    }
}
