package com.robertx22.age_of_exile.gui.screens.wiki;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.utils.RenderUtils;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;

public class WikiTypeButton extends ImageButton {

    public static int xSize = 20;
    public static int ySize = 20;

    static ResourceLocation buttonLoc = new ResourceLocation(SlashRef.MODID, "textures/gui/clear_button.png");

    ResourceLocation icon;

    WikiType type;

    public WikiTypeButton(WikiScreen screen, WikiType type, int xPos, int yPos) {
        super(xPos, yPos, xSize, ySize, 0, 0, ySize + 1, buttonLoc, (button) -> {
            screen.changeType(type);
        });
        this.type = type;
        this.icon = type.getIconLoc();

    }

    @Override
    public void renderButton(MatrixStack matrix, int x, int y, float ticks) {
        super.renderButton(matrix, x, y, ticks);
        RenderUtils.render16Icon(matrix, icon, this.x + 2, this.y + 2);
    }

}

