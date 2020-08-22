package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.mmorpg.registers.common.ModWorldGen;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DefaultBiomeFeatures.class)
public class DefaultBiomeFeaturesMixin {

    @Inject(method = "addDesertLakes", at = @At("RETURN"))
    private static void addGateway(GenerationSettings.Builder builder, CallbackInfo ci) {
        builder.structureFeature(ModWorldGen.INSTANCE.CONFIGURED_TOWER);
        System.out.println("Added tower structure.");
    }

    @Inject(method = "addLandCarvers", at = @At("RETURN"))
    private static void addLandCarvers(GenerationSettings.Builder builder, CallbackInfo ci) {

        builder.structureFeature(ModWorldGen.INSTANCE.CONFIGURED_TOWER);
        System.out.println("Added tower structure.");
    }

}