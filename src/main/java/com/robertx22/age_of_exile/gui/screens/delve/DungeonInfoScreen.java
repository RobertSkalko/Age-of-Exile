package com.robertx22.age_of_exile.gui.screens.delve;

import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonData;
import com.robertx22.age_of_exile.dimension.player_data.PlayerMapsCap;
import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.gui.screens.ItemSlotButton;
import com.robertx22.age_of_exile.gui.screens.wiki.entries.UniqueGearEntry;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Formatting;
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

    static int START_X = 130;
    static int START_Y = 30;
    static int LOOT_Y = 120;

    @Override
    protected void init() {
        super.init();

        try {
            PlayerMapsCap maps = Load.playerMaps(mc.player);

            selectedDungeon = maps.data.dungeon_datas.get(maps.data.point_pos);

            int xoff = this.width / 2 - 130;
            int yoff = this.height / 2 - 130;

            xoff = 0;
            yoff = 0; // TODO

            if (selectedDungeon == null) {
                return;
            }
            if (Load.playerMaps(mc.player)
                .canStart(point, selectedDungeon)) {
                addButton(new StartDungeonButton(false, this, point, xoff + 132, yoff + 150));
                addButton(new StartDungeonButton(true, this, point, xoff + 132 + 5 + StartDungeonButton.SIZE_X, yoff + 150));
            }

            this.addButton(new DifficultyButton(Database.Tiers()
                .get(selectedDungeon.t + ""), xoff + 165, yoff + 44));

            int x = xoff + START_X;
            int y = yoff + LOOT_Y;

            for (int i = 0; i < selectedDungeon.uniq.u.size(); i++) {

                ItemStack stack = new UniqueGearEntry(Database.UniqueGears()
                    .get(selectedDungeon.uniq.u.get(i))).createMainStack();

                this.publicAddButton(new ItemSlotButton(stack, x, y));
                x += ItemSlotButton.xSize + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void renderText(MatrixStack matrix) {

        if (selectedDungeon != null) {
            int X = 185;

            GuiUtils.renderScaledText(matrix, guiLeft + X, guiTop + 35, 1D, "Dungeon Info", Formatting.RED);

            GuiUtils.renderScaledText(matrix, guiLeft + X, guiTop + LOOT_Y - 5, 1D, "Possible Drops:", Formatting.GREEN);
        }
    }

    @Override
    public void render(MatrixStack matrix, int x, int y, float ticks) {

        try {

            DelveScreen.renderBackgroundDirt(this, 0);

            super.render(matrix, x, y, ticks);

            renderText(matrix);

        } catch (Exception e) {
            e.printStackTrace();
        }

        for (AbstractButtonWidget b : buttons) {
            b.renderToolTip(matrix, x, y);
        }
    }
}