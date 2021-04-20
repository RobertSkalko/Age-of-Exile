package com.robertx22.age_of_exile.dimension.teleporter;

import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.mmorpg.SyncedToClientValues;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class MapsScreen extends BaseScreen {

    private static final Identifier BACKGROUND = Ref.guiId("map.png");

    public MapsScreen() {
        super(176, 207);
    }

    public MapsData getMapsData() {
        return SyncedToClientValues.mapsData;
    }

    @Override
    public void init() {
        super.init();

        int x = guiLeft + 8;
        int y = guiTop + 9;

    }

    @Override
    public void render(MatrixStack matrix, int x, int y, float ticks) {

        renderBackground(matrix, BACKGROUND);

    }
}
