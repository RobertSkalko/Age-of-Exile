package com.robertx22.age_of_exile.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.config.forge.ClientConfigs;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public class ItemGlintMixin {

    @Inject(method = "drawSlot(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/screen/slot/Slot;)V", at = @At(value = "HEAD"),
        cancellable = true)
    private void drawMyGlint(MatrixStack matrices, Slot slot, CallbackInfo ci) {

        try {
            HandledScreen screen = (HandledScreen) (Object) this;

            if (ModConfig.get().client.RENDER_ITEM_RARITY_BACKGROUND) {
                ItemStack stack = slot.getStack();

                /*
                SkillGemData gem = SkillGemData.fromStack(stack);

                if (gem != null) {
                    String spellid = gem.getSkillGem().spell_id;

                    if (Database.Spells()
                        .isRegistered(spellid)) {

                        Spell spell = Database.Spells()
                            .get(spellid);

                        RenderSystem.enableBlend();
                        RenderSystem.color4f(1.0F, 1.0F, 1.0F, ModConfig.get().client.ITEM_RARITY_OPACITY); // transparency

                        MinecraftClient.getInstance()
                            .getTextureManager()
                            .bindTexture(spell.getIconLoc());

                        screen.drawTexture(matrices, slot.x, slot.y, 0, 0, 16, 16, 16, 16);
                        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1F);
                        RenderSystem.disableBlend();
                    }
                } */

                if (Gear.has(stack)) {

                    GearItemData gear = Gear.Load(stack);

                    if (gear.GetBaseGearType()
                        .isTool()) {
                        return;
                    }

                    RenderSystem.enableBlend();
                    RenderSystem.color4f(1.0F, 1.0F, 1.0F, ModConfig.get().client.ITEM_RARITY_OPACITY); // transparency

                    Identifier tex = gear.getRarity()
                        .getGlintTextureFull();

                    if (ModConfig.get().client.ITEM_RARITY_BACKGROUND_TYPE == ClientConfigs.GlintType.BORDER) {
                        tex = gear.getRarity()
                            .getGlintTextureBorder();
                    }

                    MinecraftClient.getInstance()
                        .getTextureManager()
                        .bindTexture(tex);

                    screen.drawTexture(matrices, slot.x, slot.y, 0, 0, 16, 16, 16, 16);
                    RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1F);
                    RenderSystem.disableBlend();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
