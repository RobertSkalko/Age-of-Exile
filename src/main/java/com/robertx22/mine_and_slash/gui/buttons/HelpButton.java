package com.robertx22.mine_and_slash.gui.buttons;

import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.GuiUtils;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class HelpButton extends TexturedButtonWidget {

    static Identifier ID = new Identifier(Ref.MODID, "textures/gui/hotbar_setup/spell_help.png");
    List<Text> tooltip;

    public HelpButton(List<Text> tooltip, int x, int y) {
        super(x, y, 20, 20, 0, 0, 1, ID,
            action -> {

            });
        this.tooltip = tooltip;
    }

    public boolean isInside(int x, int y) {
        return GuiUtils.isInRect(this.x, this.y, 20, 20, x, y);
    }

    @Override
    public void renderToolTip(MatrixStack matrices, int mouseX, int mouseY) {
        if (isInside(mouseX, mouseY)) {
            GuiUtils.renderTooltip(matrices, tooltip, mouseX, mouseY);
        }
    }
}