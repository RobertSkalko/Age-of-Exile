package com.robertx22.age_of_exile.gui.screens;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RenderUtils;
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

    static Identifier fancyBorderLoc = new Identifier(Ref.MODID, "textures/gui/wiki/pretty_icon_border.png");
    static int FX = 20;
    static int FY = 20;
    ItemStack stack;
    MinecraftClient mc = MinecraftClient.getInstance();

    public boolean renderFancyBorder = false;

    public ItemSlotButton(ItemStack stack, int xPos, int yPos) {
        this(stack, xPos, yPos, (button) -> {
        });
        this.stack = stack;
    }

    public ItemSlotButton(ItemStack stack, int xPos, int yPos, PressAction onclick) {
        super(xPos + 1, yPos + 1, xSize, ySize, 0, 0, ySize + 1, buttonLoc, onclick);
        this.stack = stack;
    }

    @Override
    public void renderButton(MatrixStack matrix, int x, int y, float ticks) {

        if (renderFancyBorder) {
            mc.getTextureManager()
                .bindTexture(fancyBorderLoc);
            drawTexture(matrix, this.x - 2, this.y - 2, 0, 0, FX, FY);
        }

        RenderUtils.renderStack(stack, this.x, this.y);

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
