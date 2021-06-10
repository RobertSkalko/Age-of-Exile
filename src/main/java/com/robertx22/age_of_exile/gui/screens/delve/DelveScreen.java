package com.robertx22.age_of_exile.gui.screens.delve;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.dimension.delve_gen.DelveGrid;
import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonData;
import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonGridType;
import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.RequestSyncCapToClient;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.awt.*;
import java.util.HashMap;

public class DelveScreen extends BaseScreen {

    public BlockPos teleporterPos = new BlockPos(0, 0, 0);

    public DelveScreen(BlockPos pos) {
        super(MinecraftClient.getInstance()
            .getWindow()
            .getScaledWidth(), MinecraftClient.getInstance()
            .getWindow()
            .getScaledHeight());
        this.teleporterPos = pos;

        Load.playerMaps(mc.player)
            .syncToClient(mc.player);

    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 32) { // space
            this.goToCenter();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);

    }

    int tick_count = 0;
    int scrollX = 0;
    int scrollY = 0;

    HashMap<AbstractButtonWidget, PointData> originalButtonLocMap = new HashMap<>();

    public MinecraftClient mc = MinecraftClient.getInstance();

    PointData center = new PointData(0, 0);
    public DungeonData selectedDungeon = null;

    @Override
    protected void init() {
        super.init();

        try {
            Packets.sendToServer(new RequestSyncCapToClient(PlayerCaps.MAPS));

            refreshButtons();

            goToCenter();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addButtonPublic(AbstractButtonWidget b) {
        this.addButton(b);
    }

    public void refreshButtons() {

        DelveGrid grid = Load.playerMaps(mc.player).data.grid;

        center = Load.playerMaps(mc.player).data.point_pos;

        for (int x = 0; x < grid.grid.length; x++) { // add some more to make the endings not crop into nothing
            for (int y = 0; y < grid.grid.length; y++) {

                PointData point = new PointData(x, y);
                Point pos = getPosForPoint(point);
                if (grid.isInRange(point)) {
                    boolean visible = Load.playerMaps(mc.player).data.playerCanSee(point);
                    DungeonGridType type = grid.getGridType(point);
                    newButton(new GridButton(this, point, pos.x, pos.y, type, visible));
                }
                // newButton(new GridButton(this, point, pos.x, pos.y, DungeonGridType.BORDER, true));
            }
        }

    }

    private Point getPosForPoint(PointData point) {
        return new Point(GridButton.SIZE * point.x, GridButton.SIZE * point.y);
    }

    public void goToCenter() {
        this.scrollX = -getPosForPoint(center).x + width / 2;
        this.scrollY = -getPosForPoint(center).y + height / 2;
    }

    private void newButton(AbstractButtonWidget b) {
        this.addButton(b);
        originalButtonLocMap.put(b, new PointData(b.x, b.y));

    }

    @Override
    public void onClose() {
        super.onClose();
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        this.scrollX += deltaX;
        this.scrollY += deltaY;
        scrollY = MathHelper.clamp(scrollY, -2000, 2000);
        scrollX = MathHelper.clamp(scrollX, -2000, 2000);

        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public void render(MatrixStack matrix, int x, int y, float ticks) {

        try {

            renderBackgroundIcon(BACKGROUND, this, 0);

            this.buttons.forEach(b -> {

                if (originalButtonLocMap.containsKey(b)) {
                    b.x = (this.originalButtonLocMap.get(b).
                        x);
                    b.y = (this.originalButtonLocMap.get(b)
                        .y);

                    b.x += scrollX;
                    b.y += scrollY;

                }
            });

            super.render(matrix, x, y, ticks);

            this.tick_count++;

        } catch (Exception e) {
            e.printStackTrace();
        }

        for (AbstractButtonWidget b : buttons) {
            b.renderToolTip(matrix, x, y);
        }

        //watch.print(" rendering ");
    }

    static Identifier BACKGROUND = Ref.guiId("dungeon/background");

    public static void renderBackgroundIcon(Identifier texture, Screen screen, int vOffset) {

        //copied from Scree

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        MinecraftClient.getInstance()
            .getTextureManager()
            .bindTexture(texture);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
        bufferBuilder.vertex(0.0D, (double) screen.height, 0.0D)
            .texture(0.0F, (float) screen.height / 32.0F + (float) vOffset)
            .color(64, 64, 64, 255)
            .next();
        bufferBuilder.vertex((double) screen.width, (double) screen.height, 0.0D)
            .texture((float) screen.width / 32.0F, (float) screen.height / 32.0F + (float) vOffset)
            .color(64, 64, 64, 255)
            .next();
        bufferBuilder.vertex((double) screen.width, 0.0D, 0.0D)
            .texture((float) screen.width / 32.0F, (float) vOffset)
            .color(64, 64, 64, 255)
            .next();
        bufferBuilder.vertex(0.0D, 0.0D, 0.0D)
            .texture(0.0F, (float) vOffset)
            .color(64, 64, 64, 255)
            .next();
        tessellator.draw();
    }

}
