package com.robertx22.age_of_exile.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ItemSlotButton extends TexturedButtonWidget {

    public static int xSize = 16;
    public static int ySize = 16;

    static Identifier buttonLoc = new Identifier(Ref.MODID, "");

    ItemStack stack;
    MinecraftClient mc = MinecraftClient.getInstance();

    public ItemSlotButton(ItemStack stack, int xPos, int yPos) {
        super(xPos + 1, yPos + 1, xSize, ySize, 0, 0, ySize + 1, buttonLoc, (button) -> {

        });

        this.stack = stack;

    }

    @Override
    public void renderButton(MatrixStack matrix, int x, int y, float ticks) {
        //super.renderButton(matrix, x, y, ticks);

        RenderSystem.enableDepthTest();
        mc.getItemRenderer()
            .renderInGuiWithOverrides(mc.player, stack, this.x, this.y);
        mc.getItemRenderer()
            .renderGuiItemOverlay(mc.textRenderer, stack, this.x, this.y, "");
        RenderSystem.disableDepthTest();

    }

    @Override
    public void renderToolTip(MatrixStack matrix, int x, int y) {
        if (!stack.isEmpty()) {
            if (isInside(x, y)) {
                List<Text> tooltip = new ArrayList<>();
                tooltip.addAll(stack.getTooltip(mc.player, TooltipContext.Default.NORMAL));
                GuiUtils.renderTooltip(matrix, tooltip, x, y);
            }
        }
    }

    public boolean isInside(int x, int y) {
        return GuiUtils.isInRect(this.x, this.y, xSize, ySize, x, y);
    }

}
