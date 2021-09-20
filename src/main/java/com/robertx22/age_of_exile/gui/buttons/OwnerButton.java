package com.robertx22.age_of_exile.gui.buttons;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.BaseModificationStation;
import com.robertx22.library_of_exile.utils.CLOC;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class OwnerButton extends ImageButton {

    public static int SIZE_X = 80;
    public static int SIZE_Y = 16;

    static ResourceLocation ID = SlashRef.guiId("owner_button");

    Minecraft mc = Minecraft.getInstance();

    BaseModificationStation tile;

    public OwnerButton(BaseModificationStation tile, int xPos, int yPos) {
        super(xPos, yPos, SIZE_X, SIZE_Y, 0, 0, SIZE_Y, ID, (button) -> {
        });
        this.tile = tile;

    }

    @Override
    public void renderToolTip(MatrixStack matrix, int x, int y) {
        if (isInside(x, y)) {
            List<ITextComponent> tooltip = new ArrayList<>();

            tooltip.add(new StringTextComponent("This is the owner of the station."));
            tooltip.add(new StringTextComponent("The station can craft things based on the owner's skill levels."));
            tooltip.add(new StringTextComponent("Owner gets experience when things are crafted here."));
            tooltip.add(new StringTextComponent("The station won't work if the owner is offline."));

            GuiUtils.renderTooltip(matrix, tooltip, x, y);

        }
    }

    public boolean isInside(int x, int y) {
        return GuiUtils.isInRect(this.x, this.y, SIZE_X, SIZE_Y, x, y);
    }

    @Override
    public void renderButton(MatrixStack matrix, int x, int y, float f) {

        super.renderButton(matrix, x, y, f);

        String txt = "";

        PlayerEntity owner = tile.getOwner();

        if (owner != null) {
            txt = CLOC.translate(owner.getDisplayName());
        } else {

            txt = "No Owner";
        }

        int width = mc.font.width(txt);

        boolean cut = false;

        while (width > (SIZE_X - 10)) {
            cut = true;

            txt = txt.substring(0, txt.length() - 2);

            width = mc.font.width(txt);

        }
        if (cut) {
            txt += "...";
        }

        mc.font.drawShadow(matrix, txt, this.x + (SIZE_X / 2F - (mc.font.width(txt) / 2F)), this.y + 3, TextFormatting.WHITE.getColor());
    }

}