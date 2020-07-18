package com.robertx22.mine_and_slash.capability.world;

import com.robertx22.mine_and_slash.capability.bases.BaseProvider;
import com.robertx22.mine_and_slash.capability.bases.BaseStorage;
import com.robertx22.mine_and_slash.capability.bases.ICommonCap;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.saveclasses.anti_mob_farm.AntiMobFarmData;
import com.robertx22.mine_and_slash.uncommon.datasaving.base.LoadSave;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AntiMobFarmCap {

    public static final Identifier RESOURCE = new Identifier(Ref.MODID, "anti_mob_farm");

    @CapabilityInject(IAntiMobFarmData.class)
    public static final Capability<IAntiMobFarmData> Data = null;

    public interface IAntiMobFarmData extends ICommonCap {

        void onValidMobDeathByPlayer(LivingEntity en);

        float getDropMultiForMob(LivingEntity en);

        void onMinutePassed();

    }

    @Mod.EventBusSubscriber
    public static class EventHandler {

        @SubscribeEvent
        public static void onEntityConstruct(AttachCapabilitiesEvent<World> event) {
            event.addCapability(RESOURCE, new Provider());
        }

    }

    public static class Provider extends BaseProvider<IAntiMobFarmData> {

        @Override
        public IAntiMobFarmData defaultImpl() {
            return new DefaultImpl();
        }

        @Override
        public Capability<IAntiMobFarmData> dataInstance() {
            return Data;
        }
    }

    static String DATA_LOC = Ref.MODID + ":data";

    public static class DefaultImpl implements IAntiMobFarmData {

        AntiMobFarmData data = new AntiMobFarmData();

        @Override
        public CompoundTag saveToNBT() {

            CompoundTag nbt = new CompoundTag();

            if (data != null) {
                LoadSave.Save(data, nbt, DATA_LOC);
            }

            return nbt;

        }

        @Override
        public void loadFromNBT(CompoundTag nbt) {

            data = LoadSave.Load(AntiMobFarmData.class, new AntiMobFarmData(), nbt, DATA_LOC);

            if (data == null) {
                data = new AntiMobFarmData();
            }

        }

        @Override
        public void onValidMobDeathByPlayer(LivingEntity en) {
            this.data.onValidMobDeathByPlayer(en);
        }

        @Override
        public float getDropMultiForMob(LivingEntity en) {
            return this.data.getDropMultiForMob(en);
        }

        @Override
        public void onMinutePassed() {
            this.data.tickDownAllKillCounters();
        }
    }

    public static class Storage extends BaseStorage<IAntiMobFarmData> {

    }

}
