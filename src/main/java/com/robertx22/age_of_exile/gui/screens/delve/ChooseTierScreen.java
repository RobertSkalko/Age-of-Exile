package com.robertx22.age_of_exile.gui.screens.delve;

import com.robertx22.age_of_exile.database.data.tiers.base.Difficulty;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.gui.screens.ILeftRight;
import com.robertx22.age_of_exile.gui.screens.race_select.LeftRightButton;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.Comparator;

public class ChooseTierScreen extends BaseScreen implements ILeftRight {

    public BlockPos teleporterPos = new BlockPos(0, 0, 0);
    public int currentTier = 0;

    public ChooseTierScreen(BlockPos pos) {
        super(MinecraftClient.getInstance()
            .getWindow()
            .getScaledWidth(), MinecraftClient.getInstance()
            .getWindow()
            .getScaledHeight());

        this.teleporterPos = pos;
    }

    static int START_X = 130;
    static int START_Y = 30;
    static int LOOT_Y = 120;

    DifficultyButton difficultyButton;

    @Override
    protected void init() {
        super.init();

        try {

            int xoff = this.width / 2;
            int yoff = this.height / 2 - BGY / 2;

            Difficulty diff = Difficulty.fromRank(currentTier);

            this.difficultyButton = this.addButton(
                new DifficultyButton(diff,
                    xoff - DifficultyButton.xSize / 2,
                    yoff - DifficultyButton.ySize / 2 + BGY / 2));

            addButton(new StartDelveMapButton(this, xoff - StartDelveMapButton.SIZE_X / 2,
                yoff - StartDelveMapButton.SIZE_Y / 2 + BGY + 10));

            int x = xoff - BGX / 2 - 30;
            int y = yoff + BGY / 2 - LeftRightButton.ySize / 2;

            this.addButton(new LeftRightButton(this, x, y, true, LeftRightButton.Type.ONE));
            this.addButton(new LeftRightButton(this, x - LeftRightButton.xSize - 1, y, true, LeftRightButton.Type.TEN));

            x = xoff + BGX / 2 + 30;

            this.addButton(new LeftRightButton(this, x, y, false, LeftRightButton.Type.ONE));
            this.addButton(new LeftRightButton(this, x + LeftRightButton.xSize + 1, y, false, LeftRightButton.Type.TEN));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void renderText(MatrixStack matrix) {

        int xoff = this.width / 2;
        int yoff = this.height / 2 - BGY / 2;

        GuiUtils.renderScaledText(matrix, guiLeft + xoff, yoff + 35, 1D, "Choose Difficulty", Formatting.RED);

    }

    static Identifier BG = Ref.guiId("select_diff/background");
    static int BGX = 203;
    static int BGY = 200;

    @Override
    public void render(MatrixStack matrix, int x, int y, float ticks) {

        try {

            int xoff = this.width / 2;
            int yoff = this.height / 2;

            mc.getTextureManager()
                .bindTexture(BG);
            drawTexture(matrix, xoff - BGX / 2, yoff - BGY / 2, 0, 0, BGX, BGY);

            super.render(matrix, x, y, ticks);

            renderText(matrix);

        } catch (Exception e) {
            e.printStackTrace();
        }

        for (ClickableWidget b : buttons) {
            b.renderToolTip(matrix, x, y);
        }
    }

    @Override
    public void goLeft() {
        this.currentTier--;

        if (currentTier < 0) {
            currentTier = 0;
        }
        this.difficultyButton.tier = Difficulty.fromRank(currentTier);
    }

    @Override
    public void goRight() {
        currentTier++;

        int max = ExileDB.Difficulties()
            .getList()
            .stream()
            .max(Comparator.comparingInt(x -> x.rank))
            .get().rank;

        if (currentTier > max) {
            currentTier = max;
        }

        this.difficultyButton.tier = Difficulty.fromRank(currentTier);

    }
}