package com.robertx22.mine_and_slash.gui.screens.stat_alloc;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.capability.player.PlayerStatsCap;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.types.core_stats.Dexterity;
import com.robertx22.mine_and_slash.database.data.stats.types.core_stats.Intelligence;
import com.robertx22.mine_and_slash.database.data.stats.types.core_stats.Strength;
import com.robertx22.mine_and_slash.database.data.stats.types.core_stats.base.BaseCoreStat;
import com.robertx22.mine_and_slash.gui.bases.BaseScreen;
import com.robertx22.mine_and_slash.gui.bases.IAlertScreen;
import com.robertx22.mine_and_slash.gui.bases.INamedScreen;
import com.robertx22.mine_and_slash.mmorpg.Packets;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.localization.CLOC;
import com.robertx22.mine_and_slash.uncommon.localization.Styles;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.GuiUtils;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.mine_and_slash.vanilla_mc.packets.SpendStatPointsPacket;
import com.robertx22.mine_and_slash.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.mine_and_slash.vanilla_mc.packets.sync_cap.RequestSyncCapToClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatAllocationScreen extends BaseScreen implements INamedScreen, IAlertScreen {

    private static final Identifier TEXTURE = new Identifier(
        Ref.MODID, "textures/gui/stat_point_screen.png");
    static int sizeY = 220;
    static int sizeX = 215;

    public StatAllocationScreen() {
        super(sizeX, sizeY);
    }

    PlayerStatsCap.IPlayerStatPointsData data;
    EntityCap.UnitData unitdata;

    int guiLeft = 0;
    int guiTop = 0;

    @Override
    public Identifier iconLocation() {
        return new Identifier(Ref.MODID, "textures/gui/main_hub/icons/stat_points.png");
    }

    @Override
    public boolean mouseReleased(double x, double y, int ticks) {

        buttons.forEach(b -> {
            if (GuiUtils.isInRectPoints(new Point(b.x, b.y), new Point(b.getWidth(), b.getWidth()),
                new Point((int) x, (int) y)
            )) {
                b.onClick(x, y);
            }
        });

        return super.mouseReleased(x, y, ticks);

    }

    @Override
    public Words screenName() {
        return Words.StatPoints;
    }

    @Override
    protected void init() {
        super.init();

        Packets.sendToServer(new RequestSyncCapToClient(PlayerCaps.STAT_POINTS));

        data = Load.statPoints(MinecraftClient.getInstance().player);
        unitdata = Load.Unit(MinecraftClient.getInstance().player);

        if (data == null || unitdata == null) {
            this.onClose();
            return;
        }
        //LvlPointStat

        this.guiLeft = (this.width - sizeX) / 2;
        this.guiTop = (this.height - sizeY) / 2;

        int y = 0;

        for (Stat single : Arrays.asList(Dexterity.INSTANCE, Strength.INSTANCE, Intelligence.INSTANCE)) {
            this.buttons.add(new IncreaseStatButton(unitdata, data, single, guiLeft + sizeX / 2 + 50, guiTop + 40 + y));
            y += button_sizeY + 3;
        }
    }

    @Override
    public void render(int x, int y, float ticks) {

        drawGuiBackgroundLayer(ticks, x, y);
        super.render(x, y, ticks);

        String str = "Stat Points Remaining: " + data.getAvailablePoints(Load.Unit(minecraft.player));

        MinecraftClient.getInstance().textRenderer.drawWithShadow(str,
            guiLeft + sizeX / 2 + 50 - MinecraftClient.getInstance().textRenderer
                .getStringWidth(str), guiTop + 15,
            Formatting.GREEN.getColorValue()
        );

        buttons.forEach(b -> b.renderToolTip(x, y));

    }

    protected void drawGuiBackgroundLayer(float partialTicks, int x, int y) {

        minecraft.getTextureManager()
            .bindTexture(TEXTURE);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.blit(minecraft.getWindow()
                .getScaledWidth() / 2 - this.sizeX / 2,
            minecraft.getWindow()
                .getScaledHeight() / 2 - this.sizeY / 2, 0, 0, sizeX, sizeY
        );

    }

    private static final Identifier BUTTON_TEX = new Identifier(Ref.MODID, "textures/gui/button.png");
    static int button_sizeX = 13;
    static int button_sizeY = 13;

    @Override
    public boolean shouldAlert() {
        try {
            return Load.statPoints(MinecraftClient.getInstance().player)
                .getAvailablePoints(Load.Unit(MinecraftClient.getInstance().player)) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    class IncreaseStatButton extends TexturedButtonWidget {

        TextRenderer font = MinecraftClient.getInstance().textRenderer;
        PlayerStatsCap.IPlayerStatPointsData data;
        Stat stat;
        EntityCap.UnitData unitdata;

        public IncreaseStatButton(EntityCap.UnitData unitdata, PlayerStatsCap.IPlayerStatPointsData data,
                                  Stat stat, int xPos, int yPos) {
            super(xPos, yPos, button_sizeX, button_sizeY, 0, 0, button_sizeY, BUTTON_TEX, (button) -> {

                // MMORPG.sendToServer(new SpendStatPointPacket(statData.stat));
                //MMORPG.sendToServer(new RequestSyncCapToClient(CapTypes.STAT_POINTS));

            });

            this.data = data;
            this.stat = stat;
            this.unitdata = unitdata;

        }

        @Override
        public void onPress() {
            super.onPress();

            Packets.sendToServer(new SpendStatPointsPacket(this.stat));
            Packets.sendToServer(new RequestSyncCapToClient(PlayerCaps.STAT_POINTS));

        }

        @Override
        public void renderToolTip(int x, int y) {
            if (isInside(x, y)) {

                List<Text> tooltip = new ArrayList<>();

                tooltip.add(Styles.BLUECOMP()
                    .append(stat.locName()));

                if (stat instanceof BaseCoreStat) {
                    BaseCoreStat core = (BaseCoreStat) stat;
                    tooltip.addAll(core.getCoreStatTooltip(unitdata, unitdata.getUnit()
                        .getCreateStat(stat)));
                }

                StatAllocationScreen.this.renderTooltip(TooltipUtils.compsToStrings(tooltip), x, y);

            }
        }

        public boolean isInside(int x, int y) {
            return GuiUtils.isInRect(this.x, this.y, button_sizeX, button_sizeY, x, y);
        }

        @Override
        public void renderButton(int x, int y, float f) {
            try {
                super.renderButton(x, y, f);

                Formatting format = Formatting.YELLOW;

                String str =
                    stat.getIconFormat() + CLOC.translate(stat.locName()) + format + ": " + Formatting.GREEN + format;
                str += ", Current(" + Formatting.GREEN + (int) this.unitdata.getUnit()
                    .getCreateStat(stat)
                    .getAverageValue() + format + ")";

                font.drawWithShadow(str, this.x - button_sizeX - 5 - font.getStringWidth(str),
                    this.y - button_sizeY / 2 + font.fontHeight, format.getColorValue()
                );
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}