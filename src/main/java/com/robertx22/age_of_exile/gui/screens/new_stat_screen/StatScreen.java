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
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.*;
import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.gui.bases.INamedScreen;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.NumberUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RenderUtils;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.ResetStatPointsItem;
import com.robertx22.age_of_exile.vanilla_mc.packets.SpendStatPointsPacket;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.RequestSyncCapToClient;
import com.robertx22.library_of_exile.gui.HelpButton;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StatScreen extends BaseScreen implements INamedScreen {

    static int sizeX = 247;
    static int sizeY = 166;

    MinecraftClient mc = MinecraftClient.getInstance();

    public StatScreen() {
        super(sizeX, sizeY);
    }

    @Override
    public Identifier iconLocation() {
        return new Identifier(Ref.MODID, "textures/gui/main_hub/icons/stat_overview.png");
    }

    @Override
    public boolean isPauseScreen() {
        return false;
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
        return Words.Stats;
    }

    @Override
    public void init() {
        super.init();

        EntityCap.UnitData data = Load.Unit(mc.player);
        PlayerStatsCap.IPlayerStatPointsData stats = Load.statPoints(mc.player);

        int spacing = 30;

        int XSPACING = 33;
        int YSPACING = 19;

        // CORE STATS

        int xpos = guiLeft + 95 + 55;
        int ypos = guiTop + 25;
        buttons.add(new StatButton(Dexterity.INSTANCE, xpos, ypos));
        buttons.add(new IncreaseStatButton(data, stats, Dexterity.INSTANCE, xpos - 19, ypos + 1));
        ypos += 20;
        buttons.add(new StatButton(Intelligence.INSTANCE, xpos, ypos));
        buttons.add(new IncreaseStatButton(data, stats, Intelligence.INSTANCE, xpos - 19, ypos + 1));
        ypos += 20;
        buttons.add(new StatButton(Strength.INSTANCE, xpos, ypos));
        buttons.add(new IncreaseStatButton(data, stats, Strength.INSTANCE, xpos - 19, ypos + 1));
        // CORE STATS

        // resources
        xpos = guiLeft + 8;
        ypos = guiTop + 7;

        buttons.add(new StatButton(Health.getInstance(), xpos, ypos).verticalText());
        ypos += YSPACING * 3;
        buttons.add(new StatButton(MagicShield.getInstance(), xpos, ypos).verticalText());
        ypos += YSPACING * 3;
        buttons.add(new StatButton(Mana.getInstance(), xpos, ypos).verticalText());
        // resources

        // resource regen
        xpos = guiLeft + sizeX - 7 - STAT_BUTTON_SIZE_X;
        ypos = guiTop + 7;

        buttons.add(new StatButton(HealthRegen.getInstance(), xpos, ypos).verticalText());
        ypos += YSPACING * 3;
        buttons.add(new StatButton(MagicShieldRegen.getInstance(), xpos, ypos).verticalText());
        ypos += YSPACING * 3;
        buttons.add(new StatButton(ManaRegen.getInstance(), xpos, ypos).verticalText());
        // resource regen

        // ELE RESISTS
        xpos = guiLeft + 10 + 30;
        ypos = guiTop + 85;

        for (Stat x : new ElementalResist(Elements.Water).generateAllSingleVariations()) {
            buttons.add(new StatButton(x, xpos, ypos));
            ypos += YSPACING;
        }
        // ELE RESISTS

        // DAMAGE STATS
        xpos = guiLeft + 147;
        ypos = guiTop + 90;

        buttons.add(new CombinedStatsButton(new WeaponDamage(Elements.Physical), new WeaponDamage(Elements.Water).generateAllPossibleStatVariations(), xpos, ypos));
        ypos += 25;
        buttons.add(new StatButton(CriticalHit.getInstance(), xpos, ypos));
        ypos += 25;
        buttons.add(new StatButton(CriticalDamage.getInstance(), xpos, ypos));
        // DAMAGE STATS

        // DEFENSE STATS
        xpos = guiLeft + 95;
        ypos = guiTop + 95;

        buttons.add(new StatButton(Armor.getInstance(), xpos, ypos));
        ypos += 30;
        buttons.add(new StatButton(DodgeRating.getInstance(), xpos, ypos));
        // DEFENSE STATS

        List<Text> list = new ArrayList<>();
        list.add(new LiteralText("Allocate stats here"));
        list.add(new LiteralText(""));
        list.add(new LiteralText("These stats determine your playstyle."));
        list.add(new LiteralText("To wear gear that gives Armor, you need strength,"));
        list.add(new LiteralText("Magic shield > Intelligence, Dodge > Dexterity etc."));
        list.add(new LiteralText(""));
        list.add(new LiteralText("To reset stats, you need to craft:"));
        list.add(new LiteralText(new ResetStatPointsItem().locNameForLangFile()));
        this.addButton(new HelpButton(list, guiLeft + sizeX - 55, guiTop + 5));

    }

    private static final Identifier BACKGROUND = new Identifier(Ref.MODID, "textures/gui/stats.png");

    @Override
    public void render(MatrixStack matrix, int x, int y, float ticks) {

        mc.getTextureManager()
            .bindTexture(BACKGROUND);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        drawTexture(matrix, mc.getWindow()
                .getScaledWidth() / 2 - sizeX / 2,
            mc.getWindow()
                .getScaledHeight() / 2 - sizeY / 2, 0, 0, sizeX, sizeY
        );

        super.render(matrix, x, y, ticks);

        buttons.forEach(b -> b.renderToolTip(matrix, x, y));

        int xe = guiLeft + 62;
        int ye = guiTop + 75;
        InventoryScreen.drawEntity(xe, ye, 30, xe - x, ye - y, mc.player);

        String str = "Level: " + Load.Unit(mc.player)
            .getLevel();
        GuiUtils.renderScaledText(matrix, xe, ye - 60, 0.6F, str, Formatting.YELLOW);

        int xpos = guiLeft + 95 + 55;
        int ypos = guiTop + 15;

        String points = "Points: " + Load.statPoints(mc.player)
            .getAvailablePoints(Load.Unit(mc.player));
        GuiUtils.renderScaledText(matrix, xpos, ypos, 1, points, Formatting.GREEN);

    }

    private static final Identifier BUTTON_TEX = new Identifier(Ref.MODID, "textures/gui/button.png");
    static int STAT_BUTTON_SIZE_X = 18;
    static int STAT_BUTTON_SIZE_Y = 18;

    static int PLUS_BUTTON_SIZE_X = 13;
    static int PLUS_BUTTON_SIZE_Y = 13;

    public class StatButton extends TexturedButtonWidget {

        TextRenderer font = MinecraftClient.getInstance().textRenderer;
        Stat stat;
        public boolean verticalText = false;

        public StatButton(Stat stat, int xPos, int yPos) {
            super(xPos, yPos, STAT_BUTTON_SIZE_X, STAT_BUTTON_SIZE_Y, 0, 0, STAT_BUTTON_SIZE_Y, BUTTON_TEX, (button) -> {
            });

            this.stat = stat;
        }

        public StatButton verticalText() {
            this.verticalText = true;
            return this;
        }

        @Override
        public void renderToolTip(MatrixStack matrix, int x, int y) {
            if (isInside(x, y)) {
                List<Text> tooltip = new ArrayList<>();

                tooltip.add(stat
                    .locName()
                    .formatted(Formatting.GREEN));

                tooltip.addAll(stat
                    .getCutDescTooltip());

                GuiUtils.renderTooltip(matrix,
                    tooltip, x, y);

            }
        }

        public boolean isInside(int x, int y) {
            return GuiUtils.isInRect(this.x, this.y, STAT_BUTTON_SIZE_X, STAT_BUTTON_SIZE_Y, x, y);
        }

        @Override
        public void renderButton(MatrixStack matrix, int x, int y, float f) {
            if (!(stat instanceof UnknownStat)) {

                String str = getStatString(Load.Unit(mc.player)
                    .getUnit()
                    .getCreateStat(stat), Load.Unit(mc.player));

                Identifier res = stat
                    .getIconLocation();

                RenderUtils.render16Icon(matrix, res, this.x, this.y);

                if (verticalText) {
                    StatScreen.this.drawStringWithShadow(matrix, font, str, this.x - font.getWidth(str) / 2 + STAT_BUTTON_SIZE_X / 2, this.y + STAT_BUTTON_SIZE_Y + 2, Formatting.GOLD.getColorValue());
                } else {
                    StatScreen.this.drawStringWithShadow(matrix, font, str, this.x + STAT_BUTTON_SIZE_X, this.y + 2, Formatting.GOLD.getColorValue());

                }
            }
        }

    }

    public class CombinedStatsButton extends TexturedButtonWidget {

        TextRenderer font = MinecraftClient.getInstance().textRenderer;
        List<Stat> stats;
        Stat describer;

        public CombinedStatsButton(Stat describer, List<Stat> stats, int xPos, int yPos) {
            super(xPos, yPos, STAT_BUTTON_SIZE_X, STAT_BUTTON_SIZE_Y, 0, 0, STAT_BUTTON_SIZE_Y, BUTTON_TEX, (button) -> {
            });

            this.stats = stats;
            this.describer = describer;
        }

        @Override
        public void renderToolTip(MatrixStack matrix, int x, int y) {
            if (isInside(x, y)) {
                List<Text> tooltip = new ArrayList<>();

                stats.forEach(s -> {

                    String str = s.translate() + ": " + getStatString(Load.Unit(mc.player)
                        .getUnit()
                        .getCreateStat(s), Load.Unit(mc.player));

                    tooltip.add(new LiteralText(str));

                });

                GuiUtils.renderTooltip(matrix,
                    tooltip, x, y);

            }
        }

        public boolean isInside(int x, int y) {
            return GuiUtils.isInRect(this.x, this.y, STAT_BUTTON_SIZE_X, STAT_BUTTON_SIZE_Y, x, y);
        }

        @Override
        public void renderButton(MatrixStack matrix, int x, int y, float f) {
            if (!(stats instanceof UnknownStat)) {

                StatData data = new StatData(describer);

                stats.forEach(s -> {
                    StatData part = Load.Unit(mc.player)
                        .getUnit()
                        .getCreateStat(s);

                    part.addCalcValuesTo(data);
                });

                String str = getStatString(data, Load.Unit(mc.player));

                Identifier res = describer
                    .getIconLocation();

                RenderUtils.render16Icon(matrix, res, this.x, this.y);

                StatScreen.this.drawStringWithShadow(matrix, font, str, this.x + STAT_BUTTON_SIZE_X, this.y + 2, Formatting.GOLD.getColorValue());

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

            str = "" + value + "%";

        }
        return str;

    }

    class IncreaseStatButton extends TexturedButtonWidget {

        PlayerStatsCap.IPlayerStatPointsData data;
        Stat stat;
        EntityCap.UnitData unitdata;

        public IncreaseStatButton(EntityCap.UnitData unitdata, PlayerStatsCap.IPlayerStatPointsData data,
                                  Stat stat, int xPos, int yPos) {
            super(xPos, yPos, PLUS_BUTTON_SIZE_X, PLUS_BUTTON_SIZE_Y, 0, 0, PLUS_BUTTON_SIZE_Y, BUTTON_TEX, (button) -> {

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

                tooltip.add(
                    stat.locName()
                        .formatted(Formatting.BLUE));

                if (stat instanceof BaseCoreStat) {
                    BaseCoreStat core = (BaseCoreStat) stat;
                    tooltip.addAll(core.getCoreStatTooltip(unitdata, unitdata.getUnit()
                        .getCreateStat(stat)));
                }
                GuiUtils.renderTooltip(matrix, tooltip, x, y);

            }
        }

        public boolean isInside(int x, int y) {
            return GuiUtils.isInRect(this.x, this.y, PLUS_BUTTON_SIZE_X, PLUS_BUTTON_SIZE_Y, x, y);
        }

    }

}


