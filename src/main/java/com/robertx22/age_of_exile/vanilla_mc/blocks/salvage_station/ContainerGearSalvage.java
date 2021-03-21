package com.robertx22.age_of_exile.vanilla_mc.blocks.salvage_station;

import com.robertx22.age_of_exile.vanilla_mc.blocks.BaseTileContainer;
import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.OutputSlot;
import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.SalvageSlot;
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

        final int INPUT_SLOTS_XPOS = 44;
        final int INPUT_SLOTS_YPOS = 9;
        // Add the tile input slots
        for (int y = 0; y < TileGearSalvage.INPUT_SLOTS.size(); y++) {
            addSlot(new SalvageSlot(inventory, TileGearSalvage.INPUT_SLOTS.get(y), INPUT_SLOTS_XPOS + SLOT_Y_SPACING * y, INPUT_SLOTS_YPOS));
        }

        final int OUTPUT_SLOTS_XPOS = 8;
        final int OUTPUT_SLOTS_YPOS = 63;
        // Add the tile output slots

        int i = 0;
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) { // hard coded numbers
                addSlot(new OutputSlot(inventory, TileGearSalvage.OUTPUT_SLOTS.get(i), OUTPUT_SLOTS_XPOS + SLOT_Y_SPACING * x, OUTPUT_SLOTS_YPOS + SLOT_Y_SPACING * y));

                i++;
            }
        }

    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return tile.canPlayerUse(player);
    }
}