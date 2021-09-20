package com.robertx22.age_of_exile.gui.screens.dungeon;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.robertx22.age_of_exile.dimension.dungeon_data.TeamSize;
import com.robertx22.age_of_exile.dimension.packets.StartDungeonPacket;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.CLOC;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class StartDungeonButton extends ImageButton {

    public static int SIZE_X = 49;
    public static int SIZE_Y = 16;

    static ResourceLocation ID = SlashRef.guiId("dungeon/start_dungeon_button");

    Minecraft mc = Minecraft.getInstance();

    TeamSize teamSize;

    public StartDungeonButton(TeamSize teamSize, DungeonInfoScreen screen, int xPos, int yPos) {
        super(xPos, yPos, SIZE_X, SIZE_Y, 0, 0, SIZE_Y, ID, (button) -> {
            Packets.sendToServer(new StartDungeonPacket(teamSize, screen.teleporterPos));

            screen.onClose();
        });
        this.teamSize = teamSize;
    }

    @Override
    public void renderToolTip(MatrixStack matrix, int x, int y) {
        if (isInside(x, y)) {
            List<ITextComponent> tooltip = new ArrayList<>();
            tooltip.add(new StringTextComponent("Choose this dungeon to start."));

            if (teamSize.requiredMemberAmount > 1) {
                tooltip.add(new StringTextComponent("This starts the dungeon in team mode."));
                tooltip.add(new StringTextComponent("/age_of_exile teams, is required to play."));
                tooltip.add(new StringTextComponent("The enemies are much more powerful but so are the rewards!"));

                if (teamSize.requiredMemberAmount > 2) {
                    tooltip.add(new StringTextComponent("A dedicated Tank, Healer and Damage Dealer are recommended."));
                }
            }

            GuiUtils.renderTooltip(matrix, tooltip, x, y);

        }
    }

    public boolean isInside(int x, int y) {
        return GuiUtils.isInRect(this.x, this.y, SIZE_X, SIZE_Y, x, y);
    }

    @Override
    public void renderButton(MatrixStack matrix, int x, int y, float f) {
        super.renderButton(matrix, x, y, f);

        String txt = CLOC.translate(teamSize.word.locName());

        int width = mc.font.width(txt);

        mc.font.drawShadow(matrix, txt, this.x + (SIZE_X / 2F - (width / 2F)), this.y + 3, TextFormatting.WHITE.getColor());
    }

}
