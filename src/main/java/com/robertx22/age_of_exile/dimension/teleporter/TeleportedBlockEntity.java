package com.robertx22.age_of_exile.dimension.teleporter;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.BaseModificationStation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class TeleportedBlockEntity extends BaseModificationStation {

    public TeleportedBlockEntity() {
        super(ModRegistry.BLOCK_ENTITIES.TELEPORTER, 0);
    }

    @Override
    public boolean modifyItem(int number, PlayerEntity player) {
        return false;
    }

    @Override
    public Text getDisplayName() {
        return new LiteralText("");
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return null;
    }

}
