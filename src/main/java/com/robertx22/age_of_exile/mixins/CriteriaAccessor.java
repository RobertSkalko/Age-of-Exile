package com.robertx22.age_of_exile.mixins;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(CriteriaTriggers.class)
public interface CriteriaAccessor {
    @Accessor(value = "VALUES")
    static Map<ResourceLocation, CriterionTrigger<?>> getValues() {
        throw new UnsupportedOperationException("untransformed accessor");
    }
}
