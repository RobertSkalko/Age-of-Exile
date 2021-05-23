package com.robertx22.age_of_exile.gui.screens.delve;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class GridButton extends TexturedButtonWidget {

    public static int SIZE = 32;

    static Identifier ID = Ref.guiId("dungeon/wall");
    static Identifier DUN = Ref.guiId("dungeon/dungeon");

    public PointData point;

    boolean isDungeon = false;

    public int origX;
    public int origY;
    MinecraftClient mc = MinecraftClient.getInstance();
    DelveScreen screen;

    public GridButton(DelveScreen screen, PointData point, int x, int y, boolean isdung) {
        super(x, y, 32, 32, 0, 0, 1, ID, 32, 32, (action) -> {
            if (isdung) {
                screen.addSelectedDungeonButtons();
            }
        });
        this.point = point;
        this.isDungeon = isdung;
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
            //GuiUtils.renderTooltip(matrices, tooltip, mouseX, mouseY);
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
        super.renderButton(matrix, x, y, delta);

        if (isDungeon) {
            mc.getTextureManager()
                .bindTexture(DUN);
            drawTexture(matrix, this.x, this.y, 0, 0, 32, 32, 32, 32);
        }
    }
}
