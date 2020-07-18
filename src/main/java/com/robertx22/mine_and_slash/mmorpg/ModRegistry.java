package com.robertx22.mine_and_slash.mmorpg;

import com.robertx22.mine_and_slash.mmorpg.registers.common.*;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.BaseGearTypeItemRegister;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.CurrencyItemRegistrator;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.MiscItemsRegistrator;

public class ModRegistry {

    public static MiscItemsRegistrator MISC_ITEMS;
    public static BaseGearTypeItemRegister GEAR_ITEMS;
    public static CurrencyItemRegistrator CURRENCIES;
    public static ModBlocks BLOCKS;
    public static Containers CONTAINERS;
    public static ModSounds SOUNDS;
    public static EntityRegister ENTITIES;
    public static BlockEntities BLOCK_ENTITIES;

    public static void init() {

        MISC_ITEMS = new MiscItemsRegistrator();
        GEAR_ITEMS = new BaseGearTypeItemRegister();
        CURRENCIES = new CurrencyItemRegistrator();
        BLOCKS = new ModBlocks();
        CONTAINERS = new Containers();
        SOUNDS = new ModSounds();
        ENTITIES = new EntityRegister();
        BLOCK_ENTITIES = new BlockEntities();

    }
}

