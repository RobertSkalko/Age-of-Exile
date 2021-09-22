package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.player_skills.ingredient.IngredientItem;

public class IngredientItems {

    public static void init() {

    }

    public static RegObj<IngredientItem> GUARDIAN_SCALES = of("guardian_scales");
    public static RegObj<IngredientItem> FRESH_HEART = of("fresh_heart");
    public static RegObj<IngredientItem> SLIME_BOLUS = of("slime_bolus");

    static RegObj<IngredientItem> of(String id) {
        RegObj<IngredientItem> def = Def.item("ingredient/" + id, () -> new IngredientItem(id));
        return def;
    }

}
