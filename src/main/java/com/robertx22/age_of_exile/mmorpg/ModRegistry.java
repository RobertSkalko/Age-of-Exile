package com.robertx22.age_of_exile.mmorpg;

import com.robertx22.age_of_exile.mixin_methods.AddMobSpawns;
import com.robertx22.age_of_exile.mmorpg.registers.common.*;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.BaseGearTypeItemRegister;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.CurrencyItemRegistrator;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.GearMaterialRegister;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.MiscItemsRegistrator;

public class ModRegistry {

    public static MiscItemsRegistrator MISC_ITEMS;
    public static BaseGearTypeItemRegister GEAR_ITEMS;
    public static CurrencyItemRegistrator CURRENCIES;
    public static ModBlocks BLOCKS;
    public static ModContainers CONTAINERS;
    public static ModSounds SOUNDS;
    public static ModEntities ENTITIES;
    public static ModBlockEntities BLOCK_ENTITIES;
    public static ModParticles PARTICLES;
    public static ComponentRegisters COMPONENTS;
    public static GearMaterialRegister GEAR_MATERIALS;

    public static void init() {

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

        AddMobSpawns.SPAWNS = new AddMobSpawns.Spawns();

    }

}

