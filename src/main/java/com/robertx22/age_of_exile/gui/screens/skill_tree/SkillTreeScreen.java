package com.robertx22.age_of_exile.gui.screens.skill_tree;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.capability.entity.EntityPerks;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.spell_schools.SpellSchool;
import com.robertx22.age_of_exile.database.data.spell_schools.SpellSchool.SchoolType;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.gui.bases.INamedScreen;
import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.ConnectionButton;
import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.PerkButton;
import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.SelectTreeButton;
import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.SpellSchoolButton;
import com.robertx22.age_of_exile.gui.screens.skill_tree.pick_spell_buttons.SpellHotbarButton;
import com.robertx22.age_of_exile.gui.screens.skill_tree.pick_spell_buttons.WholeSpellHotbarButton;
import com.robertx22.age_of_exile.gui.screens.skill_tree.pick_spell_buttons.picking.PickSpellForHotBarButton;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.RequestSyncCapToClient;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.GuiUtils;
import com.robertx22.library_of_exile.utils.GuiUtils.PointF;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.awt.*;
import java.util.List;
import java.util.*;

public abstract class SkillTreeScreen extends BaseScreen implements INamedScreen {
    static Identifier BIG_PANEL = new Identifier(Ref.MODID, "textures/gui/skill_tree/background.png");
    static Identifier SMALL_PANEL = new Identifier(Ref.MODID, "textures/gui/skill_tree/small_panel.png");

    public SchoolType schoolType;

    Identifier BACKGROUND_TEXTURE = new Identifier(
        Ref.MODID, "textures/gui/skill_tree/water.png");

    public SkillTreeScreen(SchoolType type) {
        super(MinecraftClient.getInstance()
            .getWindow()
            .getScaledWidth(), MinecraftClient.getInstance()
            .getWindow()
            .getScaledHeight());
        this.schoolType = type;

    }

    public static double SAVED_SCALE_FACTOR = -1;

    public void removeRemovableButtons() {
        boolean did = false;
        if (this.buttons.removeIf(b -> b instanceof IRemoveOnClickedOutside)) {
            did = true;
        }
        if (this.children.removeIf(b -> b instanceof IRemoveOnClickedOutside)) {
            did = true;
        }
        if (did) {
            this.refreshButtons();
        }
    }

    @Override
    public boolean mouseReleased(double x, double y, int ticks) {

        boolean didClickInside = false;
        for (AbstractButtonWidget b : buttons) {
            if (GuiUtils.isInRectPoints(new Point(b.x, b.y), new Point(b.getWidth(), b.getWidth()),
                new Point((int) x, (int) y)
            )) {
                if (b instanceof IRemoveOnClickedOutside) {
                    didClickInside = true;
                }
            }
        }

        if (!didClickInside) {
            removeRemovableButtons();
        }

        return super.mouseReleased(x, y, ticks);

    }

