package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.player_skills.ingredient.CraftedFoodItem;

public class CraftedConsumableItems {

    public static void init() {

    }

    public static RegObj<CraftedFoodItem> FOOD = Def.item(() -> new CraftedFoodItem());

}
