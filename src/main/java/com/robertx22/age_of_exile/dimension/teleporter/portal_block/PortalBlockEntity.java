package com.robertx22.age_of_exile.dimension.teleporter.portal_block;

import com.robertx22.age_of_exile.dimension.teleporter.PortalBlocKData;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.BaseModificationStation;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class PortalBlockEntity extends BaseModificationStation {

    public PortalBlocKData data = new PortalBlocKData();

    public PortalBlockEntity() {
        super(ModRegistry.BLOCK_ENTITIES.PORTAL, 0);
    }

    @Override
    public boolean modifyItem(int number, PlayerEntity player) {
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent("");
    }

    @Override
    public Container createMenu(int syncId, IInventory inv, PlayerEntity player) {
        return null;
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        if (data != null) {
            LoadSave.Save(data, nbt, "teldata");
        }
        return nbt;
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        try {
            super.load(state, nbt);
            this.data = LoadSave.Load(PortalBlocKData.class, new PortalBlocKData(), nbt, "teldata");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

