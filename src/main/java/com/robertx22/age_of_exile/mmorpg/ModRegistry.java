package com.robertx22.age_of_exile.mmorpg;

import com.robertx22.age_of_exile.mixin_methods.AddMobSpawns;
import com.robertx22.age_of_exile.mmorpg.registers.common.*;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.*;

public class ModRegistry {

    public static MiscItemsRegistrator MISC_ITEMS;
    public static BaseGearTypeItemRegister GEAR_ITEMS;
    public static CurrencyItemRegistrator CURRENCIES;
    public static FoodItemRegister FOOD_ITEMS;
    public static GemItemRegister GEMS;
    public static RuneItemRegister RUNES;
    public static ModBlocks BLOCKS;
    public static ModContainers CONTAINERS;
    public static ModSounds SOUNDS;
    public static ModEntities ENTITIES;
    public static ModBlockEntities BLOCK_ENTITIES;
    public static ModParticles PARTICLES;
    public static ComponentRegisters COMPONENTS;
    public static GearMaterialRegister GEAR_MATERIALS;
    public static PotionRegister POTIONS;
    public static UniqueGearItemRegister UNIQUE_GEARS;
    public static ModRecipeSerializers RECIPE_SER;
    public static RepairKitsRegister REPAIR_KITS;
    public static TabletItemRegister TABLETS;
    public static AlchemyItemRegister ALCHEMY;

    public static void init() {

        RECIPE_SER = new ModRecipeSerializers();
        REPAIR_KITS = new RepairKitsRegister();
        UNIQUE_GEARS = new UniqueGearItemRegister();
        POTIONS = new PotionRegister();
        BLOCKS = new ModBlocks(); // blocks first, cus items are made from blocks
        MISC_ITEMS = new MiscItemsRegistrator();
        CURRENCIES = new CurrencyItemRegistrator();
        CONTAINERS = new ModContainers();
        SOUNDS = new ModSounds();
        ENTITIES = new ModEntities();
        BLOCK_ENTITIES = new ModBlockEntities();
        PARTICLES = new ModParticles();
        COMPONENTS = new ComponentRegisters();
        GEAR_MATERIALS = new GearMaterialRegister();
        GEMS = new GemItemRegister();
        RUNES = new RuneItemRegister();
        GEAR_ITEMS = new BaseGearTypeItemRegister();
        FOOD_ITEMS = new FoodItemRegister();
        TABLETS = new TabletItemRegister();
        ALCHEMY = new AlchemyItemRegister();

        AddMobSpawns.SPAWNS = new AddMobSpawns.Spawns();
    }

}

