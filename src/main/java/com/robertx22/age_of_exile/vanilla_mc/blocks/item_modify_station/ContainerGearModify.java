package com.robertx22.age_of_exile.vanilla_mc.blocks.item_modify_station;

import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.BaseTileContainer;
import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.AnyCurrencyEffectItemSlot;
import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.AnyItemNotACurrencyEffectSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class ContainerGearModify extends BaseTileContainer {

    private final int HOTBAR_SLOT_COUNT = 9;
    private final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;

    public static final int MODIFY_SLOTS_COUNT = 3;

    Inventory tile;

    public ContainerGearModify(int i, PlayerInventory invPlayer, Inventory inventory,
                               BlockPos pos) {

        super(MODIFY_SLOTS_COUNT, null, i);

        this.tile = inventory;

        this.pos = pos;

        final int PLAYER_INVENTORY_XPOS = 8;
        final int PLAYER_INVENTORY_YPOS = 125;

        final int SLOT_X_SPACING = 18;
        final int SLOT_Y_SPACING = 18;
        final int HOTBAR_YPOS = 183;
        // Add the players hotbar to the gui - the [xpos, ypos] location of each item
        for (int x = 0; x < HOTBAR_SLOT_COUNT; x++) {
            int slotNumber = x;
            addSlot(new Slot(invPlayer, slotNumber, PLAYER_INVENTORY_XPOS + SLOT_X_SPACING * x, HOTBAR_YPOS));
        }
        // Add the rest of the players inventory to the gui
        for (int y = 0; y < PLAYER_INVENTORY_ROW_COUNT; y++) {
            for (int x = 0; x < PLAYER_INVENTORY_COLUMN_COUNT; x++) {
                int slotNumber = HOTBAR_SLOT_COUNT + y * PLAYER_INVENTORY_COLUMN_COUNT + x;
                int xpos = PLAYER_INVENTORY_XPOS + x * SLOT_X_SPACING;
                int ypos = PLAYER_INVENTORY_YPOS + y * SLOT_Y_SPACING;
                addSlot(new Slot(invPlayer, slotNumber, xpos, ypos));
            }
        }

        int count = 0;

        addSlot(new AnyItemNotACurrencyEffectSlot(inventory, count++, 80, 59));
        addSlot(new AnyCurrencyEffectItemSlot(inventory, count++, 80, 32));

        //TOOD
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return tile.canPlayerUse(player);
    }

}