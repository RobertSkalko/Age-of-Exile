package com.robertx22.age_of_exile.gui.screens.skill_tree.buttons;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.capability.player.EntityPerks;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.perks.PerkStatus;
import com.robertx22.age_of_exile.database.data.spell_schools.SpellSchool;
import com.robertx22.age_of_exile.gui.screens.skill_tree.SkillTreeScreen;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RenderUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.perks.PerkChangePacket;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class PerkButton extends TexturedButtonWidget {

    public static int SPACING = 26;
    public static int BIGGEST = 33;

    static Identifier ID = new Identifier(Ref.MODID, "textures/gui/skill_tree/perk_buttons.png");
    static Identifier LOCKED_TEX = new Identifier(Ref.MODID, "textures/gui/locked.png");

    public Perk perk;
    public PointData point;
    public SpellSchool school;
    public EntityPerks enperks;

    public int originalWidth;
    public int originalHeight;

    public int origX;
    public int origY;
    MinecraftClient mc = MinecraftClient.getInstance();
    SkillTreeScreen screen;

    public PerkButton(SkillTreeScreen screen, EntityPerks enperks, SpellSchool school, PointData point, Perk perk, int x, int y) {
        super(x, y, perk.getType().width, perk.getType().height, 0, 0, 1, ID, (action) -> {
        });
        this.perk = perk;
        this.point = point;
        this.school = school;
        this.enperks = enperks;

        this.origX = x;
        this.origY = y;
        this.originalWidth = this.width;
        this.originalHeight = this.height;
        this.screen = screen;

    }

    public boolean isInside(int x, int y) {

        return GuiUtils.isInRect(this.x, this.y, width, height, x, y);
    }

    @Override
    public void renderToolTip(MatrixStack matrices, int mouseX, int mouseY) {

        int MmouseX = (int) (1F / screen.zoom * mouseX);
        int MmouseY = (int) (1F / screen.zoom * mouseY);

        if (this.isInside(MmouseX, MmouseY)) {

            List<Text> tooltip = perk.GetTooltipString(new TooltipInfo(MinecraftClient.getInstance().player));
            GuiUtils.renderTooltip(matrices, tooltip, mouseX, mouseY);
        }
    }

    // copied from abstractbutton
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        mouseX = 1F / screen.zoom * mouseX;
        mouseY = 1F / screen.zoom * mouseY;

        if (this.active && this.visible) {

            boolean bl = this.clicked(mouseX, mouseY);
            if (bl) {
                this.playDownSound(MinecraftClient.getInstance()
                    .getSoundManager());

                if (button == 0) {
                    Packets.sendToServer(new PerkChangePacket(school, point, PerkChangePacket.ACTION.ALLOCATE));
                }
                if (button == 1) {
                    Packets.sendToServer(new PerkChangePacket(school, point, PerkChangePacket.ACTION.REMOVE));
                }
                this.onClick(mouseX, mouseY);

                return true;
            }

            return false;
        } else {
            return false;
        }
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {

        PerkStatus status = enperks.getStatus(MinecraftClient.getInstance().player, school, point);

        mc.getTextureManager()
            .bindTexture(ID);

        int offset = 4;

        // background
        RenderSystem.enableDepthTest();
        drawTexture(matrices, this.x, this.y, perk.getType()
            .getXOffset(), status
            .getYOffset(), this.width, this.height);

        if (this.perk.getType() == Perk.PerkType.STAT) {
            // icon
            mc.getTextureManager()
                .bindTexture(this.perk.getIcon());
            drawTexture(matrices, this.x + offset, this.y + offset, 0, 0, 16, 16, 16, 16);
        } else if (this.perk.getType() == Perk.PerkType.MAJOR) {
            // icon
            mc.getTextureManager()
                .bindTexture(this.perk.getIcon());
            offset = 8;
            RenderUtils.render16Icon(matrices, perk.getIcon(), this.x + offset, this.y + offset);
        } else if (this.perk.getType() == Perk.PerkType.SPELL_MOD) {
            // icon
            mc.getTextureManager()
                .bindTexture(this.perk.getIcon());
            offset = 5;
            drawTexture(matrices, this.x + offset, this.y + offset, 0, 0, 16, 16, 16, 16);

        } else if (perk.getType() == Perk.PerkType.START) {
            offset = 3;
            if (perk.icon == null || perk.icon.isEmpty()) {
                RenderUtils.render16Icon(matrices, new Identifier(school.icon), this.x + offset, this.y + offset);
            } else {
                RenderUtils.render16Icon(matrices, perk.getIcon(), this.x + offset, this.y + offset);
            }
        } else if (perk.getType() == Perk.PerkType.SPECIAL) {
            // icon
            offset = 6;
            mc.getTextureManager()
                .bindTexture(this.perk.getIcon());
            drawTexture(matrices, this.x + offset, this.y + offset, 0, 0, 16, 16, 16, 16);
        }

        if (this.perk.isLockedToPlayer(mc.player)) {

            if (!this.isHovered()) {
                mc.getTextureManager()
                    .bindTexture(LOCKED_TEX);

                drawTexture(matrices, this.x + offset, this.y + offset + 10, 0, 0, 16, 16, 16, 16);
            }
        }

    }

}