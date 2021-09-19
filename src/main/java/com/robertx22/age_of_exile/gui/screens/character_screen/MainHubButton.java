package com.robertx22.age_of_exile.gui.screens.character_screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.robertx22.age_of_exile.gui.bases.IAlertScreen;
import com.robertx22.age_of_exile.gui.bases.IContainerNamedScreen;
import com.robertx22.age_of_exile.gui.bases.INamedScreen;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.library_of_exile.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class MainHubButton extends ImageButton {

    public static int xSize = 105;
    public static int ySize = 28;
    public static ResourceLocation EXLAMATION_MARK_TEX = new ResourceLocation(
        Ref.MODID, "textures/gui/main_hub/exclamation_mark.png");

    boolean shouldAlert = false;

    static ResourceLocation buttonLoc = new ResourceLocation(Ref.MODID, "textures/gui/main_hub/buttons.png");

    INamedScreen screen;

    public MainHubButton(INamedScreen screen, int xPos, int yPos) {
        super(xPos, yPos, xSize, ySize, 0, 0, ySize + 1, buttonLoc, (button) -> {
            if (screen instanceof IContainerNamedScreen) {
                IContainerNamedScreen con = (IContainerNamedScreen) screen;
                con.openContainer();
            } else {
                Minecraft.getInstance()
                    .setScreen((Screen) screen);
            }
        });

        this.screen = screen;

        if (screen instanceof IAlertScreen) {
            IAlertScreen alert = (IAlertScreen) screen;
            this.shouldAlert = alert.shouldAlert();
        }

    }

    @Override
    public void renderButton(MatrixStack matrix, int x, int y, float ticks) {
        super.renderButton(matrix, x, y, ticks);

        RenderUtils.render16Icon(matrix, screen.iconLocation(), this.x + 9, this.y + 6);

        if (shouldAlert) {
            RenderUtils.render16Icon(matrix, EXLAMATION_MARK_TEX, this.x + 5, this.y + 6);
        }

        String str = screen.screenName()
            .translate();

        if (isHovered()) {
            Minecraft.getInstance().font.drawShadow(matrix,
                str, this.x + 32, this.y + 9, TextFormatting.GREEN.getColor());
        }
    }

}
