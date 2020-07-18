package com.robertx22.mine_and_slash.capability.player;

import com.robertx22.mine_and_slash.capability.bases.BaseProvider;
import com.robertx22.mine_and_slash.capability.bases.BaseStorage;
import com.robertx22.mine_and_slash.capability.bases.ICommonPlayerCap;
import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.config.forge.ModConfig;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.saveclasses.PlayerStatPointsData;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.IApplyableStats;
import com.robertx22.mine_and_slash.uncommon.datasaving.base.LoadSave;
import com.robertx22.mine_and_slash.vanilla_mc.packets.sync_cap.PlayerCaps;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PlayerStatsCap {

    public static final Identifier RESOURCE = new Identifier(Ref.MODID, "player_stat_points");
    private static final String LOC = "PLAYER_STAT_POINTS_DATA";

    @CapabilityInject(IPlayerStatPointsData.class)
    public static final Capability<IPlayerStatPointsData> Data = null;

    public interface IPlayerStatPointsData extends ICommonPlayerCap, IApplyableStats {

        public void addPoint(PlayerEntity player, Stat stat, EntityCap.UnitData data);

        int getAvailablePoints(EntityCap.UnitData data);

        boolean hasAvailablePoints(EntityCap.UnitData data);

        void resetStats();

        PlayerStatPointsData getData();

    }

    @Mod.EventBusSubscriber
    public static class EventHandler {

        @SubscribeEvent
        public static void onEntityConstruct(AttachCapabilitiesEvent<Entity> event) {

            if (event.getObject() instanceof PlayerEntity) {
                event.addCapability(RESOURCE, new Provider());
            }
        }

    }

    public static class Provider extends BaseProvider<IPlayerStatPointsData> {

        @Override
        public IPlayerStatPointsData defaultImpl() {
            return new DefaultImpl();
        }

        @Override
        public Capability<IPlayerStatPointsData> dataInstance() {
            return Data;
        }
    }

    public static class DefaultImpl implements IPlayerStatPointsData {

        PlayerStatPointsData data = new PlayerStatPointsData();

        @Override
        public CompoundTag saveToNBT() {
            CompoundTag nbt = new CompoundTag();
            LoadSave.Save(data, nbt, LOC);
            return nbt;
        }

        @Override
        public PlayerCaps getCapType() {
            return PlayerCaps.STAT_POINTS;
        }

        @Override
        public void loadFromNBT(CompoundTag nbt) {
            this.data = LoadSave.Load(PlayerStatPointsData.class, new PlayerStatPointsData(), nbt, LOC);
        }

        @Override
        public int getAvailablePoints(EntityCap.UnitData data) {

            int avail = getPointsForLevel(data.getLevel());

            return avail - this.data.getTotal();
        }

        public int getPointsForLevel(int lvl) {
            return (int) (lvl * ModConfig.INSTANCE.Server.STAT_POINTS_PER_LVL.get());
        }

        @Override
        public boolean hasAvailablePoints(EntityCap.UnitData data) {
            return getAvailablePoints(data) > 0;
        }

        @Override
        public void resetStats() {
            data.dexterity = 0;
            data.intelligence = 0;
            data.strength = 0;
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
                data.tryRecalculateStats(player);
                data.syncToClient(player);
            }
        }

        @Override
        public void applyStats(EntityCap.UnitData data) {
            this.getData()
                .applyStats(data);
        }
    }

    public static class Storage extends BaseStorage<IPlayerStatPointsData> {

    }

}