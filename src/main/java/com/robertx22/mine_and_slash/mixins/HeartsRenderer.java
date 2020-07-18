package com.robertx22.mine_and_slash.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.gui.ForgeIngameGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

// renders only max 20 hearts. So if player has hundreds, it doesn't overfill screen
@Mixin(ForgeIngameGui.class)
public abstract class HeartsRenderer {

    @ModifyVariable(method = "renderHealth(II)V", at = @At("LOAD"), name = "health", remap = false)
    public int modify$currentHealth(int health) {
        int current = MathHelper.ceil(MinecraftClient.getInstance().player.getHealth());
        float healthMax = (float) MinecraftClient.getInstance().player.getMaximumHealth();
        return (int) ((float) current / (float) healthMax * 20);
    }

    @ModifyVariable(method = "renderHealth(II)V", at = @At("LOAD"), name = "healthMax", remap = false)
    public float modify$maxHealth(float healthMax) {
        return 20;
    }

    @ModifyVariable(method = "renderHealthMount(II)V", at = @At("LOAD"), name = "health", remap = false)
    public int modify$currentHealthMounted(int health) {
        LivingEntity mount = (LivingEntity) MinecraftClient.getInstance().player.getVehicle();
        int current = MathHelper.ceil(mount.getHealth());
        float healthMax = (float) mount.getMaximumHealth();
        return (int) ((float) current / (float) healthMax * 20);
    }

    @ModifyVariable(method = "renderHealthMount(II)V", at = @At("LOAD"), name = "healthMax", remap = false)
    public float modify$maxHealthMounted(float healthMax) {
        return 20;
    }

}
