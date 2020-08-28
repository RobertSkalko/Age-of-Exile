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

        // 3x3 slot grid
        for (int x = 0; x < EssentiaFurnaceBlockEntity.INPUT_SLOTS.size() / 3; x++) {
            for (int y = 0; y < EssentiaFurnaceBlockEntity.INPUT_SLOTS.size() / 3; y++) {
                addSlot(new AnyItemNotACurrencyEffectSlot(inventory, EssentiaFurnaceBlockEntity.INPUT_SLOTS.get(y + x), INPUT_SLOTS_XPOS + SLOT_SPACING * x, INPUT_SLOTS_YPOS + SLOT_SPACING * y));
            }
        }

        final int OUTPUT_SLOTS_XPOS = 116;
        final int OUTPUT_SLOTS_YPOS = 54;

        // 3x3 slot grid
        for (int x = 0; x < EssentiaFurnaceBlockEntity.OUTPUT_SLOTS.size() / 3; x++) {
            for (int y = 0; y < EssentiaFurnaceBlockEntity.OUTPUT_SLOTS.size() / 3; y++) {
                addSlot(
                    new OutputSlot(inventory,
                        EssentiaFurnaceBlockEntity.OUTPUT_SLOTS.get(y + x),
                        OUTPUT_SLOTS_XPOS + SLOT_SPACING * x, OUTPUT_SLOTS_YPOS + SLOT_SPACING * y));
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
