package com.robertx22.age_of_exile.dimension.gui;

import com.robertx22.age_of_exile.database.data.tiers.base.Tier;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class DifficultyButton extends TexturedButtonWidget {

    public static int xSize = 32;
    public static int ySize = 32;

    static Identifier LOC = Ref.guiId("dungeon/difficulty_button");

    MinecraftClient mc = MinecraftClient.getInstance();

    Tier tier = new Tier(0);

    public DifficultyButton(Tier tier, int xPos, int yPos) {
        super(xPos + 1, yPos + 1, xSize, ySize, 0, 0, ySize + 1, LOC, (button) -> {
        });
        this.tier = tier;
    }

    @Override
    public void renderToolTip(MatrixStack matrix, int x, int y) {
        if (isInside(x, y)) {
            List<Text> tooltip = new ArrayList<>();
            tooltip.addAll(tier.getTooltip());
            GuiUtils.renderTooltip(matrix, tooltip, x, y);
        }
    }

    public boolean isInside(int x, int y) {
        return GuiUtils.isInRect(this.x, this.y, xSize, ySize, x, y);
    }

}
