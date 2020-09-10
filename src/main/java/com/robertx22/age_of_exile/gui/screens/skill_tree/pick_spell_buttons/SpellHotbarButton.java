package com.robertx22.age_of_exile.gui.screens.skill_tree.pick_spell_buttons;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.gui.screens.skill_tree.IMarkOnTop;
import com.robertx22.age_of_exile.gui.screens.skill_tree.SkillTreeScreen;
import com.robertx22.age_of_exile.gui.screens.skill_tree.pick_spell_buttons.picking.PossibleSpellsOverviewButton;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RenderUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.spells.HotbarSetupPacket;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class SpellHotbarButton extends TexturedButtonWidget implements IMarkOnTop {

    static Identifier ID = new Identifier(Ref.MODID, "textures/gui/skill_tree/hotbar.png");

    public static int XSIZE = 16;
    public static int YSIZE = 16;
    SkillTreeScreen screen;

    int hotbar;

    public SpellHotbarButton(SkillTreeScreen screen, WholeSpellHotbarButton hb, int hotbarPosition, int x, int y) {
        super(x, y, XSIZE, YSIZE, 0, 0, 1, ID, (action) -> {
            screen.addButtonPublic(new PossibleSpellsOverviewButton(screen, hotbarPosition, hb.x, hb.y - PossibleSpellsOverviewButton.YSIZE));

        });
        this.screen = screen;
        this.hotbar = hotbarPosition;

    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        Spell spell = Load.spells(screen.mc.player)
            .getSpellByNumber(hotbar);

        if (spell != null && !spell.GUID()
            .isEmpty()) {
            MinecraftClient mc = MinecraftClient.getInstance();
            mc.getTextureManager()
                .bindTexture(spell.getIconLoc());
            RenderUtils.render16Icon(matrices, spell.getIconLoc(), this.x + 0, this.y + 0);
        }
    }

    @Override
    public void renderToolTip(MatrixStack matrices, int mouseX, int mouseY) {
        Spell spell = Load.spells(screen.mc.player)
            .getSpellByNumber(hotbar);

        if (spell != null && !spell.GUID()
            .isEmpty()) {
            List<Text> tooltip = new ArrayList<>();
            tooltip.addAll(new SpellCastContext(MinecraftClient.getInstance().player, 0, spell).calcData.GetTooltipString(new TooltipInfo(MinecraftClient.getInstance().player)));
            if (this.isInside(mouseX, mouseY)) {

                GuiUtils.renderTooltip(matrices, tooltip, mouseX, mouseY);
            }
        }
    }

    public boolean isInside(int x, int y) {
        return GuiUtils.isInRect(this.x, this.y, width, height, x, y);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.active && this.visible) {

            boolean bl = this.clicked(mouseX, mouseY);
            if (bl) {
                this.playDownSound(MinecraftClient.getInstance()
                    .getSoundManager());

                if (button == 0) {
                    this.onClick(mouseX, mouseY);
                }
                if (button == 1) {
                    Packets.sendToServer(new HotbarSetupPacket(Spell.SERIALIZER, hotbar));
                }

                return true;
            }

            return false;
        } else {
            return false;
        }
    }

}
