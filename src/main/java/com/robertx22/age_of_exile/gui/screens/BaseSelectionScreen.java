package com.robertx22.age_of_exile.gui.screens;

import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.gui.screens.skill_tree.SkillTreeScreen;
import com.robertx22.age_of_exile.saveclasses.PointData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;

import java.util.HashMap;

public class BaseSelectionScreen extends BaseScreen {

    public MinecraftClient mc;

    public BaseSelectionScreen() {
        super(MinecraftClient.getInstance()
            .getWindow()
            .getScaledWidth(), MinecraftClient.getInstance()
            .getWindow()
            .getScaledHeight());
        this.mc = MinecraftClient.getInstance();

    }

    int scrollY = 0;

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {

        // this.scrollY += deltaY;

        //scrollY = MathHelper.clamp(scrollY, -3333, 3333);

        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    public void goToCenter() {

        this.scrollY = 0;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 32) { // space
            this.goToCenter();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);

    }

    HashMap<ClickableWidget, PointData> originalButtonLocMap = new HashMap<>();

    @Override
    protected <T extends ClickableWidget> T addButton(T b) {
        super.addButton(b);
        originalButtonLocMap.put(b, new PointData(b.x, b.y));
        return b;
    }

    @Override
    public void render(MatrixStack matrix, int x, int y, float ticks) {

        this.buttons.forEach(b -> {
            if (originalButtonLocMap.containsKey(b)) {
                b.y = (this.originalButtonLocMap.get(b)
                    .y + scrollY);
            }
        });

        SkillTreeScreen.renderBackgroundDirt(this, 0);

        super.render(matrix, x, y, ticks);

        this.buttons.forEach(b -> b.renderToolTip(matrix, x, y));
    }

}


