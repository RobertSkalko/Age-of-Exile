package com.robertx22.age_of_exile.gui.screens.spell;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.gui.TextUtils;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.packets.AllocateSpellPacket;
import com.robertx22.age_of_exile.vanilla_mc.packets.spells.SetupHotbarPacket;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class LearnSpellButton extends TexturedButtonWidget {

    static Identifier SPELL_SLOT = Ref.guiId("spells/slots/spell");

    public static int BUTTON_SIZE_X = 18;
    public static int BUTTON_SIZE_Y = 18;

    MinecraftClient mc = MinecraftClient.getInstance();
    SpellScreen screen;

    Spell spell;

    public LearnSpellButton(SpellScreen screen, Spell spell, int xPos, int yPos) {
        super(xPos, yPos, BUTTON_SIZE_X, BUTTON_SIZE_Y, 0, 0, BUTTON_SIZE_Y, SPELL_SLOT, (button) -> {

            if (SpellScreen.IS_PICKING_HOTBAR_SPELL) {
                SpellScreen.IS_PICKING_HOTBAR_SPELL = false;
                Packets.sendToServer(new SetupHotbarPacket(spell, SpellScreen.NUMBER_OF_HOTBAR_FOR_PICKING));
            } else {
                Packets.sendToServer(new AllocateSpellPacket(spell, AllocateSpellPacket.ACTION.ALLOCATE));
            }
        });
        this.screen = screen;
        this.spell = spell;

    }

    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float delta) {
        MinecraftClient mc = MinecraftClient.getInstance();

        mc.getTextureManager()
            .bindTexture(SPELL_SLOT);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexture(matrix, x, y, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X);

        mc.getTextureManager()
            .bindTexture(spell.getIconLoc());
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexture(matrix, x + 1, y + 1, 16, 16, 16, 16, 16, 16);

        int currentlvl = Load.spells(mc.player)
            .getLevelOf(spell.GUID());
        int maxlvl = spell.getMaxLevel();
        String lvltext = currentlvl + "/" + maxlvl;
        TextUtils.renderText(matrix, 0.8F, lvltext, x + BUTTON_SIZE_X / 2, (int) (y + BUTTON_SIZE_Y * 0.85F), Formatting.GREEN);

    }

    @Override
    public void renderToolTip(MatrixStack matrix, int x, int y) {
        if (isInside(x, y)) {

            List<Text> tooltip = new ArrayList<>();

            TooltipInfo info = new TooltipInfo(mc.player);

            tooltip.addAll(spell.GetTooltipString(info));

            GuiUtils.renderTooltip(matrix, tooltip, x, y);

        }
    }

    public boolean isInside(int x, int y) {
        return GuiUtils.isInRect(this.x, this.y, BUTTON_SIZE_X, BUTTON_SIZE_Y, x, y);
    }

}
