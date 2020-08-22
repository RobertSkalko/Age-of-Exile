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
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

                if (biome.getCategory() == Biome.Category.OCEAN || biome.getCategory() == Biome.Category.RIVER) {
                    continue;
                }

                BiomeAccessor access = (BiomeAccessor) (Object) biome;
                Map<Integer, List<StructureFeature<?>>> list = access.getStructureLists();
                list = new HashMap<>(list);
                list.get(GenerationStep.Feature.SURFACE_STRUCTURES.ordinal())
                    .add(ModWorldGen.INSTANCE.TOWER);
                access.setStructureLists(list);

                GenerationSettingsAccessor gen = (GenerationSettingsAccessor) biome.getGenerationSettings();
                List<Supplier<ConfiguredStructureFeature<?, ?>>> setlist = new ArrayList<>(gen.getGSStructureFeatures());
                setlist.add(() -> ModWorldGen.INSTANCE.CONFIGURED_TOWER);
                gen.setGSStructureFeatures(setlist);

            }
        }
    }
}