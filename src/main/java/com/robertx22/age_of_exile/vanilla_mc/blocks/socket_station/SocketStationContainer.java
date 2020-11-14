package com.robertx22.age_of_exile.vanilla_mc.blocks.socket_station;

import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.AnyCurrencyEffectItemSlot;
import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.AnyItemNotACurrencyEffectSlot;
import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.NoTouchSlot;
import com.robertx22.library_of_exile.tile_bases.BaseTileContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.BlockPos;

public class SocketStationContainer extends BaseTileContainer {

    public static final int SOCKET_SLOTS_COUNT = 2;
    public static final int RUNEWORD_SLOTS = 27;

    public static final int TOTAL_SLOTS = RUNEWORD_SLOTS + SOCKET_SLOTS_COUNT;

    Inventory tile;

    public SocketStationContainer(int i, PlayerInventory invPlayer, Inventory inventory,
                                  BlockPos pos) {

        super(TOTAL_SLOTS, null, i, invPlayer);
        this.tile = inventory;
        this.pos = pos;
        int count = 0;

        addSlot(new AnyItemNotACurrencyEffectSlot(inventory, count++, 30, 25));
        addSlot(new AnyCurrencyEffectItemSlot(inventory, count++, 71, 25));

        // runeword show slots
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                int xpos = 8 + x * 18;
                int ypos = 50 + y * 18;
                this.addSlot(new NoTouchSlot(inventory, count++, xpos, ypos));
            }
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return tile.canPlayerUse(player);
    }

}