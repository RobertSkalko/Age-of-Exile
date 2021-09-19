package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.player_skills.events.OnSmeltMining;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class FurnaceMixin {

    @Accessor(value = "recipesUsed")
    public abstract Object2IntOpenHashMap<ResourceLocation> getRecipesUsed();

    @Inject(method = "dropExperience(Lnet/minecraft/entity/player/PlayerEntity;)V", at = @At(value = "HEAD"))
    public void hookOnDropExp(PlayerEntity player, CallbackInfo ci) {
        AbstractFurnaceBlockEntity furnace = (AbstractFurnaceBlockEntity) (Object) this;
        OnSmeltMining.hookOnDropExp(getRecipesUsed(), furnace, player, ci);
    }

}
