package com.robertx22.age_of_exile.mixins;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.config.forge.ClientConfigs;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ContainerScreen.class)
public class ItemGlintMixin {

    @Inject(method = "renderSlot(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/inventory/container/Slot;)V", at = @At(value = "HEAD"))
    private void drawMyGlint(MatrixStack matrices, Slot slot, CallbackInfo ci) {

        try {
            ContainerScreen screen = (ContainerScreen) (Object) this;

            if (ClientConfigs.getConfig().RENDER_ITEM_RARITY_BACKGROUND.get()) {
                ItemStack stack = slot.getItem();

                GearRarity rar = null;

                if (Gear.has(stack)) {

                    GearItemData gear = Gear.Load(stack);

                    rar = gear.getRarity();
                }

                if (StackSaving.STAT_SOULS.has(stack)) {
                    try {
                        rar = ExileDB.GearRarities()
                            .get(StackSaving.STAT_SOULS.loadFrom(stack).rar);
                    } catch (Exception e) {

                    }
                }

                if (rar == null) {
                    return;
                }

                RenderSystem.enableBlend();
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, ClientConfigs.getConfig().ITEM_RARITY_OPACITY.get()
                    .floatValue()); // transparency

                ResourceLocation tex = rar
                    .getGlintTextureFull();

                if (ClientConfigs.getConfig().ITEM_RARITY_BACKGROUND_TYPE.get() == ClientConfigs.GlintType.BORDER) {
                    tex = rar
                        .getGlintTextureBorder();
                }

                Minecraft.getInstance()
                    .getTextureManager()
                    .bind(tex);

                screen.blit(matrices, slot.x, slot.y, 0, 0, 16, 16, 16, 16);
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1F);
                RenderSystem.disableBlend();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
