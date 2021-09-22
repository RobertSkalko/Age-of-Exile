package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.player_skills.ingredient.items.IngredientItem;

public class IngredientItems {

    public static void init() {

    }

    public static RegObj<IngredientItem> GUARDIAN_SCALES = of("guardian_scales", "Guardian Scales");
    public static RegObj<IngredientItem> FRESH_HEART = of("fresh_heart", "Fresh Heart");
    public static RegObj<IngredientItem> SLIME_BOLUS = of("slime_bolus", "Slime Bolus");
    public static RegObj<IngredientItem> ENDER_HEART = of("ender_heart", "Ender Heart");
    public static RegObj<IngredientItem> MOONSTONE = of("moonstone", "Moonstone");
    public static RegObj<IngredientItem> WISDOM_FRUIT = of("wisdom_fruit", "Wisdom Fruit");
    public static RegObj<IngredientItem> BRYONY_ROOT = of("bryony_root", "Bryony Root");
    public static RegObj<IngredientItem> LOTUS_PETALS = of("lootus_petals", "Lotus Petals");
    public static RegObj<IngredientItem> SAGE_FLOWER = of("sage_flower", "Sage Flower");
    public static RegObj<IngredientItem> FRIGID_RUNESTONE = of("frigid_runestone", "Frigid Runestone");
    public static RegObj<IngredientItem> BLACKPEARL = of("blackpearl", "Black Pearl");
    public static RegObj<IngredientItem> EARTH_CRYSTAL = of("earth_crystal", "Earth Crystal");
    public static RegObj<IngredientItem> DARK_STONE = of("dark_stone", "Dark Stone");
    public static RegObj<IngredientItem> YARROW_FLOWER = of("yarrow_flower", "Yarrow Flower");
    public static RegObj<IngredientItem> HEMLOCK = of("hemlock", "Hemlock");

    static RegObj<IngredientItem> of(String id, String locname) {
        RegObj<IngredientItem> def = Def.item("ingredient/" + id, () -> new IngredientItem(id, locname));
        return def;
    }

}
