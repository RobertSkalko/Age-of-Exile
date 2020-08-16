package com.robertx22.age_of_exile.gui.screens.new_stat_screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.capability.player.PlayerStatsCap;
import com.robertx22.age_of_exile.database.data.stats.IUsableStat;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.UnknownStat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Dexterity;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Intelligence;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Strength;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.BaseCoreStat;
import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.gui.bases.INamedScreen;
import com.robertx22.age_of_exile.mmorpg.Packets;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Styles;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.GuiUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.NumberUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RenderUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.SpendStatPointsPacket;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.RequestSyncCapToClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class StatScreen extends BaseScreen implements INamedScreen {

    static int sizeX = 176;
    static int sizeY = 166;

    MinecraftClient mc = MinecraftClient.getInstance();

    public StatScreen() {
        super(sizeX, sizeY);
    }

    @Override
    public Identifier iconLocation() {
        return new Identifier(Ref.MODID, "textures/gui/main_hub/icons/stats_screen.png");
    }

    @Override
    public Words screenName() {
        return Words.Stats;
    }

    @Override
    public void init() {
        super.init();

        EntityCap.UnitData data = Load.Unit(mc.player);
        PlayerStatsCap.IPlayerStatPointsData stats = Load.statPoints(mc.player);

        int spacing = 27;

        int xpos = guiLeft + 95;
        int ypos = guiTop + 10;

        buttons.add(new StatButton(data, Dexterity.INSTANCE, xpos, ypos));
        buttons.add(new IncreaseStatButton(data, stats, Dexterity.INSTANCE, xpos, ypos + 15));

        xpos += spacing;
        buttons.add(new StatButton(data, Intelligence.INSTANCE, xpos, guiTop + 10));
        buttons.add(new IncreaseStatButton(data, stats, Intelligence.INSTANCE, xpos, ypos + 15));
        xpos += spacing;
        buttons.add(new StatButton(data, Strength.INSTANCE, xpos, guiTop + 10));
        buttons.add(new IncreaseStatButton(data, stats, Strength.INSTANCE, xpos, ypos + 15));

    }

    private static final Identifier BACKGROUND = new Identifier(Ref.MODID, "textures/gui/stats.png");

    @Override
    public void render(MatrixStack matrix, int x, int y, float ticks) {

        mc.getTextureManager()
            .bindTexture(new Identifier(Ref.MODID, "textures/gui/stats.png"));
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        drawTexture(matrix, mc.getWindow()
                .getScaledWidth() / 2 - sizeX / 2,
            mc.getWindow()
                .getScaledHeight() / 2 - sizeY / 2, 0, 0, sizeX, sizeY
        );

        super.render(matrix, x, y, ticks);

        buttons.forEach(b -> b.renderToolTip(matrix, x, y));

        int xe = guiLeft + 51;
        int ye = guiTop + 75;
        InventoryScreen.drawEntity(xe, ye, 30, xe - x, ye - y, mc.player);

    }

    private static final Identifier BUTTON_TEX = new Identifier(Ref.MODID, "textures/gui/button.png");
    static int button_sizeX = 18;
    static int button_sizeY = 18;

    public class StatButton extends TexturedButtonWidget {

        TextRenderer font = MinecraftClient.getInstance().textRenderer;
        StatData stat;
        EntityCap.UnitData unitdata;

        public StatButton(EntityCap.UnitData unitdata, Stat stat, int xPos, int yPos) {
            super(xPos, yPos, button_sizeX, button_sizeY, 0, 0, button_sizeY, BUTTON_TEX, (button) -> {
            });

            this.stat = unitdata.getUnit()
                .getCreateStat(stat);
            this.unitdata = unitdata;
        }

        @Override
        public void renderToolTip(MatrixStack matrix, int x, int y) {
            if (isInside(x, y)) {
                List<Text> tooltip = new ArrayList<>();

                tooltip.add(Styles.GREENCOMP()
                    .append(stat.GetStat()
                        .locName()));

                tooltip.addAll(stat.GetStat()
                    .getCutDescTooltip());

                GuiUtils.renderTooltip(matrix,
                    tooltip, x, y);

            }
        }

        public boolean isInside(int x, int y) {
            return GuiUtils.isInRect(this.x, this.y, button_sizeX, button_sizeY, x, y);
        }

        public int getIconX() {
            return this.x - 22;
        }

        public int getIconY() {
            return this.y - 18 / 4;
        }

        @Override
        public void renderButton(MatrixStack matrix, int x, int y, float f) {
            // super.renderButton(x, y, f);
            if (!(stat.GetStat() instanceof UnknownStat)) {

                String str = getStatString(stat, unitdata);

                Identifier res = stat.GetStat()
                    .getIconLocation();

                RenderUtils.render16Icon(matrix, res, this.x, this.y);

                StatScreen.this.drawStringWithShadow(matrix, font, str, this.x + 15, this.y + 3, Formatting.GOLD.getColorValue());
            }
        }

    }

    public static String getStatString(StatData data, EntityCap.UnitData unitdata) {
        Stat stat = data.GetStat();

        String v1 = NumberUtils.formatForTooltip(data
            .getFirstValue());
        String v2 = NumberUtils.formatForTooltip(data
            .getSecondValue());

        String str = "";

        if (stat.UsesSecondValue()) {
            str = v1 + "-" + v2;

        } else {
            str = v1;
        }

        if (stat.IsPercent()) {
            str += '%';
        }

        if (stat instanceof IUsableStat) {
            IUsableStat usable = (IUsableStat) stat;

            String value = NumberUtils.format(
                usable.getUsableValue((int) data
                    .getAverageValue(), unitdata.getLevel()) * 100);

            str += " (" + value + "%)";

        }
        return str;

    }

    class IncreaseStatButton extends TexturedButtonWidget {

        PlayerStatsCap.IPlayerStatPointsData data;
        Stat stat;
        EntityCap.UnitData unitdata;

        public IncreaseStatButton(EntityCap.UnitData unitdata, PlayerStatsCap.IPlayerStatPointsData data,
                                  Stat stat, int xPos, int yPos) {
            super(xPos, yPos, button_sizeX, button_sizeY, 0, 0, button_sizeY, BUTTON_TEX, (button) -> {

                Packets.sendToServer(new SpendStatPointsPacket(stat));
                Packets.sendToServer(new RequestSyncCapToClient(PlayerCaps.STAT_POINTS));

            });

            this.data = data;
            this.stat = stat;
            this.unitdata = unitdata;

        }

        @Override
        public void renderToolTip(MatrixStack matrix, int x, int y) {
            if (isInside(x, y)) {

                List<Text> tooltip = new ArrayList<>();

                tooltip.add(Styles.BLUECOMP()
                    .append(stat.locName()));

                if (stat instanceof BaseCoreStat) {
                    BaseCoreStat core = (BaseCoreStat) stat;
                    tooltip.addAll(core.getCoreStatTooltip(unitdata, unitdata.getUnit()
                        .getCreateStat(stat)));
                }
                GuiUtils.renderTooltip(matrix, tooltip, x, y);

            }
        }

        public boolean isInside(int x, int y) {
            return GuiUtils.isInRect(this.x, this.y, button_sizeX, button_sizeY, x, y);
        }

        @Override
        public void renderButton(MatrixStack matrix, int x, int y, float f) {
            try {
                super.renderButton(matrix, x, y, f);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}