    public void removePerkButtons() {
        this.buttons.removeIf(x -> x instanceof PerkButton || x instanceof ConnectionButton);
        this.children.removeIf(x -> x instanceof PerkButton || x instanceof ConnectionButton);
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
    HashMap<PointData, PerkButton> pointPerkButtonMap = new HashMap<>();

    public List<SpellSchool> schoolsInOrder;

    public SpellSchool getSchoolByIndexAllowsOutOfBounds(int i) {
        if (i >= schoolsInOrder.size()) {
            return schoolsInOrder.get(i - schoolsInOrder.size());
        }
        if (i < 0) {
            return schoolsInOrder.get(schoolsInOrder.size() + i);
        }
        return schoolsInOrder.get(i);

    }

    public MinecraftClient mc = MinecraftClient.getInstance();

    EntityPerks entityPerks = Load.perks(mc.player);

    public SpellSchool school;

    @Override
    protected void init() {
        super.init();

        try {
            Packets.sendToServer(new RequestSyncCapToClient(PlayerCaps.ENTITY_PERKS));

            schoolsInOrder = SlashRegistry.SpellSchools()
                .getFiltered(x -> {
                    return x.getSchool_type() == this.schoolType;
                });
            schoolsInOrder.sort(Comparator.comparingInt(x -> x.order));

            this.school = schoolsInOrder.get(0);

            refreshButtons();

            goToCenter();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addButtonPublic(AbstractButtonWidget b) {
        this.addButton(b);
    }

    private void addConnections() {

        Set<Set<PointData>> cons = new HashSet<>();

        new ArrayList<>(buttons).forEach(b -> {
            if (b instanceof PerkButton) {
                PerkButton pb = (PerkButton) b;

                Set<PointData> connections = this.school.calcData.connections.getOrDefault(pb.point, new HashSet<>());

                int x1 = pb.x + pb.getWidth() / 2;
                int y1 = pb.y + pb.getHeight() / 2;

                // todo

                int size = 6;
                float spacing = size + size / 2F;

                for (PointData p : connections) {

                    if (cons.stream()
                        .anyMatch(x -> x.contains(p) && x.contains(pb.point))) {
                        continue;
                    }

                    cons.add(Sets.newHashSet(p, pb.point));

                    PerkButton sb = this.pointPerkButtonMap.get(p);

                    if (sb == null) {
                        continue;
                    }

                    int x2 = sb.x + sb.getWidth() / 2;
                    int y2 = sb.y + sb.getHeight() / 2;

                    List<PointF> points = GuiUtils.generateCurve(new PointF(x1, y1), new PointF(x2, y2), 360f, spacing, true);

                    for (PointF point : points) {

                        int x = (int) (point.x - ((float) size / 2));
                        int y = (int) (point.y - ((float) size / 2));

                        this.newButton(new ConnectionButton(school, p, pb.point, x, y));

                    }

                }
            }

        });

    }

    public void refreshButtons() {

        originalButtonLocMap.clear();
        pointPerkButtonMap.clear();
        this.buttons.clear();
        this.children.clear();

        this.scrollX = 0;
        this.scrollY = 0;

        for (Map.Entry<PointData, String> e : school.calcData.perks.entrySet()) {
            Perk perk = SlashRegistry.Perks()
                .get(e.getValue());

            if (perk == null) {
                continue;
            }

            try {
                // centers them if they are smaller than the biggest one
                int addx = (PerkButton.BIGGEST) / 2 - perk
                    .getType().width / 2;
                int addy = (PerkButton.BIGGEST) / 2 - perk
                    .getType().height / 2;

                int subx = PerkButton.BIGGEST / 2;
                int suby = PerkButton.BIGGEST / 2;

                int x = getPosForPoint(e.getKey()).x + addx - subx + SpellSchoolButton.XSIZE / 2;
                int y = getPosForPoint(e.getKey()).y + addy - suby + SpellSchoolButton.YSIZE / 2;

                this.newButton(new PerkButton(entityPerks, school, e.getKey(), perk, x, y));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        int sx = mc.getWindow()
            .getScaledWidth() / 2 - SpellSchoolButton.XSIZE / 2;
        int sy = 0;

        if (this.schoolsInOrder.size() > 1) {

            this.addButton(new SpellSchoolButton(this, school, sx, sy));

            int place = this.schoolsInOrder.indexOf(school);

            for (int i = 0; i < 2; i++) {
                int xadd = SpellSchoolButton.XSIZE * (i + 1) + i + 1;

                int index1 = i + 1;
                int index2 = -i - 1;

                SpellSchool school1 = getSchoolByIndexAllowsOutOfBounds(place + index1);
                SpellSchool school2 = getSchoolByIndexAllowsOutOfBounds(place + index2);

                this.addButton(new SpellSchoolButton(this, school1, sx + xadd, sy));
                this.addButton(new SpellSchoolButton(this, school2, sx - xadd, sy));

                if (i == 1) {
                    this.addButton(new SelectTreeButton(this, SelectTreeButton.LeftOrRight.LEFT, sx - xadd - 15, sy + SpellSchoolButton.YSIZE / 2 - SelectTreeButton.YSIZE / 2));
                    this.addButton(new SelectTreeButton(this, SelectTreeButton.LeftOrRight.RIGHT, sx + xadd + SpellSchoolButton.XSIZE + 1, sy + SpellSchoolButton.YSIZE / 2 - SelectTreeButton.YSIZE / 2));
                }
            }
        }

        if (this.schoolType == SchoolType.SPELLS) {
            this.addButton(new WholeSpellHotbarButton(this, mc.getWindow()
                .getScaledWidth() / 2 - WholeSpellHotbarButton.XSIZE / 2, mc.getWindow()
                .getScaledHeight() - WholeSpellHotbarButton.YSIZE));
        }

        addConnections();

    }

    private Point getPosForPoint(PointData point) {

        float halfx = mc.getWindow()
            .getScaledWidth() / 2F;

        float halfy = mc.getWindow()
            .getScaledHeight() / 2F;

        float x = (point.x - school.calcData.center.x) * PerkButton.SPACING + 2;
        float y = (point.y - school.calcData.center.y) * PerkButton.SPACING + 2;

        x -= SpellSchoolButton.XSIZE / 2F;
        y -= SpellSchoolButton.YSIZE / 2F;

        int tx = (int) (halfx + x);
        int ty = (int) (halfy + y);

        return new Point(tx, ty);

    }

    public void goToCenter() {
        this.scrollX = 0;
        this.scrollY = 0;
    }

    private void newButton(AbstractButtonWidget b) {
        this.addButton(b);
        originalButtonLocMap.put(b, new PointData(b.x, b.y));
        if (b instanceof PerkButton) {
            this.pointPerkButtonMap.put(((PerkButton) b).point, (PerkButton) b);
        }
    }

    float scale = 1;

    int zoomedOutTimes = 0;

    public static boolean RETURN_ZOOM = false;

    @Override
    public void onClose() {

        super.onClose();

        resetZoom();

    }

    @Override
    public void removed() {
        super.removed();

        resetZoom();

    }

    private void resetZoom() {
        if (SAVED_SCALE_FACTOR > 0) {
            mc.getWindow()
                .setScaleFactor(SkillTreeScreen.SAVED_SCALE_FACTOR);
            if (mc.currentScreen != null) {
                mc.currentScreen.resize(mc, mc.getWindow()
                    .getScaledWidth(), mc.getWindow()
                    .getScaledHeight());
            }
            mc.onResolutionChanged();
            SkillTreeScreen.RETURN_ZOOM = false;
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scroll) {
        if (scroll < 0) {
            this.scale -= 0.075F;
            zoomedOutTimes++;
        }
        if (scroll > 0) {
            this.scale += 0.075F;
            zoomedOutTimes--;
        }
        this.scale = MathHelper.clamp(scale, 0.25F, 1);

        this.zoomedOutTimes = MathHelper.clamp(zoomedOutTimes, 0, 25);

        SpellSchool savedschool = this.school;

        int SscrollX = this.scrollX;
        int SscrollY = this.scrollY;

        if (SAVED_SCALE_FACTOR < 0) {
            SAVED_SCALE_FACTOR = mc.getWindow()
                .getScaleFactor();
        }

        if (this.zoomedOutTimes % 2 == 0) {
            mc.getWindow()
                .setScaleFactor(MathHelper.clamp(SAVED_SCALE_FACTOR - zoomedOutTimes * 0.1F, 0.3F, 500));
            resize(mc, mc.getWindow()
                .getScaledWidth(), mc.getWindow()
                .getScaledHeight());

            this.school = savedschool;
            if (this.schoolsInOrder.size() > 1) {
                this.refreshButtons();
            }

            this.scrollX = SscrollX;
            this.scrollY = SscrollY;
        }

        return true;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        this.scrollX += deltaX;
        this.scrollY += deltaY;

        scrollY = MathHelper.clamp(scrollY, -3333, 3333);

        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public void render(MatrixStack matrix, int x, int y, float ticks) {

        try {

            this.buttons.forEach(b -> {
                if (originalButtonLocMap.containsKey(b)) {
                    b.x = (int) (this.originalButtonLocMap.get(b).
                        x + scrollX);
                    b.y = (int) (this.originalButtonLocMap.get(b)
                        .y + scrollY);
                }
            });

            renderBackgroundTexture(0);

            super.render(matrix, x, y, ticks);

            buttons.forEach(b -> {
                if (b instanceof PerkButton) {
                    b.render(matrix, x, y, ticks);
                }
            });

            renderPanels(matrix);

            // we order them here so school buttons are on top, and perks are on top of connection buttons..
            // probably a better way to do it exists?

            buttons.forEach(b -> {

                if (b instanceof IMarkOnTop) {
                    b.render(matrix, x, y, ticks);
                }

            });

            buttons.forEach(b -> {
                if (b instanceof PickSpellForHotBarButton || b instanceof SpellHotbarButton) {
                    b.render(matrix, x, y, ticks);
                }
            });

            this.tick_count++;

            buttons.forEach(b -> b.renderToolTip(matrix, x, y));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void renderPanels(MatrixStack matrix) {

        int BG_HEIGHT = 38;
        MinecraftClient mc = MinecraftClient.getInstance();
        mc.getTextureManager()
            .bindTexture(BIG_PANEL);

        RenderSystem.enableDepthTest();

        int BG_WIDTH = 237;
        int xp = mc.getWindow()
            .getScaledWidth() / 2 - BG_WIDTH / 2;
        int yp = 0;

        if (this.schoolsInOrder.size() > 1) {
            drawTexture(matrix, xp, yp, 0, 0, BG_WIDTH, 39);
        }

        mc.getTextureManager()
            .bindTexture(SMALL_PANEL);
        RenderSystem.enableDepthTest();

        int savedx = xp;
        int savedy = yp;

        // LEFT
        xp = savedx;
        yp = savedy;

        String text = "Points: " + entityPerks.data.getFreePoints(Load.Unit(mc.player));

        int tx = xp - mc.textRenderer.getWidth(text) - 10;
        int yx = yp + BG_HEIGHT / 2 - mc.textRenderer.fontHeight / 2;

        MinecraftClient.getInstance().textRenderer.drawWithShadow(matrix, text, tx, yx, Formatting.GREEN.getColorValue());

        text = "Reset Points: " + entityPerks.data.reset_points;

        tx = savedx + 10 + BG_WIDTH;

        MinecraftClient.getInstance().textRenderer.drawWithShadow(matrix, text, tx, yx, Formatting.GREEN.getColorValue());

    }

    public void renderBackgroundTexture(int vOffset) {
        //copied from Scree

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        this.client.getTextureManager()
            .bindTexture(Screen.BACKGROUND_TEXTURE);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        float f = 32.0F;
        bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
        bufferBuilder.vertex(0.0D, (double) this.height, 0.0D)
            .texture(0.0F, (float) this.height / 32.0F + (float) vOffset)
            .color(64, 64, 64, 255)
            .next();
        bufferBuilder.vertex((double) this.width, (double) this.height, 0.0D)
            .texture((float) this.width / 32.0F, (float) this.height / 32.0F + (float) vOffset)
            .color(64, 64, 64, 255)
            .next();
        bufferBuilder.vertex((double) this.width, 0.0D, 0.0D)
            .texture((float) this.width / 32.0F, (float) vOffset)
            .color(64, 64, 64, 255)
            .next();
        bufferBuilder.vertex(0.0D, 0.0D, 0.0D)
            .texture(0.0F, (float) vOffset)
            .color(64, 64, 64, 255)
            .next();
        tessellator.draw();
    }

}
