package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.player_skills.ingredient.items.AlchemyConsumableItem;
import com.robertx22.age_of_exile.player_skills.ingredient.items.CookingConsumableItem;
import com.robertx22.age_of_exile.player_skills.ingredient.items.InscribingConsumableItem;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;

import java.util.ArrayList;
import java.util.List;

public class CraftedConsumableItems {

    static int FOOD_COUNT = 5;
    static int POTION_COUNT = 4;
    static int SCROLL_COUNT = 4;

    public static List<RegObj<CookingConsumableItem>> FOODS = new ArrayList<>();
    public static List<RegObj<AlchemyConsumableItem>> POTIONS = new ArrayList<>();
    public static List<RegObj<InscribingConsumableItem>> SCROLLS = new ArrayList<>();

    public static void init() {

        for (int i = 1; i <= FOOD_COUNT; i++) {
            FOODS.add(Def.item(id(PlayerSkillEnum.COOKING, i), () -> new CookingConsumableItem()));
        }
        for (int i = 1; i <= POTION_COUNT; i++) {
            POTIONS.add(Def.item(id(PlayerSkillEnum.ALCHEMY, i), () -> new AlchemyConsumableItem()));
        }
        for (int i = 1; i <= SCROLL_COUNT; i++) {
            SCROLLS.add(Def.item(id(PlayerSkillEnum.INSCRIBING, i), () -> new InscribingConsumableItem()));
        }

    }

    static String id(PlayerSkillEnum skill, int num) {
        return "craft_result" + "/" + skill.GUID() + "/" + num;
    }

}
