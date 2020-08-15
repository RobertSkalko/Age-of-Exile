package com.robertx22.age_of_exile.mixin_methods;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.SpawnSettings;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

public class AddMobSpawns {

    static class Spawns {

        List<SpawnSettings.SpawnEntry> all = new ArrayList<>();

        public SpawnSettings.SpawnEntry ARCANE_SLIME = of(ModRegistry.ENTITIES.ARCANE_SLIME, 500, 3, 3);
        public SpawnSettings.SpawnEntry FIRE_SLIME = of(ModRegistry.ENTITIES.FIRE_BOMB, 500, 3, 3);
        public SpawnSettings.SpawnEntry WATER_SLIME = of(ModRegistry.ENTITIES.WATER_SLIME, 500, 3, 3);
        public SpawnSettings.SpawnEntry THUNDER_SLIME = of(ModRegistry.ENTITIES.THUNDER_SLIME, 500, 3, 3);
        public SpawnSettings.SpawnEntry NATURE_SLIME = of(ModRegistry.ENTITIES.NATURE_SLIME, 500, 3, 3);

        SpawnSettings.SpawnEntry of(EntityType type, int weight, int min, int max) {
            SpawnSettings.SpawnEntry entry = new SpawnSettings.SpawnEntry(type, weight, min, max);
            all.add(entry);
            return entry;
        }
    }

    static Spawns spawns = null;

    public static void my$onConstruct(CallbackInfoReturnable<List<SpawnSettings.SpawnEntry>> ci) {
        if (spawns == null) {
            spawns = new Spawns();
        }
        List<SpawnSettings.SpawnEntry> list = new ArrayList<>(ci.getReturnValue());
        list.addAll(spawns.all);
        ci.setReturnValue(list);

    }
}
