package com.robertx22.age_of_exile.gui.screens.skill_tree.buttons;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.spell_schools.SpellSchool;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ConnectionButton extends TexturedButtonWidget {

    public static int SIZE = 6;

    static Identifier ID = new Identifier(Ref.MODID, "textures/gui/skill_tree/lines.png");

    SpellSchool school;
    PointData one;
    PointData two;
    MinecraftClient mc = MinecraftClient.getInstance();
    ;

    public ConnectionButton(SpellSchool school, PointData one, PointData two, int x, int y) {
        super(x, y, SIZE, SIZE, 0, 0, 0, ID, (action) -> {
        });
        this.school = school;
        this.one = one;
        this.two = two;

    }

    int ticks = 0;
    Perk.Connection connection;

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {

        if (this.x < 0 || this.x > mc.getWindow()
            .getScaledWidth()) {
            // return; // if outside of screen, don't waste time rendering it
        }
        if (this.y < 0 || this.y > mc.getWindow()
            .getScaledHeight()) {
            // return; // if outside of screen, don't waste time rendering it
        }

        ticks++;

        if (connection == null || ticks % 10 == 0) {
            connection = Load.perks(mc.player)
                .getConnection(school, one, two);
        }

        mc.getTextureManager()
            .bindTexture(ID);
        RenderSystem.enableDepthTest();

        if (connection == Perk.Connection.POSSIBLE) {
            drawTexture(matrices, this.x, this.y, 0, 0, 6, 6);
        } else if (connection == Perk.Connection.LINKED) {
            drawTexture(matrices, this.x, this.y, 6, 0, 6, 6);
        } else if (connection == Perk.Connection.BLOCKED) {
            drawTexture(matrices, this.x, this.y, 12, 0, 6, 6);
        }

    }

}