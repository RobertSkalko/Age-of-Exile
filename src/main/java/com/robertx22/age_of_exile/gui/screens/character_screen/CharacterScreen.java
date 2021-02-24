package com.robertx22.age_of_exile.gui.screens.character_screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.a_libraries.curios.MyCurioUtils;
import com.robertx22.age_of_exile.a_libraries.curios.RefCurio;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.stats.IUsableStat;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.UnknownStat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.*;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.BaseCoreStat;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.defense.MaxElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.generated.*;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuality;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuantity;
import com.robertx22.age_of_exile.database.data.stats.types.misc.BonusExp;
import com.robertx22.age_of_exile.database.data.stats.types.offense.Accuracy;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.SpellCriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.SpellCriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.gui.bases.INamedScreen;
import com.robertx22.age_of_exile.gui.buttons.FavorButton;
import com.robertx22.age_of_exile.gui.screens.ItemSlotButton;
import com.robertx22.age_of_exile.gui.screens.MainHubButton;
import com.robertx22.age_of_exile.gui.screens.char_select.CharSelectScreen;
import com.robertx22.age_of_exile.gui.screens.player_skills.PlayerSkillsScreen;
import com.robertx22.age_of_exile.gui.screens.skill_gems.SkillGemsOpener;
import com.robertx22.age_of_exile.gui.screens.skill_tree.TalentsScreen;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.NumberUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RenderUtils;
import com.robertx22.age_of_exile.uncommon.wrappers.SText;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CharacterScreen extends BaseScreen implements INamedScreen {

    static int sizeX = 256;
    static int sizeY = 178;

    MinecraftClient mc = MinecraftClient.getInstance();

    public enum StatType {
        HUB("hub"),
        MAIN("main"),
        ELEMENTAL("elemental"),
        RESISTS("resists"),
        MISC("misc");

        String id;

        StatType(String id) {
            this.id = id;
        }

        Identifier getIcon() {
            return new Identifier(Ref.MODID, "textures/gui/stat_groups/" + id + ".png");
        }
    }

    boolean isMainScreen() {
        return this.statToShow == StatType.HUB;
    }

    public StatType statToShow = StatType.HUB;

    static HashMap<StatType, List<List<Stat>>> STAT_MAP = new HashMap<>();

    static <T extends Stat> void addTo(StatType type, List<T> stats) {

        List<Stat> list = stats.stream()
            .map(x -> (Stat) x)
            .collect(Collectors.toList());

        if (!STAT_MAP.containsKey(type)) {
            STAT_MAP.put(type, new ArrayList<>());
        }
        STAT_MAP.get(type)
            .add(list);
    }

    static {

        addTo(StatType.MAIN, Arrays.asList(Health.getInstance(), Mana.getInstance()));
        addTo(StatType.MAIN, Arrays.asList(HealthRegen.getInstance(), ManaRegen.getInstance()));
        addTo(StatType.MAIN, Arrays.asList(Armor.getInstance(), DodgeRating.getInstance()));
        addTo(StatType.MAIN, Arrays.asList(CriticalHit.getInstance(), CriticalDamage.getInstance(), SpellCriticalHit.getInstance(), SpellCriticalDamage.getInstance()));
        addTo(StatType.MAIN, Arrays.asList(Accuracy.getInstance(), SpellDamage.getInstance()));

        addTo(StatType.ELEMENTAL, new AttackDamage(Elements.Elemental).generateAllPossibleStatVariations());
        addTo(StatType.ELEMENTAL, new ElementalSpellDamage(Elements.Elemental).generateAllPossibleStatVariations());
        addTo(StatType.ELEMENTAL, new ElementalDamageBonus(Elements.Elemental).generateAllPossibleStatVariations());

        addTo(StatType.RESISTS, new ElementalResist(Elements.Elemental).generateAllPossibleStatVariations());
        addTo(StatType.RESISTS, new MaxElementalResist(Elements.Elemental).generateAllPossibleStatVariations());
        addTo(StatType.RESISTS, new ElementalPenetration(Elements.Elemental).generateAllPossibleStatVariations());

        addTo(StatType.MISC, Arrays.asList(BonusExp.getInstance(), TreasureQuality.getInstance(), TreasureQuantity.getInstance()));

    }

    public CharacterScreen() {
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
        return super.mouseReleased(x, y, ticks);
    }

    @Override
    public Words screenName() {
        return Words.Character;
    }

    @Override
    public void init() {
        super.init();

        this.buttons.clear();
        this.children.clear();

        // CORE STATS
        int xpos = guiLeft + 75;
        int ypos = guiTop + 25;

        if (this.isMainScreen()) {

            if (true) {

                xpos = guiLeft + 78;
                ypos = guiTop + 105;

                int YSEP = 20;

                addButton(new AllocateStatButton(Vitality.INSTANCE, xpos, ypos));
                ypos += YSEP;
                addButton(new AllocateStatButton(Wisdom.INSTANCE, xpos, ypos));
                ypos += YSEP;
                addButton(new AllocateStatButton(Agility.INSTANCE, xpos, ypos));

                xpos = guiLeft + 159;
                ypos = guiTop + 105;

                addButton(new AllocateStatButton(Strength.INSTANCE, xpos, ypos));
                ypos += YSEP;
                addButton(new AllocateStatButton(Intelligence.INSTANCE, xpos, ypos));
                ypos += YSEP;
                addButton(new AllocateStatButton(Dexterity.INSTANCE, xpos, ypos));

            }
        }

        if (isMainScreen()) {
            xpos = guiLeft + 12;
            ypos = guiTop + 90;
        } else {
            xpos = guiLeft + 12;
            ypos = guiTop + 12;
        }

        if (!isMainScreen()) {

            int XSPACING = 240 / STAT_MAP.get(statToShow)
                .size();
            int YSPACING = 19;

            int ynum = 0;
            for (List<Stat> list : STAT_MAP.get(statToShow)) {
                for (Stat stat : list) {
                    if (stat.isShown) {
                        addButton(new StatButton(stat, xpos, ypos + (YSPACING * ynum)));

                        ynum++;
                    }
                }
                ynum = 0;
                xpos += XSPACING;
            }
        }

        if (this.isMainScreen()) {
            addButton(new FavorButton(guiLeft + sizeX / 2 - FavorButton.FAVOR_BUTTON_SIZE_X / 2, guiTop - FavorButton.FAVOR_BUTTON_SIZE_Y));

            // hub buttons
            List<INamedScreen> screens = new ArrayList<>();
            screens.add(new TalentsScreen());
            screens.add(new SkillGemsOpener());
            screens.add(new PlayerSkillsScreen());
            screens.add(new CharSelectScreen());

            int x = guiLeft + sizeX - 1;
            int y = guiTop + 30;

            for (INamedScreen screen : screens) {
                addButton(new MainHubButton(screen, x, y));
                y += MainHubButton.ySize + 0;
            }
        }

        int i = 0;
        for (StatType group : StatType.values()) {
            this.addButton(new StatPageButton(this, group, guiLeft - StatPageButton.SIZEX, guiTop + 15 + (i * (StatPageButton.SIZEY + 1))));
            i++;
        }

        if (isMainScreen()) {
            // gear items
            addItemButton(MyCurioUtils.get(RefCurio.NECKLACE, mc.player, 0), 78, 15);
            addItemButton(MyCurioUtils.get(RefCurio.RING, mc.player, 0), 78, 33);
            addItemButton(MyCurioUtils.get(RefCurio.RING, mc.player, 1), 78, 51);
            addItemButton(MyCurioUtils.get(RefCurio.SALVAGE_BAG, mc.player, 0), 78, 69);

            addItemButton(mc.player.getEquippedStack(EquipmentSlot.HEAD), 159, 15);
            addItemButton(mc.player.getEquippedStack(EquipmentSlot.CHEST), 159, 33);
            addItemButton(mc.player.getEquippedStack(EquipmentSlot.LEGS), 159, 51);
            addItemButton(mc.player.getEquippedStack(EquipmentSlot.FEET), 159, 69);

            addItemButton(mc.player.getEquippedStack(EquipmentSlot.MAINHAND), 58, 69);
            addItemButton(mc.player.getEquippedStack(EquipmentSlot.OFFHAND), 179, 69);
        }
    }

    void addItemButton(ItemStack stack, int x, int y) {
        addButton(new ItemSlotButton(stack, this.guiLeft + x, this.guiTop + y));
    }

    private static final Identifier BACKGROUND = new Identifier(Ref.MODID, "textures/gui/stats.png");
    private static final Identifier WIDE_BACKGROUND = new Identifier(Ref.MODID, "textures/gui/full_stats_panel.png");

    @Override
    public void render(MatrixStack matrix, int x, int y, float ticks) {

        if (isMainScreen()) {
            mc.getTextureManager()
                .bindTexture(BACKGROUND);
        } else {
            mc.getTextureManager()
                .bindTexture(WIDE_BACKGROUND);
        }

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        drawTexture(matrix, mc.getWindow()
                .getScaledWidth() / 2 - sizeX / 2,
            mc.getWindow()
                .getScaledHeight() / 2 - sizeY / 2, 0, 0, sizeX, sizeY
        );

        super.render(matrix, x, y, ticks);

        buttons.forEach(b -> b.renderToolTip(matrix, x, y));

        int xe = guiLeft + 35 + 91;
        int ye = guiTop + 75 + 7;

        if (isMainScreen()) {
            InventoryScreen.drawEntity(xe, ye, 30, xe - x, ye - y, mc.player);

            String str = "Level: " + Load.Unit(mc.player)
                .getLevel();
            GuiUtils.renderScaledText(matrix, xe, ye - 63, 0.6F, str, Formatting.YELLOW);

        }
    }

    private static final Identifier STAT_PAGE_BUTTON_TEXT = new Identifier(Ref.MODID, "textures/gui/main_hub/buttons_backwards.png");

    static int PLUS_BUTTON_SIZE_X = 13;
    static int PLUS_BUTTON_SIZE_Y = 13;

    public static class AllocateStatButton extends TexturedButtonWidget {
        static int SIZEX = 18;
        static int SIZEY = 18;
        static Identifier BUTTON_TEX = new Identifier(Ref.MODID, "textures/gui/plus_button.png");

        Stat stat;

        public AllocateStatButton(Stat stat, int xPos, int yPos) {
            super(xPos, yPos, SIZEX, SIZEY, 0, 0, SIZEY, BUTTON_TEX, (button) -> {
            });
            this.stat = stat;
        }

        @Override
        public void renderToolTip(MatrixStack matrix, int x, int y) {
            if (isInside(x, y)) {

                MinecraftClient mc = MinecraftClient.getInstance();

                List<Text> tooltip = new ArrayList<>();

                tooltip.add(stat
                    .locName()
                    .formatted(Formatting.GREEN));

                tooltip.add(new SText(""));

                tooltip.addAll(((BaseCoreStat) stat).getCoreStatTooltip(Load.Unit(mc.player), Load.Unit(mc.player)
                    .getUnit()
                    .getCalculatedStat(stat)));

                GuiUtils.renderTooltip(matrix,
                    tooltip, x, y);

            }
        }

        public boolean isInside(int x, int y) {
            return GuiUtils.isInRect(this.x, this.y, SIZEX, SIZEY, x, y);
        }

        @Override
        public void renderButton(MatrixStack matrix, int x, int y, float f) {

            super.renderButton(matrix, x, y, f);

            MinecraftClient mc = MinecraftClient.getInstance();

            String txt = ((int) Load.Unit(mc.player)
                .getUnit()
                .getCalculatedStat(stat)
                .getAverageValue()) + "";

            RenderUtils.render16Icon(matrix, stat.getIconForRendering(), this.x - 20, this.y + 1);

            mc.textRenderer.drawWithShadow(matrix, txt, this.x + SIZEX + 4, this.y + 4, Formatting.YELLOW.getColorValue());

        }

    }

    private static final Identifier BUTTON_TEX = new Identifier(Ref.MODID, "textures/gui/button.png");
    static int STAT_BUTTON_SIZE_X = 18;
    static int STAT_BUTTON_SIZE_Y = 18;

    public class StatButton extends TexturedButtonWidget {

        TextRenderer font = MinecraftClient.getInstance().textRenderer;
        Stat stat;

        public StatButton(Stat stat, int xPos, int yPos) {
            super(xPos, yPos, STAT_BUTTON_SIZE_X, STAT_BUTTON_SIZE_Y, 0, 0, STAT_BUTTON_SIZE_Y, BUTTON_TEX, (button) -> {
            });

            this.stat = stat;
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
                    .getCalculatedStat(stat), Load.Unit(mc.player));

                Identifier res = stat
                    .getIconForRendering();

                RenderUtils.render16Icon(matrix, res, this.x, this.y);

                CharacterScreen.this.drawStringWithShadow(matrix, font, str, this.x + STAT_BUTTON_SIZE_X, this.y + 2, Formatting.GOLD.getColorValue());

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

    static class StatPageButton extends TexturedButtonWidget {

        public static int SIZEX = 105;
        public static int SIZEY = 28;

        StatType page;

        public StatPageButton(CharacterScreen screen, StatType page, int xPos, int yPos) {
            super(xPos + 1, yPos, SIZEX, SIZEY, 0, 0, SIZEY, STAT_PAGE_BUTTON_TEXT, (button) -> {
                screen.statToShow = page;
                screen.init();
            });
            this.page = page;
        }

        @Override
        public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
            MinecraftClient mc = MinecraftClient.getInstance();

            super.renderButton(matrices, x, y, delta);

            RenderUtils.render16Icon(matrices, page.getIcon(), this.x + 80, this.y + 6);

            if (isHovered()) {
                String txt = page.id;
                MinecraftClient.getInstance().textRenderer.drawWithShadow(matrices,
                    txt, this.x + SIZEX - 30 - mc.textRenderer.getWidth(txt), this.y + 9, Formatting.YELLOW.getColorValue());
            }

        }

        @Override
        public void renderToolTip(MatrixStack matrix, int x, int y) {
            if (isInside(x, y)) {

                List<Text> tooltip = new ArrayList<>();
                GuiUtils.renderTooltip(matrix, tooltip, x, y);

            }
        }

        public boolean isInside(int x, int y) {
            return GuiUtils.isInRect(this.x, this.y, PLUS_BUTTON_SIZE_X, PLUS_BUTTON_SIZE_Y, x, y);
        }

    }

}


