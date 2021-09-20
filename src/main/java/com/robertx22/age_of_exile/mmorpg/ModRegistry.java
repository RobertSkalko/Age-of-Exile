package com.robertx22.age_of_exile.mmorpg;

import com.robertx22.age_of_exile.mmorpg.registers.common.*;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.*;

public class ModRegistry {

    public static BackpackUpgradesRegister BACKPACK_UPGRADES;
    public static ModRecipeTypes RECIPE_TYPES;
    public static MiscItemsRegistrator MISC_ITEMS;
    public static BaseGearTypeItemRegister GEAR_ITEMS;
    public static CurrencyItemRegistrator CURRENCIES;
    public static FoodItemRegister FOOD_ITEMS;
    public static GemItemRegister GEMS;
    public static RuneItemRegister RUNES;
    public static SlashBlocks BLOCKS;
    public static ModContainers CONTAINERS;
    public static ModSounds SOUNDS;
    public static SlashEntities ENTITIES;
    public static SlashBlockEntities BLOCK_ENTITIES;
    public static ModParticles PARTICLES;
    public static ComponentRegisters COMPONENTS;
    public static GearMaterialRegister GEAR_MATERIALS;
    public static PotionRegister POTIONS;
    public static ModRecipeSerializers RECIPE_SER;
    public static InscribingItemRegister INSCRIBING;
    public static AlchemyItemRegister ALCHEMY;
    public static TierItemsRegister TIERED;

    public static void init() {

        RECIPE_TYPES = new ModRecipeTypes();
        RECIPE_SER = new ModRecipeSerializers();
        POTIONS = new PotionRegister();
        BLOCKS = new SlashBlocks(); // blocks first, cus items are made from blocks
        MISC_ITEMS = new MiscItemsRegistrator();
        CURRENCIES = new CurrencyItemRegistrator();
        CONTAINERS = new ModContainers();
        SOUNDS = new ModSounds();
        ENTITIES = new SlashEntities();
        BLOCK_ENTITIES = new SlashBlockEntities();
        PARTICLES = new ModParticles();
        COMPONENTS = new ComponentRegisters();
        GEAR_MATERIALS = new GearMaterialRegister();
        GEMS = new GemItemRegister();
        RUNES = new RuneItemRegister();
        GEAR_ITEMS = new BaseGearTypeItemRegister();
        FOOD_ITEMS = new FoodItemRegister();
        INSCRIBING = new InscribingItemRegister();
        ALCHEMY = new AlchemyItemRegister();
        TIERED = new TierItemsRegister();
        BACKPACK_UPGRADES = new BackpackUpgradesRegister();

    }

}

