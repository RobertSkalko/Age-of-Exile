package com.robertx22.age_of_exile.vanilla_mc.blocks.item_modify_station;

import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.AnyCurrencyEffectItemSlot;
import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.AnyItemNotACurrencyEffectSlot;
import com.robertx22.library_of_exile.tile_bases.BaseTileContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.BlockPos;

public class ContainerGearModify extends BaseTileContainer {

    public static final int MODIFY_SLOTS_COUNT = 2;

    Inventory tile;

    public ContainerGearModify(int i, PlayerInventory invPlayer, Inventory inventory,
                               BlockPos pos) {

        super(MODIFY_SLOTS_COUNT, null, i, invPlayer);
        this.tile = inventory;
        this.pos = pos;
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