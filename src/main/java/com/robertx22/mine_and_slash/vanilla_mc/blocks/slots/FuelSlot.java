package com.robertx22.mine_and_slash.vanilla_mc.blocks.slots;

import com.robertx22.mine_and_slash.mmorpg.ModRegistry;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import java.util.HashMap;

public class FuelSlot extends Slot {
    public FuelSlot(Inventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return FUEL_VALUES.get(stack.getItem()) != null;
    }

    public static HashMap<Item, Integer> FUEL_VALUES = new HashMap<Item, Integer>() {
        {
            {

                put(Items.DIAMOND, 300);
                put(Items.GOLD_INGOT, 80);
                put(Items.IRON_INGOT, 20);
                put(Items.EMERALD, 200);
                put(Items.REDSTONE, 3);

                put(ModRegistry.MISC_ITEMS.MAGIC_ESSENCE, 50);
                put(ModRegistry.MISC_ITEMS.RARE_MAGIC_ESSENCE, 500);

            }
        }
    };

}