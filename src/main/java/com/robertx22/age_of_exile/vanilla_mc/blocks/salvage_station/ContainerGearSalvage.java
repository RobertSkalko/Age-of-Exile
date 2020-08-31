package com.robertx22.age_of_exile.vanilla_mc.blocks.salvage_station;

import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.OutputSlot;
import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.SalvageSlot;
import com.robertx22.library_of_exile.tile_bases.BaseTileContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.BlockPos;

public class ContainerGearSalvage extends BaseTileContainer {

    Inventory tile;

    public ContainerGearSalvage(int num, PlayerInventory invPlayer, Inventory inventory,
                                BlockPos pos) {
        super(TileGearSalvage.TOTAL_SLOTS_COUNT, null, num, invPlayer);

        this.pos = pos;
        this.tile = inventory;

        final int SLOT_Y_SPACING = 18;

        final int INPUT_SLOTS_XPOS = 26;
        final int INPUT_SLOTS_YPOS = 24;
        // Add the tile input slots
        for (int y = 0; y < TileGearSalvage.INPUT_SLOTS.size(); y++) {
            addSlot(new SalvageSlot(inventory, TileGearSalvage.INPUT_SLOTS.get(y), INPUT_SLOTS_XPOS, INPUT_SLOTS_YPOS + SLOT_Y_SPACING * y));
        }

        final int OUTPUT_SLOTS_XPOS = 134;
        final int OUTPUT_SLOTS_YPOS = 24;
        // Add the tile output slots
        for (int y = 0; y < TileGearSalvage.OUTPUT_SLOTS.size(); y++) {
            addSlot(new OutputSlot(inventory, TileGearSalvage.OUTPUT_SLOTS.get(y), OUTPUT_SLOTS_XPOS, OUTPUT_SLOTS_YPOS + SLOT_Y_SPACING * y));
        }

    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return tile.canPlayerUse(player);
    }
}