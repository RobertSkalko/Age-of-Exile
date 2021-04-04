package com.robertx22.age_of_exile.gui.buttons;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.BaseModificationStation;
import com.robertx22.library_of_exile.utils.CLOC;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class OwnerButton extends TexturedButtonWidget {

    public static int SIZE_X = 80;
    public static int SIZE_Y = 16;

    static Identifier ID = Ref.guiId("owner_button");

    MinecraftClient mc = MinecraftClient.getInstance();

    BaseModificationStation tile;

    public OwnerButton(BaseModificationStation tile, int xPos, int yPos) {
        super(xPos, yPos, SIZE_X, SIZE_Y, 0, 0, SIZE_Y, ID, (button) -> {
        });
        this.tile = tile;

    }

    @Override
    public void renderToolTip(MatrixStack matrix, int x, int y) {
        if (isInside(x, y)) {
            List<Text> tooltip = new ArrayList<>();

            tooltip.add(new LiteralText("This is the owner of the station."));
            tooltip.add(new LiteralText("The station can craft things based on the owner's skill levels."));
            tooltip.add(new LiteralText("Owner gets experience when things are crafted here."));
            tooltip.add(new LiteralText("The station won't work if the owner is offline."));

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

            txt = "No owner";
        }

        mc.textRenderer.drawWithShadow(matrix, txt, this.x + (SIZE_X / 2F - (mc.textRenderer.getWidth(txt) / 2F)), this.y + 3, Formatting.WHITE.getColorValue());
    }

}