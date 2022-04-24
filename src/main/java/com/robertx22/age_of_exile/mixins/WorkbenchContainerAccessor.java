package com.robertx22.age_of_exile.mixins;

import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.util.IWorldPosCallable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(WorkbenchContainer.class)
public interface WorkbenchContainerAccessor {
    @Accessor
    IWorldPosCallable getAccess();
}
