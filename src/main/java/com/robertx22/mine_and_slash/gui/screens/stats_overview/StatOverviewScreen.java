package com.robertx22.mine_and_slash.gui.screens.stats_overview;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.database.data.stats.IUsableStat;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.types.UnknownStat;
import com.robertx22.mine_and_slash.gui.bases.BaseScreen;
import com.robertx22.mine_and_slash.gui.bases.INamedScreen;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.localization.Styles;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.GuiUtils;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.NumberUtils;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.RenderUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.*;
import java.util.stream.Collectors;

@Environment(EnvType.CLIENT)
public class StatOverviewScreen extends BaseScreen implements INamedScreen {

    static int sizeY = 220;
    static int sizeX = 215;

    Stat.StatGroup statgroup = Stat.StatGroup.Main;
    int currentElement = 0;
    HashMap<String, List<Stat>> statmap = new HashMap<>();
    MinecraftClient mc = MinecraftClient.getInstance();

    public StatOverviewScreen() {
        super(sizeX, sizeY);
    }

    EntityCap.UnitData data = Load.Unit(MinecraftClient.getInstance().player);

    @Override
    public Identifier iconLocation() {
        return new Identifier(Ref.MODID, "textures/gui/main_hub/icons/stat_overview.png");
    }

    @Override
    public Words screenName() {
        return Words.StatOverview;
    }

    @Override
    public void init() {
        genStatList();
    }

    private static final Identifier texture = new Identifier(Ref.MODID, "textures/gui/stats_screen.png");

    @Override
    public void render(MatrixStack matrix, int x, int y, float ticks) {

        mc.getTextureManager()
            .bindTexture(texture);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        drawTexture(matrix, mc.getWindow()
                .getScaledWidth() / 2 - this.sizeX / 2,
            mc.getWindow()
                .getScaledHeight() / 2 - this.sizeY / 2, 0, 0, sizeX, sizeY
        );

        renderStats(matrix);

        super.render(matrix, x, y, ticks);

        buttons.forEach(b -> b.renderToolTip(matrix, x, y));

    }

    private int getGUIStartX() {

        return (int) (mc.getWindow()
            .getScaledWidth() / 2 - this.sizeX / 2);
    }

    private int getGUIStartY() {

        return (int) (mc.getWindow()
            .getScaledHeight() / 2 - this.sizeY / 2);
    }

    private int getTextStartX() {

        return (int) (mc.getWindow()
            .getScaledWidth() / 2 - this.sizeX / 2 + 35);
    }

    private int getTextStartY() {

        return (int) (mc.getWindow()
            .getScaledHeight() / 2 - this.sizeY / 2 + 40);
    }

    public String getStatString(Stat stat, EntityCap.UnitData data) {

        String v1 = NumberUtils.formatForTooltip(data.getUnit()
            .peekAtStat(stat)
            .getFirstValue());
        String v2 = NumberUtils.formatForTooltip(data.getUnit()
            .peekAtStat(stat)
            .getSecondValue());

        String str = "";

        if (stat.UsesSecondValue()) {
            str = stat.translate() + ": " + v1 + "-" + v2;

        } else {
            str = stat.translate() + ": " + v1;
        }

        if (stat.IsPercent()) {
            str += '%';
        }

        if (stat instanceof IUsableStat) {
            IUsableStat usable = (IUsableStat) stat;

            String value = NumberUtils.format(
                usable.getUsableValue((int) data.getUnit()
                    .getCreateStat(stat)
                    .getAverageValue(), data.getLevel()) * 100);

            str += " (" + value + "%)";

        }
        return str;

    }

    private List<Stat> getList() {
        List<Stat> list = new ArrayList<>();
        statmap.forEach((key, value) -> {
            list.addAll(value);
            list.add(new UnknownStat()); // as spacing
        });

        return list;

    }

    private int drawTitleAndIncreaseSpacing(MatrixStack matrix, int x, int y, String str) {
        this.drawStringWithShadow(matrix, mc.textRenderer, str, x, y, Formatting.GREEN.getColorValue());
        return this.getHeightSpacing();

    }

