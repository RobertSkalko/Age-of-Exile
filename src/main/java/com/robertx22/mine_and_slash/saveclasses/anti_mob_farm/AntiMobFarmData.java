package com.robertx22.mine_and_slash.saveclasses.anti_mob_farm;

import com.robertx22.mine_and_slash.config.forge.CommonConfig;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.WorldUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.ChunkPos;

import java.util.HashMap;

@Storable
public class AntiMobFarmData {

    @Store
    private HashMap<String, AntiMobFarmChunkData> map = new HashMap<String, AntiMobFarmChunkData>();

    public void onValidMobDeathByPlayer(LivingEntity en) {

        String key = getKey(en);

        AntiMobFarmChunkData data = map.getOrDefault(key, new AntiMobFarmChunkData());
        data.onMobDeath();
        map.put(key, data);
    }

    public float getDropMultiForMob(LivingEntity en) {

        if (WorldUtils.isMapWorldClass(en.world)) {
            return 1;
        }
        if (CommonConfig.INSTANCE.ENABLE_ANTI_MOB_FARM == false) {
            return 1;
        }

        String key = getKey(en);

        if (map.containsKey(key)) {
            return map.get(key)
                .getDropsMulti();
        } else {
            return 1;
        }
    }

    public void tickDownAllKillCounters() {
        map.entrySet()
            .forEach(x -> {
                x.getValue()
                    .tickDown(AntiMobFarmChunkData.Type.NORMAL); // todo, punish mob farms
            });
    }

    private String getKey(LivingEntity en) {
        ChunkPos cp = new ChunkPos(en.getBlockPos());
        return cp.x + "_" + cp.z;
    }

}