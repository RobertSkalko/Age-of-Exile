package com.robertx22.age_of_exile.gui.screens.delve;

import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.tiers.base.Difficulty;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class DifficultyButton extends TexturedButtonWidget {

    public static int xSize = 32;
    public static int ySize = 32;

    static Identifier LOC = Ref.guiId("dungeon/difficulty_button");

    MinecraftClient mc = MinecraftClient.getInstance();

    public Difficulty tier;

    public DifficultyButton(Difficulty tier, int xPos, int yPos) {
        super(xPos + 1, yPos + 1, xSize, ySize, 0, 0, ySize + 1, LOC, (button) -> {
        });
        this.tier = tier;
    }

    @Override
    public void renderToolTip(MatrixStack matrix, int x, int y) {
        if (isInside(x, y)) {

            List<Text> tooltip = new ArrayList<>();

            tooltip.addAll(tier
                .getTooltip());

            tooltip.add(new LiteralText(""));

            for (GearRarity r : ExileDB.GearRarities()
                .getList()) {
                if (r.drops_after_tier > -1) {
                    if (tier.rank >= r.drops_after_tier) {
                        tooltip.add(new LiteralText("").append(r.locName())
                            .append(" items can drop")
                            .formatted(r.textFormatting()));
                    }
                }
            }

            GuiUtils.renderTooltip(matrix, tooltip, x, y);
        }
    }

    public boolean isInside(int x, int y) {
        return GuiUtils.isInRect(this.x, this.y, xSize, ySize, x, y);
    }

}
