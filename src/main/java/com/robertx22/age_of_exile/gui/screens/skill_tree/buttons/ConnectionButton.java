package com.robertx22.age_of_exile.gui.screens.skill_tree.buttons;

import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.talent_tree.TalentTree;
import com.robertx22.age_of_exile.gui.screens.skill_tree.SkillTreeScreen;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ConnectionButton extends TexturedButtonWidget {

    public static int SIZE = 6;

    public static Identifier ID = new Identifier(Ref.MODID, "textures/gui/skill_tree/lines.png");

    TalentTree school;
    PointData one;
    PointData two;

    SkillTreeScreen screen;
    MinecraftClient mc = MinecraftClient.getInstance();

    public ConnectionButton(SkillTreeScreen screen, TalentTree school, PointData one, PointData two, int x, int y) {
        super(x, y, SIZE, SIZE, 0, 0, 0, ID, (action) -> {
        });
        this.school = school;
        this.one = one;
        this.two = two;

        this.screen = screen;

        connection = Load.perks(mc.player)
            .getConnection(school, one, two);

    }

    int ticks = 0;
    Perk.Connection connection;

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        // do nothing. use the custom method
    }

    // render here so the bind texture is only called once
    // because there are thousands of connector buttons
    public void renderButtonForReal(MatrixStack matrices, int mouseX, int mouseY, float delta) {

        ticks++;

        if (screen.mouseRecentlyClickedTicks > 1) {
            if (ticks % 10 == 0) {
                if (screen.pointClicked.equals(this.one) || screen.pointClicked.equals(this.two)) {
                    connection = Load.perks(mc.player)
                        .getConnection(school, one, two);
                }
            }
        }

        //RenderSystem.enableDepthTest();

        if (connection == Perk.Connection.POSSIBLE) {
            drawTexture(matrices, this.x, this.y, 0, 0, 6, 6);
        } else if (connection == Perk.Connection.LINKED) {
            drawTexture(matrices, this.x, this.y, 6, 0, 6, 6);
        } else if (connection == Perk.Connection.BLOCKED) {
            drawTexture(matrices, this.x, this.y, 12, 0, 6, 6);
        }

    }
}