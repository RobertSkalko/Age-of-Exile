package com.robertx22.age_of_exile.gui.screens.skill_tree.buttons;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.talent_tree.TalentTree;
import com.robertx22.age_of_exile.gui.screens.skill_tree.SkillTreeScreen;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;

public class ConnectionButton extends ImageButton {

    public static int SIZE = 6;

    public static ResourceLocation ID = new ResourceLocation(SlashRef.MODID, "textures/gui/skill_tree/lines.png");

    TalentTree school;
    PointData one;
    PointData two;

    SkillTreeScreen screen;
    Minecraft mc = Minecraft.getInstance();

    public ConnectionButton(SkillTreeScreen screen, TalentTree school, PointData one, PointData two, int x, int y) {
        super(x, y, SIZE, SIZE, 0, 0, 0, ID, (action) -> {
        });
        this.school = school;
        this.one = one;
        this.two = two;

        this.screen = screen;

        connection = Load.playerRPGData(mc.player).talents
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
                    connection = Load.playerRPGData(mc.player).talents
                        .getConnection(school, one, two);
                }
            }
        }

        //RenderSystem.enableDepthTest();

        if (connection == Perk.Connection.POSSIBLE) {
            blit(matrices, this.x, this.y, 0, 0, 6, 6);
        } else if (connection == Perk.Connection.LINKED) {
            blit(matrices, this.x, this.y, 6, 0, 6, 6);
        } else if (connection == Perk.Connection.BLOCKED) {
            blit(matrices, this.x, this.y, 12, 0, 6, 6);
        }

    }
}