package com.robertx22.age_of_exile.vanilla_mc.blocks.repair_station;

import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.AnyItemNotACurrencyEffectSlot;
import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.FuelSlot;
import com.robertx22.library_of_exile.tile_bases.BaseTileContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.BlockPos;

public class ContainerGearRepair extends BaseTileContainer {

    public static final int SLOTS_COUNT = 2;

    Inventory tile;

    public ContainerGearRepair(int i, PlayerInventory invPlayer, Inventory inventory,
                               BlockPos pos) {

        super(SLOTS_COUNT, null, i, invPlayer);
        this.tile = inventory;
        this.pos = pos;
        int count = 0;

        addSlot(new AnyItemNotACurrencyEffectSlot(inventory, count++, 80, 59));
        addSlot(new FuelSlot(inventory, count++, 80, 32));

    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return tile.canPlayerUse(player);
    }
}