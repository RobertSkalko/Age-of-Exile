package com.robertx22.mine_and_slash.capability.world;

import com.robertx22.mine_and_slash.capability.bases.ICommonCap;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.saveclasses.anti_mob_farm.AntiMobFarmData;
import com.robertx22.mine_and_slash.uncommon.datasaving.base.LoadSave;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;

public class AntiMobFarmCap {

    public static final Identifier RESOURCE = new Identifier(Ref.MODID, "anti_mob_farm");

    public interface IAntiMobFarmData extends ICommonCap {

        void onValidMobDeathByPlayer(LivingEntity en);

        float getDropMultiForMob(LivingEntity en);

        void onMinutePassed();

    }

    static String DATA_LOC = Ref.MODID + ":data";

    public static class DefaultImpl implements IAntiMobFarmData {

        AntiMobFarmData data = new AntiMobFarmData();

        @Override
        public void toTag(CompoundTag nbt) {

            if (data != null) {
                LoadSave.Save(data, nbt, DATA_LOC);
            }

        }

        @Override
        public void fromTag(CompoundTag nbt) {

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

}
