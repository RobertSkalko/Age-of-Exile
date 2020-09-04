package com.robertx22.age_of_exile.gui.screens.skill_tree;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.perks.PerkStatus;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RenderUtils;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class PerkButton extends TexturedButtonWidget {

    public static int SPACING = 28;
    public static int BIGGEST = 26;

    static Identifier ID = new Identifier(Ref.MODID, "textures/gui/skill_tree/perk_buttons.png");

    public Perk perk;
    PerkStatus status;

    public PerkButton(Perk perk, int x, int y) {
        super(x, y, perk.getType().width, perk.getType().height, 0, 0, 1, ID, (action) -> {
        });
        this.perk = perk;

        this.status = PerkStatus.CONNECTED; // TODO NEED CAP FOR THIS
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

        // background
        RenderSystem.enableDepthTest();
        drawTexture(matrices, this.x, this.y, perk.getType()
            .getXOffset(), status
            .getYOffset(), this.width, this.height);

        if (this.perk.getType() == Perk.PerkType.STAT) {
            // icon
            mc.getTextureManager()
                .bindTexture(this.perk.getIcon());
            drawTexture(matrices, this.x + 4, this.y + 4, 0, 0, 16, 16, 16, 16);
        } else if (this.perk.getType() == Perk.PerkType.SPELL) {
            // icon
            mc.getTextureManager()
                .bindTexture(this.perk.getIcon());

            RenderUtils.render16Icon(matrices, perk.getIcon(), this.x + 4, this.y + 4);
            //drawTexture(matrices, this.x + 8, this.y + 8, 0, 0, 32, 32, 32, 32);
        } else if (perk.getType() == Perk.PerkType.START) {

        } else {
            // icon
            mc.getTextureManager()
                .bindTexture(this.perk.getIcon());
            drawTexture(matrices, this.x + 4, this.y + 4, 0, 0, 16, 16, 16, 16);
        }
    }

}