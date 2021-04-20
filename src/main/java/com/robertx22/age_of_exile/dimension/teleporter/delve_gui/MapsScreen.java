package com.robertx22.age_of_exile.dimension.teleporter.delve_gui;

import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonData;
import com.robertx22.age_of_exile.dimension.teleporter.MapsData;
import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.gui.screens.ItemSlotButton;
import com.robertx22.age_of_exile.gui.screens.wiki.entries.UniqueGearEntry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.List;

public class MapsScreen extends BaseScreen {

    private static final Identifier BACKGROUND = Ref.guiId("map");

    public MapsScreen() {
        super(245, 196);

        Load.playerMaps(mc.player)
            .syncToClient(mc.player);
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

        int x = guiLeft + START_X;
        int y = guiTop + LOOT_Y;

        for (int i = 0; i < selectedDungeon.uniques.uniques.size(); i++) {

            ItemStack stack = new UniqueGearEntry(Database.UniqueGears()
                .get(selectedDungeon.uniques.uniques.get(i))).createMainStack();

            this.publicAddButton(new ItemSlotButton(stack, x, y));
            x += ItemSlotButton.xSize + 1;
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

    }
}
