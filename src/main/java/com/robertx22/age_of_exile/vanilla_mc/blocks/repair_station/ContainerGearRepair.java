package com.robertx22.age_of_exile.vanilla_mc.blocks.repair_station;

import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.FuelSlot;
import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.OutputSlot;
import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.RepairSlot;
import com.robertx22.library_of_exile.tile_bases.BaseTileContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.BlockPos;

public class ContainerGearRepair extends BaseTileContainer {

    Inventory tile;

    public ContainerGearRepair(int num, PlayerInventory invPlayer, Inventory inv,
                               BlockPos pos) {
        super(TileGearRepair.TOTAL_SLOTS_COUNT, null, num, invPlayer);

        this.pos = pos;
        this.tile = inv;

        final int SLOT_X_SPACING = 18;
        final int SLOT_Y_SPACING = 18;

        final int FUEL_SLOTS_XPOS = 80; // 53;
        final int FUEL_SLOTS_YPOS = 96;
        // Add the tile fuel slots
        for (int x = 0; x < TileGearRepair.FUEL_SLOTS.size(); x++) {
            addSlot(new FuelSlot(inv, TileGearRepair.FUEL_SLOTS.get(x), FUEL_SLOTS_XPOS + SLOT_X_SPACING * x, FUEL_SLOTS_YPOS));
        }

        final int INPUT_SLOTS_XPOS = 26;
        final int INPUT_SLOTS_YPOS = 24;
        // Add the tile input slots
        for (int y = 0; y < TileGearRepair.INPUT_SLOTS.size(); y++) {
            addSlot(new RepairSlot(inv, TileGearRepair.INPUT_SLOTS.get(y), INPUT_SLOTS_XPOS, INPUT_SLOTS_YPOS + SLOT_Y_SPACING * y));
        }

        final int OUTPUT_SLOTS_XPOS = 134;
        final int OUTPUT_SLOTS_YPOS = 24;
        // Add the tile output slots
        for (int y = 0; y < TileGearRepair.OUTPUT_SLOTS.size(); y++) {
            addSlot(new OutputSlot(inv, TileGearRepair.OUTPUT_SLOTS.get(y), OUTPUT_SLOTS_XPOS, OUTPUT_SLOTS_YPOS + SLOT_Y_SPACING * y));
        }

    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return tile.canPlayerUse(player);
    }
}