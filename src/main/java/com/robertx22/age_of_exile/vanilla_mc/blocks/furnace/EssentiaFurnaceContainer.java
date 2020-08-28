package com.robertx22.age_of_exile.vanilla_mc.blocks.furnace;

import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.BaseTileContainer;
import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.AnyItemNotACurrencyEffectSlot;
import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.EssentiaFuelSlot;
import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.OutputSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.BlockPos;

public class EssentiaFurnaceContainer extends BaseTileContainer {

    Inventory tile;

    public EssentiaFurnaceContainer(int num, PlayerInventory invPlayer, Inventory inventory,
                                    BlockPos pos) {
        super(EssentiaFurnaceBlockEntity.TOTAL_SLOTS_COUNT, null, num, invPlayer);

        this.pos = pos;
        this.tile = inventory;

        final int SLOT_SPACING = 18;

        final int INPUT_SLOTS_XPOS = 8;
        final int INPUT_SLOTS_YPOS = 56;

        int x = 0;
        int y = 0;
        // 3x3 slot grid
        for (int i = 0; i < EssentiaFurnaceBlockEntity.INPUT_SLOTS.size(); i++) {

            addSlot(new AnyItemNotACurrencyEffectSlot(inventory, EssentiaFurnaceBlockEntity.INPUT_SLOTS.get(i), INPUT_SLOTS_XPOS + SLOT_SPACING * x, INPUT_SLOTS_YPOS + SLOT_SPACING * y));

            x++;
            if (x >= 3) {
                x = 0;
                y++;
            }

        }

        final int OUTPUT_SLOTS_XPOS = 116;
        final int OUTPUT_SLOTS_YPOS = 54;

        x = 0;
        y = 0;
        // 3x3 slot grid
        for (int i = 0; i < EssentiaFurnaceBlockEntity.OUTPUT_SLOTS.size(); i++) {
            addSlot(new OutputSlot(inventory, EssentiaFurnaceBlockEntity.OUTPUT_SLOTS.get(i), OUTPUT_SLOTS_XPOS + SLOT_SPACING * x, OUTPUT_SLOTS_YPOS + SLOT_SPACING * y));
            x++;
            if (x >= 3) {
                x = 0;
                y++;
            }

        }

        addSlot(
            new EssentiaFuelSlot(inventory, EssentiaFurnaceBlockEntity.FUEL_SLOTS.get(0), 80, 91));

    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return tile.canPlayerUse(player);
    }
}
