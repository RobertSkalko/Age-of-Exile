package com.robertx22.age_of_exile.dimension.teleporter.portal_block;

import com.robertx22.age_of_exile.dimension.teleporter.PortalBlocKData;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.BaseModificationStation;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

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
    public Text getDisplayName() {
        return new LiteralText("");
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return null;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (data != null) {
            LoadSave.Save(data, nbt, "teldata");
        }
        return nbt;
    }

    @Override
    public void fromTag(BlockState state, NbtCompound nbt) {
        try {
            super.fromTag(state, nbt);
            this.data = LoadSave.Load(PortalBlocKData.class, new PortalBlocKData(), nbt, "teldata");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

