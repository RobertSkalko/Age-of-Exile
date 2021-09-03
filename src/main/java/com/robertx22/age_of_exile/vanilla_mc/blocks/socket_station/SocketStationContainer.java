package com.robertx22.age_of_exile.vanilla_mc.blocks.socket_station;

import com.robertx22.age_of_exile.vanilla_mc.blocks.BaseTileContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class SocketStationContainer extends BaseTileContainer {

    Inventory tile;

    public SocketStationContainer(int i, PlayerInventory invPlayer, Inventory inventory,
                                  BlockPos pos) {

        super(SocketStationBlockEntity.SLOT_COUNT, null, i, invPlayer);
        this.tile = inventory;
        this.pos = pos;
        int count = 0;

        for (int runeslot : SocketStationBlockEntity.RUNE_SLOTS) {
            int spacing = runeslot > 2 ? 31 : 0;
            addSlot(new Slot(inventory, count++, 111 + spacing + runeslot * 21, 27));
        }

        addSlot(new Slot(inventory, count++, 179, 26));

        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 9; y++) {
                int xpos = PLAYER_INVENTORY_XPOS() + y * 18;
                int ypos = PLAYER_INVENTORY_YPOS() - 40 + x * 18;
                addSlot(new Slot(inventory, count++, xpos, ypos));
            }
        }

    }

    @Override
    public int PLAYER_INVENTORY_XPOS() {
        return 108;
    }

    @Override
    public int PLAYER_INVENTORY_YPOS() {
        return 113;
    }

    @Override
    protected int HOTBAR_YPOS() {
        return 171;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return tile.canPlayerUse(player);
    }

}