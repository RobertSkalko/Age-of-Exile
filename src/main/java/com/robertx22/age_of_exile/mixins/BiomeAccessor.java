package com.robertx22.age_of_exile.mixins;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.StructureFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin({Biome.class})
public interface BiomeAccessor {
    @Accessor("field_26634")
    Map<Integer, List<StructureFeature<?>>> getStructureLists();

    @Mutable
    @Accessor("field_26634")
    void setStructureLists(Map<Integer, List<StructureFeature<?>>> var1);
}
