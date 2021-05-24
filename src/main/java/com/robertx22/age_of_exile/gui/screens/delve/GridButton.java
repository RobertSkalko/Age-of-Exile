package com.robertx22.age_of_exile.gui.screens.delve;

import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonData;
import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonGridType;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class GridButton extends TexturedButtonWidget {
    static Identifier COMPLETED = Ref.guiId("dungeon/completed");
    static Identifier CURRENT_POS = Ref.guiId("dungeon/current_pos");

    public static int SIZE = 32;

    public PointData point;
    public int origX;
    public int origY;
    MinecraftClient mc = MinecraftClient.getInstance();
    DelveScreen screen;
    boolean isvisible = true;
    DungeonGridType type;

    public GridButton(DelveScreen screen, PointData point, int x, int y, DungeonGridType type, boolean isvisible) {
        super(x, y, 32, 32, 0, 0, 1, DungeonGridType.WALL.icon, 32, 32, (action) -> {
            if (type.isDungeon() && isvisible) {
                DungeonData dun = Load.playerMaps(MinecraftClient.getInstance().player).data.dungeon_datas.get(point);
                MinecraftClient.getInstance()
                    .openScreen(new DungeonInfoScreen(screen.teleporterPos, dun, point));
            }
        });
        this.isvisible = isvisible;
        this.point = point;
        this.type = type;
        this.origX = x;
        this.origY = y;
        this.screen = screen;

    }

    public boolean isInside(int x, int y) {
        return GuiUtils.isInRect((int) (this.x), (int) (this.y), (int) (width), (int) (height), x, y);
    }

    @Override
    public void renderToolTip(MatrixStack matrices, int mouseX, int mouseY) {
        if (this.isInside(mouseX, mouseY)) {
            List<Text> tooltip = new ArrayList<>();

            if (isvisible) {
                if (type.isDungeon()) {
                    tooltip.add(new LiteralText("Dungeon"));
                    tooltip.add(new LiteralText("Click to show info"));
                }
            }

            GuiUtils.renderTooltip(matrices, tooltip, mouseX, mouseY);
        }
    }

    // copied from abstractbutton
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        if (this.active && this.visible) {

            boolean bl = this.clicked(mouseX, mouseY);
            if (bl) {
                this.playDownSound(MinecraftClient.getInstance()
                    .getSoundManager());

                this.onClick(mouseX, mouseY);

                return true;
            }

            return false;
        } else {
            return false;
        }

    }

    @Override
    public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float delta) {

        Identifier icon = type.icon;

        if (type.hide) {
            if (!Load.playerMaps(mc.player).data.playerCanSee(point)) {
                icon = DungeonGridType.WALL.icon;
            }
        }

        mc.getTextureManager()
            .bindTexture(icon);
        drawTexture(matrix, this.x, this.y, 0, 0, 32, 32, 32, 32);

        if (Load.playerMaps(mc.player).data.point_pos.equals(point)) {
            mc.getTextureManager()
                .bindTexture(CURRENT_POS);
            drawTexture(matrix, this.x + 8, this.y + 8, 0, 0, 16, 16, 16, 16);
        }

        if (isvisible && type.isDungeon()) {
            if (Load.playerMaps(mc.player).data.completed.contains(point)) {
                mc.getTextureManager()
                    .bindTexture(COMPLETED);
                drawTexture(matrix, this.x + 8, this.y + 8, 0, 0, 16, 16, 16, 16);
            }
        }
    }
}
