package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public class AbstractBlockMixin {

    // so the workbench container doesnt auto close
    @Inject(method = "is(Lnet/minecraft/block/Block;)Z", at = @At(value = "HEAD"), cancellable = true)
    private <T extends Entity> void disableCanSpawn(Block block, CallbackInfoReturnable<Boolean> cir) {
        if (block == Blocks.CRAFTING_TABLE) {
            AbstractBlock.AbstractBlockState state = (AbstractBlock.AbstractBlockState) (Object) this;
            if (state.getBlock() == SlashBlocks.MNS_CRAFTING_TABLE.get()) {
                cir.setReturnValue(true);
            }
        }
    }
}
