package com.robertx22.age_of_exile.gui.screens.character_screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.capability.player.PlayerStatsCap;
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
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.HealEffectivenessOnSelf;
import com.robertx22.age_of_exile.database.data.stats.types.resources.HealPower;
import com.robertx22.age_of_exile.database.data.stats.types.resources.PlusResourceOnKill;
import com.robertx22.age_of_exile.database.data.stats.types.resources.RegeneratePercentStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.LifeOnHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Lifesteal;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicOnHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShieldRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicSteal;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.*;
import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.gui.bases.INamedScreen;
import com.robertx22.age_of_exile.gui.buttons.FavorButton;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.NumberUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RenderUtils;
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
        MAIN("main"),
        ELEMENTAL("elemental"),
        RESISTS("resists"),
        RESTORATION("restoration"),
        WEAPON("weapon"),
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
        return this.statToShow == StatType.MAIN;
    }

    public StatType statToShow = StatType.MAIN;

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

        addTo(StatType.MAIN, Arrays.asList(Health.getInstance(), MagicShield.getInstance(), Mana.getInstance()));
        addTo(StatType.MAIN, Arrays.asList(HealthRegen.getInstance(), MagicShieldRegen.getInstance(), ManaRegen.getInstance()));
        addTo(StatType.MAIN, Arrays.asList(Armor.getInstance(), DodgeRating.getInstance()));
        addTo(StatType.MAIN, Arrays.asList(SpellDamage.getInstance(), CriticalHit.getInstance(), CriticalDamage.getInstance()));

        addTo(StatType.ELEMENTAL, new AttackDamage(Elements.Elemental).generateAllPossibleStatVariations());
        addTo(StatType.ELEMENTAL, new ElementalSpellDamage(Elements.Elemental).generateAllPossibleStatVariations());
        addTo(StatType.ELEMENTAL, new ElementalDamageBonus(Elements.Elemental).generateAllPossibleStatVariations());

        addTo(StatType.RESISTS, new ElementalResist(Elements.Elemental).generateAllPossibleStatVariations());
        addTo(StatType.RESISTS, new MaxElementalResist(Elements.Elemental).generateAllPossibleStatVariations());
        addTo(StatType.RESISTS, new ElementalPenetration(Elements.Elemental).generateAllPossibleStatVariations());

        addTo(StatType.RESTORATION, Arrays.asList(LifeOnHit.getInstance(), Lifesteal.getInstance(), PlusResourceOnKill.HEALTH, RegeneratePercentStat.HEALTH));
        addTo(StatType.RESTORATION, Arrays.asList(MagicOnHit.getInstance(), MagicSteal.getInstance(), PlusResourceOnKill.MAGIC_SHIELD, RegeneratePercentStat.MAGIC_SHIELD));
        addTo(StatType.RESTORATION, Arrays.asList(ManaOnHit.getInstance(), ManaSteal.getInstance(), PlusResourceOnKill.MANA, RegeneratePercentStat.MANA));
        addTo(StatType.RESTORATION, Arrays.asList(HealPower.getInstance(), HealEffectivenessOnSelf.getInstance(), ManaBurnResistance.getInstance()));

        addTo(StatType.WEAPON, new SpecificWeaponDamage(WeaponTypes.Sword).generateAllPossibleStatVariations());
        addTo(StatType.WEAPON, new SpecificElementalWeaponDamage(WeaponTypes.Sword).generateAllPossibleStatVariations());

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

        EntityCap.UnitData data = Load.Unit(mc.player);
        PlayerStatsCap.IPlayerStatPointsData stats = Load.statPoints(mc.player);

        int XSPACING = 240 / STAT_MAP.get(statToShow)
            .size();
        int YSPACING = 19;

        // CORE STATS
        int xpos = guiLeft + 95;
        int ypos = guiTop + 25;

        if (this.isMainScreen()) {

            addButton(new StatButton(Vitality.INSTANCE, xpos, ypos));
            addButton(new IncreaseStatButton(data, stats, Vitality.INSTANCE, xpos - 19, ypos + 1));
            ypos += 20;
            addButton(new StatButton(Willpower.INSTANCE, xpos, ypos));
            addButton(new IncreaseStatButton(data, stats, Willpower.INSTANCE, xpos - 19, ypos + 1));
            ypos += 20;
            addButton(new StatButton(Wisdom.INSTANCE, xpos, ypos));
            addButton(new IncreaseStatButton(data, stats, Wisdom.INSTANCE, xpos - 19, ypos + 1));

            //
            xpos += 65;
            ypos = guiTop + 25;

            addButton(new StatButton(Strength.INSTANCE, xpos, ypos));
            addButton(new IncreaseStatButton(data, stats, Strength.INSTANCE, xpos - 19, ypos + 1));
            ypos += 20;
            addButton(new StatButton(Intelligence.INSTANCE, xpos, ypos));
            addButton(new IncreaseStatButton(data, stats, Intelligence.INSTANCE, xpos - 19, ypos + 1));
            ypos += 20;
            addButton(new StatButton(Dexterity.INSTANCE, xpos, ypos));
            addButton(new IncreaseStatButton(data, stats, Dexterity.INSTANCE, xpos - 19, ypos + 1));

        }

        if (isMainScreen()) {
            xpos = guiLeft + 12;
            ypos = guiTop + 90;
        } else {
            xpos = guiLeft + 12;
            ypos = guiTop + 12;
        }

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

        if (this.isMainScreen()) {
            List<Text> list = new ArrayList<>();
            list.add(new LiteralText("Allocate stats here"));
            list.add(new LiteralText(""));
            list.add(new LiteralText("These stats determine your playstyle."));
            list.add(new LiteralText("Points into vitality or willpower are recommended for newbies."));
            list.add(new LiteralText(""));
            list.add(new LiteralText("To reset stats, you need to craft:"));
            list.add(ModRegistry.MISC_ITEMS.RESET_STATS_POTION.locName());
            this.addButton(new HelpButton(list, guiLeft + sizeX - 30, guiTop + 5));

            addButton(new FavorButton(guiLeft + sizeX - 45, guiTop + 40));

        }

        int i = 0;
        for (StatType group : StatType.values()) {
            this.addButton(new StatPageButton(this, group, guiLeft + sizeX, guiTop + 5 + (i * (PAGE_BUTTON_SIZE_Y + 1))));
            i++;
        }

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

        int xe = guiLeft + 32;
        int ye = guiTop + 75;

        if (isMainScreen()) {
            InventoryScreen.drawEntity(xe, ye, 30, xe - x, ye - y, mc.player);

            String str = "Level: " + Load.Unit(mc.player)
                .getLevel();
            GuiUtils.renderScaledText(matrix, xe, ye - 60, 0.6F, str, Formatting.YELLOW);

            int xpos = guiLeft + 95;
            int ypos = guiTop + 15;

            String points = "Points: " + Load.statPoints(mc.player)
                .getAvailablePoints(Load.Unit(mc.player));
            GuiUtils.renderScaledText(matrix, xpos, ypos, 1, points, Formatting.GREEN);

        }
    }

    private static final Identifier BUTTON_TEX = new Identifier(Ref.MODID, "textures/gui/button.png");
    static int STAT_BUTTON_SIZE_X = 18;
    static int STAT_BUTTON_SIZE_Y = 18;

    static int PLUS_BUTTON_SIZE_X = 13;
    static int PLUS_BUTTON_SIZE_Y = 13;

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
                        .getCalculatedStat(stat)));
                }
                GuiUtils.renderTooltip(matrix, tooltip, x, y);

            }
        }

        public boolean isInside(int x, int y) {
            return GuiUtils.isInRect(this.x, this.y, PLUS_BUTTON_SIZE_X, PLUS_BUTTON_SIZE_Y, x, y);
        }

    }

    private static final Identifier PAGE_BUTTON_TEX = new Identifier(Ref.MODID, "textures/gui/page_buttons.png");
    static int PAGE_BUTTON_SIZE_X = 32;
    static int PAGE_BUTTON_SIZE_Y = 28;

    class StatPageButton extends TexturedButtonWidget {

        StatType page;

        public StatPageButton(CharacterScreen screen, StatType page, int xPos, int yPos) {
            super(xPos, yPos, PAGE_BUTTON_SIZE_X, PAGE_BUTTON_SIZE_Y, 0, 0, PAGE_BUTTON_SIZE_Y, BUTTON_TEX, (button) -> {
                screen.statToShow = page;
                screen.init();
            });
            this.page = page;
        }

        @Override
        public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
            MinecraftClient mc = MinecraftClient.getInstance();
            mc.getTextureManager()
                .bindTexture(PAGE_BUTTON_TEX);

            int i = 0;
            if (page != CharacterScreen.this.statToShow) {
                i += 32;
            }
            RenderSystem.enableDepthTest();
            drawTexture(matrices, this.x, this.y, 0, i, this.width, this.height);

            mc.getTextureManager()
                .bindTexture(page.getIcon());
            drawTexture(matrices, this.x + 6, this.y + 6, 0, i, 16, 16, 16, 16);

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


