package com.robertx22.age_of_exile.dimension.teleporter.delve_gui;

import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonData;
import com.robertx22.age_of_exile.dimension.packets.StartDungeonPacket;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.library_of_exile.main.Packets;
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

public class StartDungeonButton extends TexturedButtonWidget {

    public static int SIZE_X = 80;
    public static int SIZE_Y = 16;

    static Identifier ID = Ref.guiId("owner_button");

    MinecraftClient mc = MinecraftClient.getInstance();

    DungeonData dungeon;

    public StartDungeonButton(MapsScreen screen, DungeonData dungeon, int xPos, int yPos) {
        super(xPos, yPos, SIZE_X, SIZE_Y, 0, 0, SIZE_Y, ID, (button) -> {
            Packets.sendToServer(new StartDungeonPacket(screen.teleporterPos, dungeon));
        });
        this.dungeon = dungeon;
    }

    @Override
    public void renderToolTip(MatrixStack matrix, int x, int y) {
        if (isInside(x, y)) {
            List<Text> tooltip = new ArrayList<>();
            tooltip.add(new LiteralText("Choose this dungeon to start."));
            tooltip.add(new LiteralText("You won't be able to play others on the same floor."));
            GuiUtils.renderTooltip(matrix, tooltip, x, y);

        }
    }

    public boolean isInside(int x, int y) {
        return GuiUtils.isInRect(this.x, this.y, SIZE_X, SIZE_Y, x, y);
    }

    @Override
    public void renderButton(MatrixStack matrix, int x, int y, float f) {
        super.renderButton(matrix, x, y, f);

        String txt = "Start";

        int width = mc.textRenderer.getWidth(txt);

        mc.textRenderer.drawWithShadow(matrix, txt, this.x + (SIZE_X / 2F - (width / 2F)), this.y + 3, Formatting.WHITE.getColorValue());
    }

}
