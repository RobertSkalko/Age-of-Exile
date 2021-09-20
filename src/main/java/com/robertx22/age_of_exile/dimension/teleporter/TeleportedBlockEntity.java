package com.robertx22.age_of_exile.dimension.teleporter;

import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlockEntities;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.BaseModificationStation;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class TeleportedBlockEntity extends BaseModificationStation {

    public TeleportedBlockEntity() {
        super(SlashBlockEntities.TELEPORTER.get(), 0);
    }

    public TeleporterData data = new TeleporterData();

    @Override
    public boolean modifyItem(int number, PlayerEntity player) {
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent("");
    }

    @Override
    public Container createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return null;
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        LoadSave.Save(data, nbt, "td");
        return nbt;
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        try {
            super.load(state, nbt);
            this.data = LoadSave.Load(TeleporterData.class, new TeleporterData(), nbt, "td");

            if (data == null) {
                data = new TeleporterData();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tick() {
        super.tick();

    }

}
