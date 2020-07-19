package com.robertx22.mine_and_slash.database.data.spells.blocks.base;

import com.robertx22.mine_and_slash.database.data.spells.entities.bases.ISpellEntity;
import com.robertx22.mine_and_slash.saveclasses.spells.EntitySpellData;
import com.robertx22.mine_and_slash.uncommon.datasaving.EntitySpellDataSaving;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tickable;

public abstract class BaseSpellTileEntity extends BlockEntity implements ISpellEntity, Tickable {

    public EntitySpellData data = new EntitySpellData();

    public BaseSpellTileEntity(BlockEntityType type) {
        super(type);
    }

    @Override
    public void fromTag(CompoundTag nbt) {
        super.fromTag(nbt);
        this.data = EntitySpellDataSaving.Load(nbt);
    }

    @Override
    public final void tick() {

        try {
            data.ticksExisted++;
        } catch (Exception e) {
            e.printStackTrace();
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
            return;
        }

        try {
            onTick();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (data.getRemainingLifeTicks() < 1 && data.ticksExisted > 5) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public abstract void onTick();

    @Override
    public CompoundTag toTag(CompoundTag nbt) {
        super.toTag(nbt);
        EntitySpellDataSaving.Save(nbt, data);
        return nbt;

    }

    @Override
    public EntitySpellData getSpellData() {
        return data;
    }

    @Override
    public void setSpellData(EntitySpellData data) {
        this.data = data;
    }

}