    private int renderStats(MatrixStack matrix) {

        List<Stat> list = getList();

        int x = this.getTextStartX();
        int y = this.getTextStartY();

        int added = 0;

        added += this.drawTitleAndIncreaseSpacing(matrix, x - 22, y + added, this.statgroup.word.translate() + ": ");
        added += this.getHeightSpacing() / 3;

        y += added;

        //
        List<AbstractButtonWidget> newlist = new ArrayList<>();
        for (AbstractButtonWidget button : new ArrayList<AbstractButtonWidget>(buttons)) {
            if (button instanceof StatButton == false) {
                newlist.add(button);
            }
        }
        this.buttons.clear();
        this.buttons.addAll(newlist);
        //

        EntityCap.UnitData data = Load.Unit(mc.player);

        for (int i = currentElement; i < list.size(); i++) {
            if (i > -1) { // or scrolling crashes

                if (added < this.sizeY - 60) {

                    Stat stat = list.get(i);

                    if (stat instanceof UnknownStat) {

                    } else {
                        this.addButton(new StatButton(data, stat, x, y));
                    }

                    y += getHeightSpacing();
                    added += getHeightSpacing();
                }

            }

        }

        return list.size();

    }

    private int getHeightSpacing() {
        return 18;
    }

    @Override
    public boolean mouseScrolled(double num1, double num2, double num3) {

        this.currentElement -= num3;

        this.currentElement = MathHelper.clamp(currentElement, 0, renderStats(new MatrixStack()));

        return false;
    }

    void genStatList() {

        this.statmap = new HashMap<>();

        List<Stat> statlist = SlashRegistry.Stats()
            .getAll()
            .values()
            .stream()
            .filter(stat -> stat.IsShownOnStatGui() && stat.statGroup()
                .equals(statgroup))
            .collect(Collectors.toList());

        Collections.sort(statlist, Comparator.comparing(stat -> stat.GUID()));

        List<Stat> misc = new ArrayList<>();

        for (Stat stat : statlist) {
            List<Stat> same = statlist.stream()
                .filter(x -> x.getClass() == stat.getClass())
                .collect(Collectors.toList());

            if (same.size() > 1) {
                statmap.put(stat.getClass()
                    .getName(), same);
            } else {
                misc.add(stat);
            }
        }
        statmap.put("misc", misc);

    }

    @Override
    public boolean mouseClicked(double X, double Y, int idk) {
        if (super.mouseClicked(X, Y, idk)) {
            return true;
        } else {
            // my stuff

            for (Stat.StatGroup group : Stat.StatGroup.values()) {

                int x = group.X() + this.getGUIStartX();
                int y = group.Y + this.getGUIStartY();

                if (GuiUtils.isInRect(x, y, group.width, group.height, (int) X, (int) Y)) {

                    this.currentElement = 0;
                    this.statgroup = group;
                    genStatList();
                }

            }

            return true;
        }
    }

    static int button_sizeX = 18;
    static int button_sizeY = 18;

    static Identifier BUTTON_TEX = new Identifier("");

    class StatButton extends TexturedButtonWidget {

        TextRenderer font = MinecraftClient.getInstance().textRenderer;
        Stat stat;
        EntityCap.UnitData unitdata;

        public StatButton(EntityCap.UnitData unitdata, Stat statData, int xPos, int yPos) {
            super(xPos, yPos, button_sizeX, button_sizeY, 0, 0, button_sizeY, BUTTON_TEX, (button) -> {
            });

            this.stat = statData;
            this.unitdata = unitdata;

        }

        @Override
        public void renderToolTip(MatrixStack matrix, int x, int y) {
            if (isInside(x, y)) {

                List<Text> tooltip = new ArrayList<>();

                tooltip.add(Styles.GREENCOMP()
                    .append(stat.locName()));

                tooltip.addAll(stat.getCutDescTooltip());

                GuiUtils.renderTooltip(matrix,
                    tooltip, x, y);

            }
        }

        public boolean isInside(int x, int y) {
            return GuiUtils.isInRect(getIconX(), getIconY(), button_sizeX, button_sizeY, x, y);
        }

        public int getIconX() {
            return this.x - 22;
        }

        public int getIconY() {
            return this.y - getHeightSpacing() / 4;
        }

        @Override
        public void renderButton(MatrixStack matrix, int x, int y, float f) {
            // super.renderButton(x, y, f);

            if (!(stat instanceof UnknownStat)) {

                String str = StatOverviewScreen.this.getStatString(stat, unitdata);

                Identifier res = stat.getIconLocation();

                RenderUtils.render16Icon(matrix, res, getIconX(), getIconY());

                StatOverviewScreen.this.drawStringWithShadow(matrix, font, str, this.x, this.y, Formatting.GOLD.getColorValue());
            }
        }

    }
}
