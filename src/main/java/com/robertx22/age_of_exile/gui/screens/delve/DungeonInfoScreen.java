package com.robertx22.age_of_exile.gui.screens.delve;

import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonData;
import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonGridType;
import com.robertx22.age_of_exile.dimension.dungeon_data.TeamSize;
import com.robertx22.age_of_exile.dimension.player_data.PlayerMapsCap;
import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.gui.screens.wiki.entries.UniqueGearEntry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.gui.ItemSlotButton;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class DungeonInfoScreen extends BaseScreen {

    public BlockPos teleporterPos = new BlockPos(0, 0, 0);
    public DungeonData selectedDungeon = null;

    PointData point;

    public DungeonInfoScreen(BlockPos pos, DungeonData dungeon, PointData point) {
        super(MinecraftClient.getInstance()
            .getWindow()
            .getScaledWidth(), MinecraftClient.getInstance()
            .getWindow()
            .getScaledHeight());

        this.point = point;
        this.selectedDungeon = dungeon;
        this.teleporterPos = pos;

    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) { // escape
            MinecraftClient.getInstance()
                .openScreen(new DelveScreen(teleporterPos)); // go back to delve screen
            return false;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    static int START_X = 130;
    static int START_Y = 30;
    static int LOOT_Y = 120;

    @Override
    protected void init() {
        super.init();

        try {
            PlayerMapsCap maps = Load.playerMaps(mc.player);

            int xoff = this.width / 2;
            int yoff = this.height / 2 - 125;

            // xoff = 0;
            // yoff = 0; // TODO

            if (selectedDungeon == null) {
                return;
            }
            if (Load.playerMaps(mc.player)
                .canStart(point, selectedDungeon)) {
                int teamsY = yoff + 185;

                for (TeamSize size : TeamSize.values()) {
                    addButton(new StartDungeonButton(size, this, point, xoff - StartDungeonButton.SIZE_X / 2, teamsY));
                    teamsY += 16;

                }
            }

            this.addButton(new DifficultyButton(selectedDungeon.getDifficulty(), xoff - DifficultyButton.xSize / 2 + 20, yoff + 20));
            this.addButton(new DungeonButton(selectedDungeon, xoff - DifficultyButton.xSize / 2 - 20, yoff + 20));

            int x = xoff - 50;
            int y = yoff + LOOT_Y;

            for (int i = 0; i < selectedDungeon.u.u.size(); i++) {

                ItemStack stack = new UniqueGearEntry(ExileDB.UniqueGears()
                    .get(selectedDungeon.u.u.get(i))).createMainStack();

                this.publicAddButton(new ItemSlotButton(stack, x, y));
                x += ItemSlotButton.xSize + 1;
            }
            ItemStack randomitem = new ItemStack(Items.CHEST);
            randomitem.setCustomName(new LiteralText(Formatting.DARK_PURPLE + "Random Unique"));
            this.publicAddButton(new ItemSlotButton(randomitem, x, y));

            this.publicAddButton(new ItemSlotButton(maps.data.getStartCostOf(point), xoff - 8, y + 40));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void renderText(MatrixStack matrix) {

        if (selectedDungeon != null) {

            int xoff = this.width / 2;
            int yoff = this.height / 2 - 125;

            GuiUtils.renderScaledText(matrix, guiLeft + xoff, yoff - 5, 1D, "Dungeon Info", Formatting.RED);

            GuiUtils.renderScaledText(matrix, guiLeft + xoff, yoff + LOOT_Y - 10, 1D, "Possible Drops:", Formatting.GREEN);

            GuiUtils.renderScaledText(matrix, guiLeft + xoff, yoff + LOOT_Y + 30, 1D, "Cost:", Formatting.GOLD);
        }
    }

    @Override
    public void render(MatrixStack matrix, int x, int y, float ticks) {

        try {

            DelveScreen.renderBackgroundIcon(DungeonGridType.WALL.icon, this, 0);

            Identifier BG = Ref.guiId("dungeon/map");

            int BGX = 123;
            int BGY = 235;

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
}