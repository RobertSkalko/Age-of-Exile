package com.robertx22.age_of_exile.gui.screens.favor;

import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.gui.bases.INamedScreen;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.RequestSyncCapToClient;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class FavorScreen extends BaseScreen implements INamedScreen {

    static int sizeX = 256;
    static int sizeY = 166;

    MinecraftClient mc = MinecraftClient.getInstance();

    public FavorScreen() {
        super(sizeX, sizeY);
        Packets.sendToServer(new RequestSyncCapToClient(PlayerCaps.FAVOR));
    }

    @Override
    public void init() {
        super.init();

        this.addButton(new FavorBarButton(guiLeft + sizeX / 2 - BAR_BUTTON_SIZE_X / 2, guiTop + sizeY / 2));
    }

    @Override
    public Identifier iconLocation() {
        return new Identifier(Ref.MODID, "textures/gui/main_hub/icons/favor.png");
    }

    @Override
    public Words screenName() {
        return Words.AzunasFavor;
    }

    private static final Identifier BUTTON_TEX = new Identifier(Ref.MODID, "textures/gui/favor.png");
    static int BAR_BUTTON_SIZE_X = 227;
    static int BAR_BUTTON_SIZE_Y = 13;

    public class FavorBarButton extends TexturedButtonWidget {

        TextRenderer font = MinecraftClient.getInstance().textRenderer;

        public FavorBarButton(int xPos, int yPos) {
            super(xPos, yPos, BAR_BUTTON_SIZE_X, BAR_BUTTON_SIZE_Y, 0, 0, BAR_BUTTON_SIZE_Y, BUTTON_TEX, (button) -> {
            });

        }

        @Override
        public void renderToolTip(MatrixStack matrix, int x, int y) {
            if (isInside(x, y)) {
                List<Text> tooltip = new ArrayList<>();

                GuiUtils.renderTooltip(matrix,
                    tooltip, x, y);

            }
        }

        public boolean isInside(int x, int y) {
            return GuiUtils.isInRect(this.x, this.y, BAR_BUTTON_SIZE_X, BAR_BUTTON_SIZE_Y, x, y);
        }

        @Override
        public void renderButton(MatrixStack matrix, int x, int y, float f) {

            int current = Load.favor(mc.player)
                .getFavor();
            int max = Load.favor(mc.player)
                .getMaxPossibleFavor();

            String str = current + "/" + max;

            int yadd = 0;

            if (current < 1) {
                yadd = 17;
            } else if (current < 1000) {
                yadd = 32;
            } else {
                yadd = 47;
            }

            float perc = (float) current / (float) max;

            mc.getTextureManager()
                .bindTexture(BUTTON_TEX);
            drawTexture(matrix, this.x, this.y, 0, 0, this.width, this.height);
            drawTexture(matrix, this.x, this.y, 0, yadd, (int) (this.width * perc), this.height);

            FavorScreen.this.drawStringWithShadow(matrix, font, str, this.x + BAR_BUTTON_SIZE_X / 2 - font.getWidth(str) / 2, this.y + 2, Formatting.GOLD.getColorValue());

        }

    }

}
