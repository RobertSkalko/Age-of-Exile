package com.robertx22.mine_and_slash.gui.screens.spell_hotbar_setup;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.mine_and_slash.capability.player.PlayerSpellCap;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.gui.bases.BaseScreen;
import com.robertx22.mine_and_slash.gui.bases.INamedScreen;
import com.robertx22.mine_and_slash.mmorpg.Packets;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipContext;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.mine_and_slash.saveclasses.item_classes.SkillGemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.datasaving.SkillGem;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.GuiUtils;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.RenderUtils;
import com.robertx22.mine_and_slash.vanilla_mc.items.misc.SkillGemItem;
import com.robertx22.mine_and_slash.vanilla_mc.packets.spells.HotbarSetupPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SpellHotbatSetupScreen extends BaseScreen implements INamedScreen {

    static Identifier BACKGROUND_TEXTURE = new Identifier(
        Ref.MODID, "textures/gui/hotbar_setup/window.png");
    public MinecraftClient mc;

    static int x = 212;
    static int y = 222;

    PlayerSpellCap.ISpellsCap spells;

    public SpellHotbatSetupScreen() {
        super(x, y);
        this.mc = MinecraftClient.getInstance();
        this.spells = Load.spells(mc.player);
    }

    @Override
    protected void init() {
        super.init();

        int x = guiLeft + 7;
        int y = guiTop + 40;

        int count = 0;

        for (int i = 0; i < mc.player.inventory.main.size(); i++) {

            ItemStack stack = mc.player.inventory.main.get(i);

            if (stack.getItem() instanceof SkillGemItem) {

                if (stack == null) {
                    continue;
                }

                if (count >= 11) {
                    y += AvailableSpellButton.ySize + 2;
                    x = guiLeft + 7;
                    count = 0;
                }
                if (count >= 1) {
                    x += AvailableSpellButton.xSize + 2;
                }
                count++;
                addButton(new AvailableSpellButton(i, stack, SkillGem.Load(stack), x, y));
            }

        }
        count = 0;
        /////////////////

        y = guiTop + 140;

        y += 50;
        x = guiLeft + 15;

        for (int i = 0; i < 9; i++) {

            HotbarButton but = new HotbarButton(i, x, y);

            this.addButton(but);

            x += HotbarButton.xSize;

        }

    }

    @Override
    public void render(MatrixStack matrix, int x, int y, float ticks) {

        try {
            drawBackground(matrix);

            drawText(matrix);

            super.render(matrix, x, y, ticks);

            this.buttons.forEach(b -> b.renderToolTip(matrix, x, y));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void drawText(MatrixStack matrix) {

        double scale = 1.25;

        String str = "Spell Hotbar";
        int xp = (int) (guiLeft + (SpellHotbatSetupScreen.x / 2));
        int yp = 180 + guiTop;
        GuiUtils.renderScaledText(matrix, xp, yp, scale, str, Formatting.GREEN);

        str = "Skill Gems in Inventory";
        xp = (int) (guiLeft + (SpellHotbatSetupScreen.x / 2));
        yp = 12 + guiTop;
        GuiUtils.renderScaledText(matrix, xp, yp, scale, str, Formatting.YELLOW);

        scale = 1;

        str = "Cast by right clicking with a staff.";
        xp = (int) (guiLeft + (SpellHotbatSetupScreen.x / 2));
        yp = 100 + guiTop;
        GuiUtils.renderScaledText(matrix, xp, yp, scale, str, Formatting.YELLOW);

    }

    protected void drawBackground(MatrixStack matrix) {
        MinecraftClient.getInstance()
            .getTextureManager()
            .bindTexture(BACKGROUND_TEXTURE);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexture(matrix, guiLeft, guiTop, this.getZOffset(), 0.0F, 0.0F, this.x, this.y, 256, 256);

    }

    @Override
    public Identifier iconLocation() {
        return new Identifier(Ref.MODID, "textures/gui/main_hub/icons/spellbar.png");
    }

    @Override
    public Words screenName() {
        return Words.Spellbar;
    }

    public static HotbarButton barBeingPicked = null;

    static class HotbarButton extends TexturedButtonWidget {

        public static int xSize = 20;
        public static int ySize = 20;

        static Identifier NORMAL_TEX = new Identifier(
            Ref.MODID, "textures/gui/hotbar_setup/hotbar_button.png");
        static Identifier PICKED_TEX = new Identifier(
            Ref.MODID, "textures/gui/hotbar_setup/picked_bar.png");

        int number;

        SkillGemData skillgem;

        public HotbarButton(int number, int xPos, int yPos) {
            super(xPos, yPos, xSize, ySize, 0, 0, ySize + 1, new Identifier(""), (button) -> {
            });

            this.number = number;
            this.skillgem = Load.spells(MinecraftClient.getInstance().player)
                .getCastingData()
                .getSkillGemByNumber(number);

        }

        @Override
        public void renderToolTip(MatrixStack matrix, int mouseX, int mouseY) {
            if (this.getSpell() != null) {
                if (GuiUtils.isInRectPoints(new Point(x, y), new Point(xSize, ySize), new Point(mouseX, mouseY))) {
                    List<Text> tooltip = new ArrayList<>();

                    TooltipContext ctx = new TooltipContext(null, tooltip, Load.Unit(ClientOnly.getPlayer()));

                    skillgem.BuildTooltip(ctx);

                    GuiUtils.renderTooltip(matrix, tooltip, mouseX, mouseY);
                }
            }
        }

        @Override
        public void onPress() {
            super.onPress();

            if (this.getSpell() != null) {
                Packets.sendToServer(new HotbarSetupPacket(-1, number));
            } else {
                SpellHotbatSetupScreen.barBeingPicked = this;
            }

        }

        public BaseSpell getSpell() {
            return Load.spells(MinecraftClient.getInstance().player)
                .getCastingData()
                .getSpellByNumber(number);

        }

        @Override
        public void renderButton(MatrixStack matrix, int x, int y, float ticks) {

            MinecraftClient mc = MinecraftClient.getInstance();

            if (SpellHotbatSetupScreen.barBeingPicked == this) {
                mc.getTextureManager()
                    .bindTexture(PICKED_TEX);
            } else {
                mc.getTextureManager()
                    .bindTexture(NORMAL_TEX);
            }

            RenderSystem.disableDepthTest();

            drawTexture(matrix, this.x, this.y, 0, 0, this.width, this.height);

            RenderSystem.enableDepthTest();

            BaseSpell spell = getSpell();

            if (spell != null) {
                RenderUtils.render16Icon(matrix, spell.getIconLoc(), this.x + 2, this.y + 2);
            }
        }

    }

    static class AvailableSpellButton extends TexturedButtonWidget {

        public static int xSize = 16;
        public static int ySize = 16;

        static Identifier buttonLoc = new Identifier(Ref.MODID, "textures/gui/main_hub/buttons.png");

        ItemStack stack;
        SkillGemData skillgem;
        int invslot;

        public AvailableSpellButton(int invslot, ItemStack stack, SkillGemData skillgem, int xPos, int yPos) {

            super(xPos, yPos, xSize, ySize, 0, 0, ySize + 1, new Identifier(""), (button) -> {

                if (SpellHotbatSetupScreen.barBeingPicked != null) {

                    HotbarButton bar = SpellHotbatSetupScreen.barBeingPicked;

                    Packets.sendToServer(new HotbarSetupPacket(invslot, bar.number));

                }

            });
            this.invslot = invslot;
            this.skillgem = skillgem;
        }

        @Override
        public void renderToolTip(MatrixStack matrix, int mouseX, int mouseY) {
            if (skillgem != null) {
                if (GuiUtils.isInRectPoints(new Point(x, y), new Point(xSize, ySize), new Point(mouseX, mouseY))) {
                    TooltipInfo info = new TooltipInfo(MinecraftClient.getInstance().player);

                    TooltipContext ctx = new TooltipContext(stack, new ArrayList<>(), Load.Unit(MinecraftClient.getInstance().player));
                    skillgem.BuildTooltip(ctx);
                    GuiUtils.renderTooltip(matrix, ctx.tooltip, mouseX, mouseY);
                }
            }
        }

        @Override
        public void renderButton(MatrixStack matrix, int x, int y, float ticks) {
            //super.renderButton(x, y, ticks);

            if (skillgem != null && skillgem.getSpell()
                .getIconLoc() != null) {
                RenderUtils.render16Icon(matrix, skillgem.getSpell()
                    .getIconLoc(), this.x, this.y);
            }
        }

    }

}

