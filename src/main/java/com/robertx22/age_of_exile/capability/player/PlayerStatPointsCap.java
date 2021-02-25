package com.robertx22.age_of_exile.capability.player;

import com.robertx22.age_of_exile.capability.bases.ICommonPlayerCap;
import com.robertx22.age_of_exile.capability.player.data.StatPointsData;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;

import java.util.Map;

public class PlayerStatPointsCap implements ICommonPlayerCap {
    public static final Identifier RESOURCE = new Identifier(Ref.MODID, "stat_points");

    private static final String LOC = "data";

    PlayerEntity player;

    public PlayerStatPointsCap(PlayerEntity player) {
        this.player = player;
    }

    public StatPointsData data = new StatPointsData();

    public int getFreePoints() {
        int total = (int) (Load.Unit(player)
            .getLevel() * GameBalanceConfig.get().STAT_POINTS_PER_LEVEL);

        int spent = 0;

        for (Map.Entry<String, Integer> x : data.map.entrySet()) {
            spent += x.getValue();
        }

        return total - spent;
    }

    @Override
    public CompoundTag toTag(CompoundTag nbt) {
        LoadSave.Save(data, nbt, LOC);
        return nbt;
    }

    @Override
    public PlayerCaps getCapType() {
        return PlayerCaps.STAT_POINTS;
    }

    @Override
    public void fromTag(CompoundTag nbt) {
        this.data = LoadSave.Load(StatPointsData.class, new StatPointsData(), nbt, LOC);
        if (data == null) {
            data = new StatPointsData();
        }
    }

}