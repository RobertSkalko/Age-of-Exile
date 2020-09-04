package com.robertx22.age_of_exile.gui.screens.skill_tree;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class PerkButton extends TexturedButtonWidget {

    public static int SPACING = 29;

    static Identifier ID = new Identifier(Ref.MODID, "textures/gui/skill_tree/perk_buttons.png");

    public Perk perk;

    public PerkButton(Perk perk, int x, int y) {
        super(x, y, perk.getType().width, perk.getType().height, 0, 0, 1, ID, (action) -> {
        });
        this.perk = perk;
    }

    public boolean isInside(int x, int y) {
        return GuiUtils.isInRect(this.x, this.y, width, height, x, y);
    }

    @Override
    public void renderToolTip(MatrixStack matrices, int mouseX, int mouseY) {
        if (this.isInside(mouseX, mouseY)) {
            //GuiUtils.renderTooltip(matrices, this.tooltip, mouseX, mouseY);
        }

    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        MinecraftClient mc = MinecraftClient.getInstance();
        mc.getTextureManager()
            .bindTexture(ID);

        RenderSystem.enableDepthTest();
        drawTexture(matrices, this.x, this.y, perk.getType()
            .getXOffset(), perk.getType()
            .getYOffset(), this.width, this.height, this.width, this.height);
    }

}