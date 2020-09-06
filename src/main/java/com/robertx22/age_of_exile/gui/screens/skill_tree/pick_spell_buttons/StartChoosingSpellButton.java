package com.robertx22.age_of_exile.gui.screens.skill_tree.pick_spell_buttons;

import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.age_of_exile.gui.screens.skill_tree.IMarkOnTop;
import com.robertx22.age_of_exile.gui.screens.skill_tree.SkillTreeScreen;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class StartChoosingSpellButton extends TexturedButtonWidget implements IMarkOnTop {

    static Identifier ID = new Identifier(Ref.MODID, "textures/gui/skill_tree/hotbar.png");

    public static int XSIZE = 16;
    public static int YSIZE = 16;
    SkillTreeScreen screen;

    int hotbar;

    public StartChoosingSpellButton(SkillTreeScreen screen, WholeSpellHotbarButton hb, int hotbarPosition, int x, int y) {
        super(x, y, XSIZE, YSIZE, 0, 0, 1, ID, (action) -> {
            screen.addButtonPublic(new PossibleSpellsOverviewButton(screen, hotbarPosition, hb.x, hb.y - PossibleSpellsOverviewButton.YSIZE));

        });
        this.screen = screen;
        this.hotbar = hotbarPosition;

    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        BaseSpell spell = Load.spells(screen.mc.player)
            .getSpellByNumber(hotbar);

        if (spell != null && !spell.GUID()
            .isEmpty()) {
            MinecraftClient mc = MinecraftClient.getInstance();
            mc.getTextureManager()
                .bindTexture(spell.getIconLoc());
            RenderUtils.render16Icon(matrices, spell.getIconLoc(), this.x + 4, this.y + 4);
        }
    }

}
