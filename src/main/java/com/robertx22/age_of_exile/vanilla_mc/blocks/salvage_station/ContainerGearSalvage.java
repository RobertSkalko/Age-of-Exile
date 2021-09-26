package com.robertx22.age_of_exile.vanilla_mc.blocks.salvage_station;

import com.robertx22.age_of_exile.mmorpg.registers.common.SlashContainers;
import com.robertx22.age_of_exile.vanilla_mc.blocks.BaseTileContainer;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.VanillaFuelSlot;
import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.OutputSlot;
import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.SalvageSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public class ContainerGearSalvage extends BaseTileContainer {

    IInventory tile;

    public ContainerGearSalvage(int num, PlayerInventory invPlayer, PacketBuffer packet) {
        this(num, invPlayer, new Inventory(TileGearSalvage.TOTAL_SLOTS_COUNT), packet.readBlockPos());
    }

    public ContainerGearSalvage(int num, PlayerInventory invPlayer, IInventory inventory,
                                BlockPos pos) {
        super(TileGearSalvage.TOTAL_SLOTS_COUNT, SlashContainers.SALVAGE.get(), num, invPlayer);

        this.pos = pos;
        this.tile = inventory;

        tile.startOpen(invPlayer.player);

        int i = 0;
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) { // hard coded numbers
                addSlot(new SalvageSlot(inventory, i, 8 + x * 18, 40 + y * 18));
                i++;
            }
        }
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) { // hard coded numbers
                addSlot(new OutputSlot(inventory, i, 116 + x * 18, 40 + y * 18));
                i++;
            }
        }
        addSlot(new VanillaFuelSlot(inventory, i, 80, 95));
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return tile.stillValid(player);
    }
}