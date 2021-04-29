package com.robertx22.age_of_exile.dimension.gui;

import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonData;
import com.robertx22.age_of_exile.dimension.teleporter.MapsData;
import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.gui.screens.ItemSlotButton;
import com.robertx22.age_of_exile.gui.screens.wiki.entries.UniqueGearEntry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class MapsScreen extends BaseScreen {

    private static final Identifier BACKGROUND = Ref.guiId("dungeon/map");

    public BlockPos teleporterPos = new BlockPos(0, 0, 0);

    public MapsScreen(BlockPos pos) {
        super(245, 196);

        this.teleporterPos = pos;

        Load.playerMaps(mc.player)
            .syncToClient(mc.player);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public MapsData getMapsData() {
        return Load.playerMaps(mc.player).mapsData;
    }

    public DungeonData selectedDungeon = null;

    @Override
    public void init() {
        super.init();

        this.buttons.clear();
        this.children.clear();

        int x = guiLeft + 8;
        int y = guiTop + 185 - DungeonButton.ySize;

        int xstart = guiLeft + 8;

        if (getMapsData().isEmpty) {
            return;
        }

        try {
            for (int i = 0; i < getMapsData().dungeonsByFloors.size(); i++) {
                List<DungeonData> list = getMapsData().dungeonsByFloors.get(i);

                x = xstart;
                for (DungeonData d : list) {

                    if (selectedDungeon == null) {
                        selectedDungeon = d;
                    }

                    this.addButton(new DungeonButton(d, this, x, y));
                    x += DungeonButton.xSize + 5;
                }

                y -= DungeonButton.ySize + 3;

            }

            addSelectedDungeonButtons();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static int START_X = 130;
    static int START_Y = 30;
    static int LOOT_Y = 120;

    public void addSelectedDungeonButtons() {

        if (Load.playerMaps(mc.player)
            .canStart(selectedDungeon)) {
            addButton(new StartDungeonButton(false, this, selectedDungeon, guiLeft + 132, guiTop + 150));
            addButton(new StartDungeonButton(true, this, selectedDungeon, guiLeft + 132 + 5 + StartDungeonButton.SIZE_X, guiTop + 150));
        }

        this.addButton(new DifficultyButton(Database.Tiers()
            .get(selectedDungeon.tier + ""), guiLeft + 165, guiTop + 44));

        int x = guiLeft + START_X;
        int y = guiTop + LOOT_Y;

        for (int i = 0; i < selectedDungeon.uniques.uniques.size(); i++) {

            ItemStack stack = new UniqueGearEntry(Database.UniqueGears()
                .get(selectedDungeon.uniques.uniques.get(i))).createMainStack();

            this.publicAddButton(new ItemSlotButton(stack, x, y));
            x += ItemSlotButton.xSize + 1;
        }

        x = guiLeft + START_X;
        y = guiTop + LOOT_Y - 30;

        for (int i = 0; i < selectedDungeon.quest_rew.stacks.size(); i++) {
            ItemStack stack = selectedDungeon.quest_rew.stacks.get(i);
            this.publicAddButton(new ItemSlotButton(stack, x, y));
            x += ItemSlotButton.xSize + 1;
        }

    }

    public void renderText(MatrixStack matrix) {

        if (selectedDungeon != null) {
            int X = 185;

            GuiUtils.renderScaledText(matrix, guiLeft + X, guiTop + 35, 1D, "Dungeon Info", Formatting.RED);

            if (!this.selectedDungeon.quest_rew.stacks.isEmpty()) {
                GuiUtils.renderScaledText(matrix, guiLeft + X, guiTop + LOOT_Y - 35, 1D, "Quest Rewards:", Formatting.YELLOW);
            }
            GuiUtils.renderScaledText(matrix, guiLeft + X, guiTop + LOOT_Y - 5, 1D, "Possible Drops:", Formatting.GREEN);
        }
    }

    public List<AbstractButtonWidget> getButtons() {
        return this.buttons;
    }

    @Override
    public void render(MatrixStack matrix, int x, int y, float ticks) {

        renderBackground(matrix, BACKGROUND);

        super.render(matrix, x, y, ticks);

        buttons.forEach(b -> b.renderToolTip(matrix, x, y));

        renderText(matrix);

    }
}
