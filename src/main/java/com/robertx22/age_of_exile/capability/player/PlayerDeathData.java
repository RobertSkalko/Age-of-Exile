package com.robertx22.age_of_exile.capability.player;

import com.robertx22.age_of_exile.capability.bases.ICommonPlayerCap;
import com.robertx22.age_of_exile.saveclasses.PlayerDeathStatistics;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.library_of_exile.utils.LoadSave;
import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;

public class PlayerDeathData implements Component, ICommonPlayerCap {

    public BlockPos deathPos;
    public String deathDim;

    public PlayerDeathStatistics deathStats = new PlayerDeathStatistics();

    @Override
    public void readFromNbt(CompoundTag nbt) {
        this.deathPos = BlockPos.fromLong(nbt.getLong("death_pos"));
        this.deathDim = nbt.getString("dim");
        try {
            this.deathStats = LoadSave.Load(PlayerDeathStatistics.class, new PlayerDeathStatistics(), nbt, "stats");
        } catch (Exception e) {
            e.printStackTrace();
            this.deathStats = new PlayerDeathStatistics();
        }
    }

    @Override
    public void writeToNbt(CompoundTag nbt) {
        if (deathPos != null) {
            nbt.putLong("death_pos", deathPos.asLong());
        }
        if (deathDim != null) {
            nbt.putString("dim", deathDim);
        }
        if (deathStats != null) {
            LoadSave.Save(deathStats, nbt, "stats");
        }
    }

    @Override
    public PlayerCaps getCapType() {
        return PlayerCaps.DEATH_STATS;
    }
}
