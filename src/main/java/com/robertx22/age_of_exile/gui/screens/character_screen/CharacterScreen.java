package com.robertx22.age_of_exile.gui.screens.character_screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.stats.IUsableStat;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.CoreStat;
import com.robertx22.age_of_exile.database.data.stats.types.UnknownStat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.AllAttributes;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalPenetration;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.loot.ItemFind;
import com.robertx22.age_of_exile.database.data.stats.types.loot.MagicFind;
import com.robertx22.age_of_exile.database.data.stats.types.misc.BonusExp;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.gui.bases.INamedScreen;
import com.robertx22.age_of_exile.gui.buttons.FavorButton;
import com.robertx22.age_of_exile.gui.buttons.ScalingDifficultyButton;
import com.robertx22.age_of_exile.gui.screens.player_skills.ProfessionsScreen;
import com.robertx22.age_of_exile.gui.screens.skill_tree.TalentsScreen;
import com.robertx22.age_of_exile.gui.screens.spell.SpellScreen;
import com.robertx22.age_of_exile.gui.screens.wiki.WikiScreen;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.localization.RandomTips;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.NumberUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.age_of_exile.uncommon.wrappers.SText;
import com.robertx22.age_of_exile.vanilla_mc.packets.AllocateStatPacket;
import com.robertx22.library_of_exile.gui.HelpButton;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.GuiUtils;
import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.library_of_exile.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CharacterScreen extends BaseScreen implements INamedScreen {

    static int sizeX = 256;
    static int sizeY = 178;

    Minecraft mc = Minecraft.getInstance();

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

        ResourceLocation getIcon() {
            return new ResourceLocation(SlashRef.MODID, "textures/gui/stat_groups/" + id + ".png");
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
        addTo(StatType.MAIN, Arrays.asList(Stats.CRIT_CHANCE.get(), Stats.CRIT_DAMAGE.get()));
        addTo(StatType.MAIN, Arrays.asList(SpellDamage.getInstance(), Stats.ATTACK_SPEED.get(), Stats.COOLDOWN_REDUCTION.get()));

        addTo(StatType.ELEMENTAL, new AttackDamage(Elements.Elemental).generateAllPossibleStatVariations());
        addTo(StatType.ELEMENTAL, Stats.ELEMENTAL_SPELL_DAMAGE.getAll());

        addTo(StatType.RESISTS, new ElementalResist(Elements.Elemental).generateAllPossibleStatVariations());
        addTo(StatType.RESISTS, new ElementalPenetration(Elements.Elemental).generateAllPossibleStatVariations());

        addTo(StatType.MISC, Arrays.asList(BonusExp.getInstance(), MagicFind.getInstance(), ItemFind.getInstance()));

    }

    public CharacterScreen() {
        super(sizeX, sizeY);
    }

    @Override
    public ResourceLocation iconLocation() {
        return new ResourceLocation(SlashRef.MODID, "textures/gui/main_hub/icons/stat_overview.png");
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

            List<ITextComponent> help = new ArrayList<>();

            help.add(Words.DidYouKnow.locName());
            help.add(new StringTextComponent(""));
            help.addAll(TooltipUtils.cutIfTooLong(RandomUtils.randomFromList(Arrays.asList(RandomTips.values()))
                .locName()));
            addButton(new HelpButton(help, guiLeft + 15, guiTop + 15));

            if (true) {

                xpos = guiLeft + 78;
                ypos = guiTop + 105;

                int YSEP = 20;

                // TODO MAKE STATIC IDS
                addButton(new AllocateStatButton(AllAttributes.VIT_ID, xpos, ypos));
                ypos += YSEP;
                addButton(new AllocateStatButton(AllAttributes.WIS_ID, xpos, ypos));
                ypos += YSEP;
                addButton(new AllocateStatButton(AllAttributes.AGI_ID, xpos, ypos));

                xpos = guiLeft + 159;
                ypos = guiTop + 105;

                addButton(new AllocateStatButton(AllAttributes.STR_ID, xpos, ypos));
                ypos += YSEP;
                addButton(new AllocateStatButton(AllAttributes.INT_ID, xpos, ypos));
                ypos += YSEP;
                addButton(new AllocateStatButton(AllAttributes.DEX_ID, xpos, ypos));

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
                    if (stat.show) {
                        addButton(new StatButton(stat, xpos, ypos + (YSPACING * ynum)));

                        ynum++;
                    }
                }
                ynum = 0;
                xpos += XSPACING;
            }
        }

        if (this.isMainScreen()) {
            if (ServerContainer.get().ENABLE_FAVOR_SYSTEM.get()) {
                addButton(new FavorButton(guiLeft + sizeX / 2 - FavorButton.FAVOR_BUTTON_SIZE_X / 2, guiTop - FavorButton.FAVOR_BUTTON_SIZE_Y));
            }

            if (ServerContainer.get().MAX_DIFFICULTY.get() > 1) {
                addButton(new ScalingDifficultyButton(guiLeft + sizeX / 2 - ScalingDifficultyButton.BUTTON_SIZE_X / 2, guiTop + CharacterScreen.sizeY + 5));
            }

            // hub buttons

            List<INamedScreen> screens = new ArrayList<>();
            screens.add(new SpellScreen());
            if (MMORPG.RUN_DEV_TOOLS) {
                screens.add(new TalentsScreen());
                // todo until talents are done
            }
            screens.add(new ProfessionsScreen());
            screens.add(new WikiScreen());

            int x = guiLeft + sizeX - 1;
            int y = guiTop + 20;

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
            addButton(new PlayerGearButton(mc.player, this, this.guiLeft + CharacterScreen.sizeX / 2 - PlayerGearButton.xSize / 2, this.guiTop + 10));
        }
    }

    private static final ResourceLocation BACKGROUND = new ResourceLocation(SlashRef.MODID, "textures/gui/stats.png");
    private static final ResourceLocation WIDE_BACKGROUND = new ResourceLocation(SlashRef.MODID, "textures/gui/full_stats_panel.png");

    @Override
    public void render(MatrixStack matrix, int x, int y, float ticks) {

        if (isMainScreen()) {
            mc.getTextureManager()
                .bind(BACKGROUND);
        } else {
            mc.getTextureManager()
                .bind(WIDE_BACKGROUND);
        }

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        blit(matrix, mc.getWindow()
                .getGuiScaledWidth() / 2 - sizeX / 2,
            mc.getWindow()
                .getGuiScaledHeight() / 2 - sizeY / 2, 0, 0, sizeX, sizeY
        );

        super.render(matrix, x, y, ticks);

        buttons.forEach(b -> b.renderToolTip(matrix, x, y));

        int p = Load.playerRPGData(mc.player).statPoints
            .getFreePoints(mc.player);
        if (p > 0) {
            String points = "Points: " + p;
            mc.font.drawShadow(matrix, points, guiLeft + sizeX / 2 - mc.font.width(points) / 2, guiTop + sizeY + 25, TextFormatting.GREEN.getColor());
        }

    }

    private static final ResourceLocation STAT_PAGE_BUTTON_TEXT = new ResourceLocation(SlashRef.MODID, "textures/gui/main_hub/buttons_backwards.png");

    static int PLUS_BUTTON_SIZE_X = 13;
    static int PLUS_BUTTON_SIZE_Y = 13;

    public static class AllocateStatButton extends ImageButton {
        static int SIZEX = 18;
        static int SIZEY = 18;
        static ResourceLocation BUTTON_TEX = new ResourceLocation(SlashRef.MODID, "textures/gui/plus_button.png");

        Stat stat;

        public AllocateStatButton(String stat, int xPos, int yPos) {
            super(xPos, yPos, SIZEX, SIZEY, 0, 0, SIZEY, BUTTON_TEX, (button) -> {
                Packets.sendToServer(new AllocateStatPacket(ExileDB.Stats()
                    .get(stat)));
            });
            this.stat = ExileDB.Stats()
                .get(stat);
        }

        @Override
        public void renderToolTip(MatrixStack matrix, int x, int y) {
            if (isInside(x, y)) {

                Minecraft mc = Minecraft.getInstance();

                List<ITextComponent> tooltip = new ArrayList<>();

                tooltip.add(stat
                    .locName()
                    .withStyle(TextFormatting.GREEN));

                tooltip.add(new SText(""));

                tooltip.addAll(((CoreStat) stat).getCoreStatTooltip(Load.Unit(mc.player), Load.Unit(mc.player)
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

            Minecraft mc = Minecraft.getInstance();

            String txt = ((int) Load.Unit(mc.player)
                .getUnit()
                .getCalculatedStat(stat)
                .getValue()) + "";

            RenderUtils.render16Icon(matrix, stat.getIconForRendering(), this.x - 20, this.y + 1);

            mc.font.drawShadow(matrix, txt, this.x + SIZEX + 4, this.y + 4, TextFormatting.YELLOW.getColor());

        }

    }

    private static final ResourceLocation BUTTON_TEX = new ResourceLocation(SlashRef.MODID, "textures/gui/button.png");
    static int STAT_BUTTON_SIZE_X = 18;
    static int STAT_BUTTON_SIZE_Y = 18;

    public static class StatButton extends ImageButton {

        Stat stat;
        public String presetValue = "";

        public StatButton(Stat stat, int xPos, int yPos) {
            super(xPos, yPos, STAT_BUTTON_SIZE_X, STAT_BUTTON_SIZE_Y, 0, 0, STAT_BUTTON_SIZE_Y, BUTTON_TEX, (button) -> {
            });

            this.stat = stat;
        }

        @Override
        public void renderToolTip(MatrixStack matrix, int x, int y) {
            if (isInside(x, y)) {
                List<ITextComponent> tooltip = new ArrayList<>();

                tooltip.add(stat
                    .locName()
                    .withStyle(TextFormatting.GREEN));

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
                Minecraft mc = Minecraft.getInstance();
                String str = getStatString(Load.Unit(mc.player)
                    .getUnit()
                    .getCalculatedStat(stat), Load.Unit(mc.player));

                if (!presetValue.isEmpty()) {
                    str = presetValue;
                }

                ResourceLocation res = stat
                    .getIconForRendering();

                RenderUtils.render16Icon(matrix, res, this.x, this.y);

                mc.font.drawShadow(matrix, str, this.x + STAT_BUTTON_SIZE_X, this.y + 2, TextFormatting.GOLD.getColor());

            }
        }

    }

    public static String getStatString(StatData data, EntityData unitdata) {
        Stat stat = data.GetStat();

        String v1 = NumberUtils.formatForTooltip(data
            .getValue());

        String str = "";

        str = v1;

        if (stat.IsPercent()) {
            str += '%';
        }

        if (stat instanceof IUsableStat && stat instanceof Health == false) {
            IUsableStat usable = (IUsableStat) stat;

            String value = NumberUtils.format(
                usable.getUsableValue((int) data
                    .getValue(), unitdata.getLevel()) * 100);

            str = "" + value + "%";

        }
        return str;

    }

    static class StatPageButton extends ImageButton {

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
            Minecraft mc = Minecraft.getInstance();

            super.renderButton(matrices, x, y, delta);

            RenderUtils.render16Icon(matrices, page.getIcon(), this.x + 80, this.y + 6);

            if (isHovered()) {
                String txt = page.id;
                Minecraft.getInstance().font.drawShadow(matrices,
                    txt, this.x + SIZEX - 30 - mc.font.width(txt), this.y + 9, TextFormatting.YELLOW.getColor());
            }

        }

        @Override
        public void renderToolTip(MatrixStack matrix, int x, int y) {
            if (isInside(x, y)) {

                List<ITextComponent> tooltip = new ArrayList<>();
                GuiUtils.renderTooltip(matrix, tooltip, x, y);

            }
        }

        public boolean isInside(int x, int y) {
            return GuiUtils.isInRect(this.x, this.y, PLUS_BUTTON_SIZE_X, PLUS_BUTTON_SIZE_Y, x, y);
        }

    }

}


