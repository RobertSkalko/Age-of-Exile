package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.player_skills.ingredient.items.IngredientItem;

public class IngredientItems {

    public static void init() {

    }

    //specials
    public static RegObj<IngredientItem> WISHING_BOWL = of("wishing_bowl", "Wishing Bowl");
    public static RegObj<IngredientItem> POT_OF_GREED = of("pot_of_greed", "Pot of Greed");
    public static RegObj<IngredientItem> SOUL_MATRIX = of("soul_matrix", "Soul Matrix");

    //normal
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
    public static RegObj<IngredientItem> CRYSTAL_BERYL = of("crystal_beryl", "Crystal Beryl");
    public static RegObj<IngredientItem> RED_CORAL = of("red_coral", "Red Coral");
    public static RegObj<IngredientItem> ECTOPLASM = of("ectoplasm", "Ectoplasm");
    public static RegObj<IngredientItem> BLOOD_ROOT = of("blootroot", "Blood Root");
    public static RegObj<IngredientItem> CRYSTAL_AMETHYST = of("crystal_amethyst", "Crystal Amethyst");
    public static RegObj<IngredientItem> ENDERMAN_HEART = of("enderman_heart", "Enderman Heart");
    public static RegObj<IngredientItem> FIRE_STONE = of("fire_stone", "Fire Stone");
    public static RegObj<IngredientItem> CRYSTAL_BLUE_ROCK = of("crystal_blue_rock", "Crystal Blue Rock");
    public static RegObj<IngredientItem> BOLETE_MUSHROOM = of("bolete_mushroom", "Bolete Mushroom");
    public static RegObj<IngredientItem> MAGIC_LOG = of("magic_log", "Magic Log");
    public static RegObj<IngredientItem> REDSTONE_CRYSTAL = of("redstone_crystal", "Redstone Crystal");

    static RegObj<IngredientItem> of(String id, String locname) {
        RegObj<IngredientItem> def = Def.item("ingredient/" + id, () -> new IngredientItem(id, locname));
        return def;
    }

}
