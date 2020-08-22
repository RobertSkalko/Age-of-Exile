package com.robertx22.age_of_exile.mixins;

import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.datafixers.DataFixer;
import com.robertx22.age_of_exile.mmorpg.registers.common.ModWorldGen;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ServerResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListenerFactory;
import net.minecraft.util.UserCache;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

    @Shadow
    @Final
    protected DynamicRegistryManager.Impl registryManager;

    @Inject(
        method = "<init>",
        at = @At(value = "TAIL")
    )
    private void modifyBiomeRegistry(Thread thread, DynamicRegistryManager.Impl impl, LevelStorage.Session session, SaveProperties saveProperties, ResourcePackManager resourcePackManager, Proxy proxy, DataFixer dataFixer, ServerResourceManager serverResourceManager, MinecraftSessionService minecraftSessionService, GameProfileRepository gameProfileRepository, UserCache userCache, WorldGenerationProgressListenerFactory worldGenerationProgressListenerFactory, CallbackInfo ci) {

        if (registryManager.getOptional(Registry.BIOME_KEY)
            .isPresent()) {

            for (Biome biome : registryManager.getOptional(Registry.BIOME_KEY)
                .get()) {
                List<List<Supplier<ConfiguredFeature<?, ?>>>> tempFeature = ((GenerationSettingsAccessor) biome.getGenerationSettings()).getGSFeatures();
                List<List<Supplier<ConfiguredFeature<?, ?>>>> mutableFeatures = new ArrayList<>(tempFeature);

                ((GenerationSettingsAccessor) biome.getGenerationSettings()).setGSFeatures(mutableFeatures);
                ((GenerationSettingsAccessor) biome.getGenerationSettings()).setGSStructureFeatures(new ArrayList<>(((GenerationSettingsAccessor) biome.getGenerationSettings()).getGSStructureFeatures()));

                biome.getGenerationSettings()
                    .getStructureFeatures()
                    .add(() -> ModWorldGen.INSTANCE.CONFIGURED_TOWER);

            }
        }
    }
}