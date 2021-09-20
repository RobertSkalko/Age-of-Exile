package com.robertx22.age_of_exile.mmorpg;

import com.robertx22.age_of_exile.mmorpg.registers.common.ComponentRegisters;
import com.robertx22.age_of_exile.mmorpg.registers.common.ModParticles;
import com.robertx22.age_of_exile.mmorpg.registers.common.ModRecipeTypes;
import com.robertx22.age_of_exile.mmorpg.registers.common.PotionRegister;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.*;

public class ModRegistry {

    public static BackpackUpgradesRegister BACKPACK_UPGRADES;
    public static ModRecipeTypes RECIPE_TYPES;
    public static CurrencyItemRegistrator CURRENCIES;
    public static FoodItemRegister FOOD_ITEMS;
    public static ModParticles PARTICLES;
    public static ComponentRegisters COMPONENTS;
    public static PotionRegister POTIONS;
    public static AlchemyItemRegister ALCHEMY;
    public static ProfessionItems TIERED;

    public static void init() {

        RECIPE_TYPES = new ModRecipeTypes();
        POTIONS = new PotionRegister();
        CURRENCIES = new CurrencyItemRegistrator();
        COMPONENTS = new ComponentRegisters();
        FOOD_ITEMS = new FoodItemRegister();
        ALCHEMY = new AlchemyItemRegister();
        TIERED = new ProfessionItems();
        BACKPACK_UPGRADES = new BackpackUpgradesRegister();

    }

}

