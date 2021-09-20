package com.robertx22.age_of_exile.vanilla_mc.blocks.alchemy;

import com.robertx22.age_of_exile.vanilla_mc.blocks.BaseTileContainer;
import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.OutputSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public class AlchemyContainer extends BaseTileContainer {

    IInventory tile;

    public AlchemyContainer(int num, PlayerInventory invPlayer, PacketBuffer packet) {
        this(num, invPlayer, new Inventory(AlchemyTile.totalSlots()), packet.readBlockPos());
    }

    public AlchemyContainer(int i, PlayerInventory invPlayer, IInventory inventory,
                            BlockPos pos) {

        super(AlchemyTile.totalSlots(), null, i, invPlayer);
        this.tile = inventory;
        this.pos = pos;
        int count = 0;

        tile.startOpen(invPlayer.player);

        addSlot(new Slot(inventory, count++, 57, 38));
        addSlot(new Slot(inventory, count++, 80, 31));
        addSlot(new Slot(inventory, count++, 103, 38));

        addSlot(new OutputSlot(inventory, count++, 80, 72)); // output

    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return tile.stillValid(player);
    }

}
