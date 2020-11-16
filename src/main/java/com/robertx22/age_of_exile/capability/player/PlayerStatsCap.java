package com.robertx22.age_of_exile.capability.player;

import com.robertx22.age_of_exile.capability.bases.ICommonPlayerCap;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.PlayerStatPointsData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IApplyableStats;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;

public class PlayerStatsCap {

    public static final Identifier RESOURCE = new Identifier(Ref.MODID, "player_stat_points");
    private static final String LOC = "PLAYER_STAT_POINTS_DATA";

    public interface IPlayerStatPointsData extends ICommonPlayerCap, IApplyableStats {

        public void addPoint(PlayerEntity player, Stat stat, EntityCap.UnitData data);

        int getAvailablePoints(EntityCap.UnitData data);

        boolean hasAvailablePoints(EntityCap.UnitData data);

        void resetStats();

        PlayerStatPointsData getData();

    }

    public static class DefaultImpl implements IPlayerStatPointsData {

        PlayerStatPointsData data = new PlayerStatPointsData();

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
            this.data = LoadSave.Load(PlayerStatPointsData.class, new PlayerStatPointsData(), nbt, LOC);
        }

        @Override
        public int getAvailablePoints(EntityCap.UnitData data) {

            int avail = getPointsForLevel(data.getLevel());

            return avail - this.data.getTotal();
        }

        public int getPointsForLevel(int lvl) {
            return (int) (lvl * ModConfig.get().Server.STAT_POINTS_PER_LVL);
        }

        @Override
        public boolean hasAvailablePoints(EntityCap.UnitData data) {
            return getAvailablePoints(data) > 0;
        }

        @Override
        public void resetStats() {
            data.stats.clear();
        }

        @Override
        public PlayerStatPointsData getData() {
            return data;
        }

        @Override
        public void addPoint(PlayerEntity player, Stat stat, EntityCap.UnitData data) {
            if (this.hasAvailablePoints(data)) {
                getData().addPoint(stat);
                data.setEquipsChanged(true);
                data.tryRecalculateStats();
                data.syncToClient(player);
            }
        }

        @Override
        public void applyStats(EntityCap.UnitData data) {
            this.getData()
                .applyStats(data);
        }
    }

}