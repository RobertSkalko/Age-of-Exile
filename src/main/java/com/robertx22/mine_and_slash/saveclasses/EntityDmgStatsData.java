package com.robertx22.mine_and_slash.saveclasses;

import com.robertx22.mine_and_slash.uncommon.utilityclasses.Utilities;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Storable
public class EntityDmgStatsData {

    @Store
    private HashMap<String, Float> map = new HashMap<>();

    public void onDamage(LivingEntity entity, float dmg) {

        if (entity == null) {
            return;
        } else {
            if (entity instanceof PlayerEntity) {
                String id = entity.getUuid()
                    .toString();
                map.put(id, dmg + map.getOrDefault(id, 0F));
            }
        }
    }

    public LivingEntity getHighestDamageEntity(ServerWorld world, LivingEntity victim) {

        Optional<Map.Entry<String, Float>> opt = map.entrySet()
            .stream()
            .max((one, two) -> one.getValue() >= two.getValue() ? 1 : -1);

        if (opt.isPresent()) {

            Map.Entry<String, Float> entry = opt.get();

            String id = entry.getKey();
            Float val = entry.getValue();

            float totalPlayerDmg = 0;

            for (Float x : map.values()) {
                totalPlayerDmg += x;
            }

            if (totalPlayerDmg < victim.getMaxHealth() / 2F) {
                return null; // means enviroment did more damage than the highest entity dmg dealer
            }

            Entity en = Utilities.getEntityByUUID(world, UUID.fromString(id));

            if (en instanceof LivingEntity) {
                return (LivingEntity) en;
            }
        }
        return null;

    }
}
