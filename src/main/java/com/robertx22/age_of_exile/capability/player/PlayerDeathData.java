package com.robertx22.age_of_exile.capability.player;

import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;

public class PlayerDeathData implements Component {

    public BlockPos deathPos;
    public String deathDim;

    @Override
    public void readFromNbt(CompoundTag nbt) {
        this.deathPos = BlockPos.fromLong(nbt.getLong("death_pos"));
        this.deathDim = nbt.getString("dim");
    }

    @Override
    public void writeToNbt(CompoundTag nbt) {
        if (deathPos != null) {
            nbt.putLong("death_pos", deathPos.asLong());
        }
        if (deathDim != null) {
            nbt.putString("dim", deathDim);
        }
    }

}
