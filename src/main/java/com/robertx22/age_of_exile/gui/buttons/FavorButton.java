package com.robertx22.age_of_exile.gui.buttons;

import com.robertx22.age_of_exile.database.data.favor.FavorRank;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class FavorButton extends TexturedButtonWidget {
    private static final Identifier HIGH = new Identifier(Ref.MODID, "textures/gui/favor/high.png");
    private static final Identifier LOW = new Identifier(Ref.MODID, "textures/gui/favor/low.png");
    private static final Identifier NOTHING = new Identifier(Ref.MODID, "textures/gui/favor/nothing.png");

    public static int FAVOR_BUTTON_SIZE_X = 34;
    public static int FAVOR_BUTTON_SIZE_Y = 34;

    MinecraftClient mc = MinecraftClient.getInstance();

    public FavorButton(int xPos, int yPos) {
        super(xPos, yPos, FAVOR_BUTTON_SIZE_X, FAVOR_BUTTON_SIZE_Y, 0, 0, FAVOR_BUTTON_SIZE_Y, HIGH, (button) -> {
        });

    }

    @Override
    public void renderToolTip(MatrixStack matrix, int x, int y) {
        if (isInside(x, y)) {
            List<Text> tooltip = new ArrayList<>();

            FavorRank rank = Load.favor(mc.player)
                .getRank();

            tooltip.add(Words.Favor.locName()
                .append(": " + Load.favor(mc.player)
                    .getFavor())
                .formatted(Formatting.WHITE)
                .formatted(Formatting.BOLD));

            tooltip.add(new LiteralText(""));

            tooltip.addAll(rank.getTooltip());

            GuiUtils.renderTooltip(matrix,
                tooltip, x, y);

        }
    }

    public boolean isInside(int x, int y) {
        return GuiUtils.isInRect(this.x, this.y, FAVOR_BUTTON_SIZE_X, FAVOR_BUTTON_SIZE_Y, x, y);
    }

    @Override
    public void renderButton(MatrixStack matrix, int x, int y, float f) {

        int current = (int) Load.favor(mc.player)
            .getFavor();

        Identifier TEX;

        if (current < 1) {
            TEX = NOTHING;
        } else if (current < 1000) {
            TEX = LOW;
        } else {
            TEX = HIGH;
        }

        mc.getTextureManager()
            .bindTexture(TEX);
        drawTexture(matrix, this.x, this.y, 0, 0, this.width, this.height, width, height);
        //drawTexture(matrix, this.x, this.y, 0, yadd, (int) (this.width * perc), this.height);

        //FavorScreen.this.drawStringWithShadow(matrix, font, str, this.x + BAR_BUTTON_SIZE_X / 2 - font.getWidth(str) / 2, this.y + 2, Formatting.GOLD.getColorValue());

    }

}