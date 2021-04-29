package com.robertx22.age_of_exile.dimension.teleporter.portal_block;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.BaseModificationStation;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class PortalBlockEntity extends BaseModificationStation {

    public BlockPos dungeonPos = new BlockPos(0, 0, 0);

    public PortalBlockEntity() {
        super(ModRegistry.BLOCK_ENTITIES.PORTAL, 0);
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

    @Override
    public CompoundTag toTag(CompoundTag nbt) {
        super.toTag(nbt);

        nbt.putLong("dpos", dungeonPos.asLong());

        return nbt;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag nbt) {
        try {
            super.fromTag(state, nbt);

            this.dungeonPos = BlockPos.fromLong(nbt.getLong("dpos"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

