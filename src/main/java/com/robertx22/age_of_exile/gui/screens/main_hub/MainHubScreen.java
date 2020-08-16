package com.robertx22.age_of_exile.gui.screens.main_hub;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.gui.bases.IAlertScreen;
import com.robertx22.age_of_exile.gui.bases.IContainerNamedScreen;
import com.robertx22.age_of_exile.gui.bases.INamedScreen;
import com.robertx22.age_of_exile.gui.screens.new_stat_screen.StatScreen;
import com.robertx22.age_of_exile.gui.screens.spell_hotbar_setup.HotbarNamedScreen;
import com.robertx22.age_of_exile.gui.screens.stat_alloc.StatAllocationScreen;
import com.robertx22.age_of_exile.gui.screens.stats_overview.StatOverviewScreen;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.GuiUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class MainHubScreen extends BaseScreen implements INamedScreen {

    Identifier BACKGROUND_TEXTURE = new Identifier(Ref.MODID, "textures/gui/main_hub/window.png");
    public static Identifier EXLAMATION_MARK_TEX = new Identifier(
        Ref.MODID, "textures/gui/main_hub/exclamation_mark.png");

    public MinecraftClient mc;

    static int x = 318;
    static int y = 232;

    public MainHubScreen() {
        super(x, y);
        this.mc = MinecraftClient.getInstance();
        this.data = Load.Unit(mc.player);
    }

    @Override
    protected void init() {
        super.init();

        List<INamedScreen> screens = new ArrayList<>();

        screens.add(new HotbarNamedScreen());
        screens.add(new StatOverviewScreen());
        screens.add(new StatAllocationScreen());
        screens.add(new StatScreen());

        int x = guiLeft + 10;
        int y = guiTop + 45;

        int count = 0;

        for (INamedScreen screen : screens) {

            if (count >= 3) {
                y += Button.ySize + 5;
                x = guiLeft + 9;
                count = 0;
            }
            if (count >= 1) {
                x += Button.xSize + 5;
            }
            count++;

            addButton(new Button(screen, x, y));

        }

    }

    @Override
    public void render(MatrixStack matrix, int x, int y, float ticks) {

        drawBackground(matrix);

        super.render(matrix, x, y, ticks);

        renderTitle(matrix);
        renderLevelExp(matrix);

    }

    private void renderTitle(MatrixStack matrix) {
        double scale = 1.8F;
        String str = "Main Hub";
        int xp = (int) (guiLeft + (MainHubScreen.x / 2));
        int yp = (int) (guiTop + 20);
        GuiUtils.renderScaledText(matrix, xp, yp, scale, str, Formatting.YELLOW);
    }

    EntityCap.UnitData data;

    private void renderLevelExp(MatrixStack matrix) {

        double scale = 1.5;
        String str = "Level: " + data.getLevel() + ", Exp: " + data.getExp() + "/" + data.getExpRequiredForLevelUp();
        int xp = (int) (guiLeft + (MainHubScreen.x / 2));
        int yp = (int) (guiTop + 200);
        GuiUtils.renderScaledText(matrix, xp, yp, scale, str, Formatting.GREEN);
    }

    protected void drawBackground(MatrixStack matrix) {
        MinecraftClient.getInstance()
            .getTextureManager()
            .bindTexture(BACKGROUND_TEXTURE);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexture(matrix, guiLeft, guiTop, this.getZOffset(), 0.0F, 0.0F, this.x, this.y, 256, 512);

    }

    @Override
    public Identifier iconLocation() {
        return new Identifier(Ref.MODID, "textures/gui/main_hub/icons/map_info.png");
    }

    @Override
    public Words screenName() {
        return Words.MapInfo;
    }

    static class Button extends TexturedButtonWidget {

        public static int xSize = 95;
        public static int ySize = 32;

        boolean shouldAlert = false;

        static Identifier buttonLoc = new Identifier(Ref.MODID, "textures/gui/main_hub/buttons.png");

        INamedScreen screen;

        public Button(INamedScreen screen, int xPos, int yPos) {
            super(xPos, yPos, xSize, ySize, 0, 0, ySize + 1, buttonLoc, (button) -> {
                if (screen instanceof IContainerNamedScreen) {
                    IContainerNamedScreen con = (IContainerNamedScreen) screen;
                    con.openContainer();
                } else {
                    MinecraftClient.getInstance()
                        .openScreen((Screen) screen);
                }
            });

            this.screen = screen;

            if (screen instanceof IAlertScreen) {
                IAlertScreen alert = (IAlertScreen) screen;
                this.shouldAlert = alert.shouldAlert();
            }

        }

        @Override
        public void renderButton(MatrixStack matrix, int x, int y, float ticks) {
            super.renderButton(matrix, x, y, ticks);

            RenderUtils.render16Icon(matrix, screen.iconLocation(), this.x + 9, this.y + 7);

            if (shouldAlert) {
                RenderUtils.render16Icon(matrix, EXLAMATION_MARK_TEX, this.x + 5, this.y + 7);
            }

            String str = screen.screenName()
                .translate();

            MinecraftClient.getInstance().textRenderer.drawWithShadow(matrix,
                str, this.x + 30, this.y + 10, Formatting.GREEN.getColorValue());
        }

    }

}

