package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.mixin_methods.OnItemCrafted;
import com.robertx22.age_of_exile.player_skills.events.OnCraftGiveSkillExp;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public class OnCraftMixin {

    @Inject(method = "onCraft(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;I)V", at = @At(value = "HEAD"))
    public void onCraft(World world, PlayerEntity player, int amount, CallbackInfo ci) {
        try {
            if (player != null && player.world.isClient) {
                return;
            }
            ItemStack stack = (ItemStack) (Object) this;

            OnItemCrafted.onCraft(stack, world, player, amount, ci);
            OnCraftGiveSkillExp.onCraft(stack, world, player, amount, ci);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
