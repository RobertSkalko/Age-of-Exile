package com.robertx22.age_of_exile.gui.screens.spell;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.database.data.synergy.Synergy;
import com.robertx22.age_of_exile.database.data.value_calc.LevelProvider;
import com.robertx22.age_of_exile.gui.TextUtils;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.packets.spells.AllocateSynergyPacket;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class LearnSynergyButton extends ImageButton {

    static ResourceLocation SPELL_SLOT = SlashRef.guiId("spells/slots/synergy");

    public static int BUTTON_SIZE_X = 18;
    public static int BUTTON_SIZE_Y = 18;

    Minecraft mc = Minecraft.getInstance();
    SpellScreen screen;

    Synergy synergy;

    public LearnSynergyButton(SpellScreen screen, Synergy syn, int xPos, int yPos) {
        super(xPos, yPos, BUTTON_SIZE_X, BUTTON_SIZE_Y, 0, 0, BUTTON_SIZE_Y, SPELL_SLOT, (button) -> {
            Packets.sendToServer(new AllocateSynergyPacket(screen.currentSchool(), syn, AllocateSynergyPacket.ACTION.ALLOCATE));

        });
        this.screen = screen;
        this.synergy = syn;

    }

    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float delta) {
        Minecraft mc = Minecraft.getInstance();

        mc.getTextureManager()
            .bind(SPELL_SLOT);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        blit(matrix, x, y, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X);

        mc.getTextureManager()
            .bind(synergy.getIconLoc());
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        blit(matrix, x + 1, y + 1, 16, 16, 16, 16, 16, 16);

        int currentlvl = Load.spells(mc.player)
            .getLevelOf(synergy.GUID());
        int maxlvl = synergy.getMaxLevel();
        String lvltext = currentlvl + "/" + maxlvl;
        TextUtils.renderText(matrix, 0.8F, lvltext, x + BUTTON_SIZE_X / 2, (int) (y + BUTTON_SIZE_Y * 0.85F), TextFormatting.GREEN);

    }

    @Override
    public void renderToolTip(MatrixStack matrix, int x, int y) {
        if (isInside(x, y)) {

            List<ITextComponent> tooltip = new ArrayList<>();

            tooltip.add(synergy.locName()
                .withStyle(TextFormatting.LIGHT_PURPLE, TextFormatting.BOLD));

            TooltipInfo info = new TooltipInfo(mc.player);

            synergy.getStats(new LevelProvider(mc.player, synergy))
                .forEach(s -> {
                    tooltip.addAll(s.GetTooltipString(info));
                });

            int reqlvl = screen.currentSchool()
                .getLevelNeededToAllocate(screen.currentSchool().synergies.get(synergy.GUID()));

            tooltip.add(new StringTextComponent("Required Level: " + reqlvl).withStyle(TextFormatting.RED));

            GuiUtils.renderTooltip(matrix, tooltip, x, y);

        }
    }

    public boolean isInside(int x, int y) {
        return GuiUtils.isInRect(this.x, this.y, BUTTON_SIZE_X, BUTTON_SIZE_Y, x, y);
    }

}

