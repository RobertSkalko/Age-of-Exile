package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.mixin_methods.AddMobSpawns;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.SpawnSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(SpawnSettings.class)
public class SpawnSettingsMixin {

    @Inject(method = "getSpawnEntry(Lnet/minecraft/entity/SpawnGroup;)Ljava/util/List;", at = @At("RETURN"), cancellable = true)
    public void my$onConstruct(SpawnGroup spawnGroup, CallbackInfoReturnable<List<SpawnSettings.SpawnEntry>> ci) {
        if (spawnGroup == SpawnGroup.MONSTER) {
            AddMobSpawns.my$onConstruct(ci);
        }
    }

}
