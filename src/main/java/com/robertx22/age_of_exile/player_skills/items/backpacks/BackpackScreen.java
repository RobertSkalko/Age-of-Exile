package com.robertx22.age_of_exile.player_skills.items.backpacks;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.player_skills.items.backpacks.upgrades.BackpackUpgradeItem;
import com.robertx22.library_of_exile.gui.ItemSlotButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class BackpackScreen extends ContainerScreen<BackpackContainer> {
    private ResourceLocation texture = new ResourceLocation(SlashRef.MODID, "textures/gui/backpack/background.png");

    public BackpackScreen(BackpackContainer handler, PlayerInventory inventory, ITextComponent text) {
        super(handler, inventory, new StringTextComponent(""));

        imageWidth = 176;
        imageHeight = 233;

        this.texture = new ResourceLocation(SlashRef.MODID, "textures/gui/backpack/" + handler.info.tier + ".png");
    }

    @Override
    public void init() {
        super.init();

        int i = 0;
        for (BackpackUpgradeItem item : this.menu.info.data.getUpgrades()) {
            if (i < 9) {
                this.addButton(new ItemSlotButton(new ItemStack(item), this.leftPos + 7 + (i * 18), this.topPos + 11));
            }
            i++;
        }

    }

    @Override
    protected void renderBg(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        Minecraft.getInstance()
            .getTextureManager()
            .bind(texture);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        blit(matrices, this.leftPos, this.topPos, 0, 0, imageWidth, imageHeight);

    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        this.renderTooltip(matrices, mouseX, mouseY);

        buttons.forEach(x -> x.renderToolTip(matrices, mouseX, mouseY));
    }

    @Override
    protected void renderLabels(MatrixStack matrices, int mouseX, int mouseY) {
        // dont draw the damn name

    }

}
