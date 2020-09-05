package com.robertx22.age_of_exile.gui.screens.skill_tree;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.mmorpg.Ref;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ConnectionButton extends TexturedButtonWidget {

    public static int SIZE = 6;

    static Identifier ID = new Identifier(Ref.MODID, "textures/gui/skill_tree/lines.png");

    Perk.Connection connection;

    public ConnectionButton(Perk.Connection con, int x, int y) {
        super(x, y, SIZE, SIZE, 0, 0, 0, ID, (action) -> {
        });
        this.connection = con;
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {

        MinecraftClient mc = MinecraftClient.getInstance();
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