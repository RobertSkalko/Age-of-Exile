package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.mixin_methods.AddMobSpawns;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.SpawnSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;

@Mixin(SpawnSettings.class)
public abstract class SpawnSettingsMixin {

    @Accessor(value = "spawners")
    public abstract Map<SpawnGroup, List<SpawnSettings.SpawnEntry>> getspawners();

    @Accessor(value = "spawners")
    public abstract void setspawners(Map<SpawnGroup, List<SpawnSettings.SpawnEntry>> map);

    @Inject(method = "getSpawnEntry(Lnet/minecraft/entity/SpawnGroup;)Ljava/util/List;", at = @At("RETURN"))
    public void my$onConstruct(SpawnGroup spawnGroup, CallbackInfoReturnable<List<SpawnSettings.SpawnEntry>> ci) {
        try {
            if (spawnGroup == SpawnGroup.MONSTER) {
                Map<SpawnGroup, List<SpawnSettings.SpawnEntry>> map = getspawners();
                if (AddMobSpawns.shouldAddMySpawns(map)) {
                    setspawners(AddMobSpawns.addMySpawns(map));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
