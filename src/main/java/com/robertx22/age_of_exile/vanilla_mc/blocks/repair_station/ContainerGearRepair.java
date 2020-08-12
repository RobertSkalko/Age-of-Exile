package com.robertx22.age_of_exile.vanilla_mc.blocks.repair_station;

import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.BaseTileContainer;
import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.CapacitorSlot;
import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.FuelSlot;
import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.OutputSlot;
import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.RepairSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class ContainerGearRepair extends BaseTileContainer {

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;

    public static final int FUEL_SLOTS_COUNT = TileGearRepair.FUEL_SLOTS_COUNT;
    public static final int INPUT_SLOTS_COUNT = 5;
    public static final int OUTPUT_SLOTS_COUNT = 5;
    public static final int FURNACE_SLOTS_COUNT = FUEL_SLOTS_COUNT + INPUT_SLOTS_COUNT + OUTPUT_SLOTS_COUNT + 1;

    // slot index is the unique index for all slots in this container i.e. 0 - 35
    // for invPlayer then 36 - 49 for tileInventoryFurnace
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int FIRST_FUEL_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    private static final int FIRST_INPUT_SLOT_INDEX = FIRST_FUEL_SLOT_INDEX + FUEL_SLOTS_COUNT;
    private static final int FIRST_OUTPUT_SLOT_INDEX = FIRST_INPUT_SLOT_INDEX + INPUT_SLOTS_COUNT;
    private static final int FIRST_CAPACITOR_SLOT_INDEX = FIRST_OUTPUT_SLOT_INDEX + OUTPUT_SLOTS_COUNT;

    // slot number is the slot number within each component; i.e. invPlayer slots 0
    // - 35, and tileInventoryFurnace slots 0 - 14
    private static final int FIRST_FUEL_SLOT_NUMBER = 0;
    private static final int FIRST_INPUT_SLOT_NUMBER = FIRST_FUEL_SLOT_NUMBER + FUEL_SLOTS_COUNT;
    private static final int FIRST_OUTPUT_SLOT_NUMBER = FIRST_INPUT_SLOT_NUMBER + INPUT_SLOTS_COUNT;
    private static final int FIRST_CAPACITOR_SLOT_NUMBER = FIRST_OUTPUT_SLOT_NUMBER + OUTPUT_SLOTS_COUNT;
    Inventory tile;

    public ContainerGearRepair(int num, PlayerInventory invPlayer, Inventory inv,
                               BlockPos pos) {
        super(FURNACE_SLOTS_COUNT, null, num);

        this.pos = pos;
        this.tile = inv;

        final int SLOT_X_SPACING = 18;
        final int SLOT_Y_SPACING = 18;
        final int HOTBAR_XPOS = 8;
        final int HOTBAR_YPOS = 183;
        // Add the players hotbar to the gui - the [xpos, ypos] location of each item
        for (int x = 0; x < HOTBAR_SLOT_COUNT; x++) {
            int slotNumber = x;
            addSlot(new Slot(invPlayer, slotNumber, HOTBAR_XPOS + SLOT_X_SPACING * x, HOTBAR_YPOS));
        }

        final int PLAYER_INVENTORY_XPOS = 8;
        final int PLAYER_INVENTORY_YPOS = 125;
        // Add the rest of the players inventory to the gui
        for (int y = 0; y < PLAYER_INVENTORY_ROW_COUNT; y++) {
            for (int x = 0; x < PLAYER_INVENTORY_COLUMN_COUNT; x++) {
                int slotNumber = HOTBAR_SLOT_COUNT + y * PLAYER_INVENTORY_COLUMN_COUNT + x;
                int xpos = PLAYER_INVENTORY_XPOS + x * SLOT_X_SPACING;
                int ypos = PLAYER_INVENTORY_YPOS + y * SLOT_Y_SPACING;
                addSlot(new Slot(invPlayer, slotNumber, xpos, ypos));
            }
        }

        final int FUEL_SLOTS_XPOS = 80; // 53;
        final int FUEL_SLOTS_YPOS = 96;
        // Add the tile fuel slots
        for (int x = 0; x < FUEL_SLOTS_COUNT; x++) {
            int slotNumber = x + FIRST_FUEL_SLOT_NUMBER;
            addSlot(new FuelSlot(inv, slotNumber, FUEL_SLOTS_XPOS + SLOT_X_SPACING * x, FUEL_SLOTS_YPOS));
        }

        final int INPUT_SLOTS_XPOS = 26;
        final int INPUT_SLOTS_YPOS = 24;
        // Add the tile input slots
        for (int y = 0; y < INPUT_SLOTS_COUNT; y++) {
            int slotNumber = y + FIRST_INPUT_SLOT_NUMBER;
            addSlot(new RepairSlot(inv, slotNumber, INPUT_SLOTS_XPOS, INPUT_SLOTS_YPOS + SLOT_Y_SPACING * y));
        }

        final int OUTPUT_SLOTS_XPOS = 134;
        final int OUTPUT_SLOTS_YPOS = 24;
        // Add the tile output slots
        for (int y = 0; y < OUTPUT_SLOTS_COUNT; y++) {
            int slotNumber = y + FIRST_OUTPUT_SLOT_NUMBER;
            addSlot(new OutputSlot(inv, slotNumber, OUTPUT_SLOTS_XPOS, OUTPUT_SLOTS_YPOS + SLOT_Y_SPACING * y));
        }

        final int CAPACITOR_SLOTS_XPOS = 80; // 53;
        final int CAPACITOR_SLOTS_YPOS = 24;
        // Add the tile capacitor slot
        for (int x = 0; x < 1; x++) {
            int slotNumber = x + FIRST_CAPACITOR_SLOT_NUMBER;
            addSlot(new CapacitorSlot(inv, slotNumber, CAPACITOR_SLOTS_XPOS + SLOT_X_SPACING * x, CAPACITOR_SLOTS_YPOS));
        }

    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return tile.canPlayerUse(player);
    }
}