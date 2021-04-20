package com.robertx22.age_of_exile.gui.screens.wiki;

import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.gui.bases.INamedScreen;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WikiScreen extends BaseScreen implements INamedScreen {
    private static final Identifier BACKGROUND = new Identifier(Ref.MODID, "textures/gui/wiki/wiki.png");

    static int sizeX = 256;
    static int sizeY = 207;

    MinecraftClient mc = MinecraftClient.getInstance();

    public WikiScreen() {
        super(sizeX, sizeY);
    }

    WikiType currentType = WikiType.UNIQUE_GEARS;

    public void changeType(WikiType type) {
        this.currentType = type;
        this.index = 0;
        this.init();
    }

    int index = 0;

    public static int ENTRY_BUTTONS_MAX = 7;

    List<WikiEntry> entries = new ArrayList<>();

    @Override
    public Identifier iconLocation() {
        return new Identifier(Ref.MODID, "textures/gui/main_hub/icons/wiki.png");
    }

    @Override
    public Words screenName() {
        return Words.Wiki;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void init() {
        super.init();

        int x = guiLeft + 8;
        int y = guiTop + 9;

        this.buttons.clear();

        int amount = ENTRY_BUTTONS_MAX;

        this.entries = this.currentType.getAllEntries();

        for (int i = index; i < amount + index; i++) {

            if (entries.size() > i) {
                this.addButton(new WikiEntryButton(this, entries.get(i), x, y));
                y += WikiEntryButton.ySize - 1;
            }
        }

        x = guiLeft + 3;
        y = guiTop - 3 - WikiTypeButton.ySize;

        for (int i = 0; i < WikiType.values().length; i++) {

            WikiType type = Arrays.asList(WikiType.values())
                .get(i);

            if (type.showsInWiki()) {
                this.addButton(new WikiTypeButton(this, type, x, y));
                x += WikiTypeButton.xSize + 3;
            }
        }

    }

    public void moveUp() {
        if (index >= entries.size()) {
            index = 0;
        } else {
            index++;
        }
        init();
    }

    public void moveDown() {
        if (index <= 0) {
            index = entries.size() - 1;
        } else {
            index--;
        }
        init();
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scroll) {
        if (scroll < 0) {
            moveUp();
        }
        if (scroll > 0) {
            moveDown();
        }
        return true;
    }

    @Override
    public void render(MatrixStack matrix, int x, int y, float ticks) {

        renderBackground(matrix, BACKGROUND);

        super.render(matrix, x, y, ticks);

        buttons.forEach(b -> {

            if (b instanceof WikiEntryButton == false) {
                b.render(matrix, x, y, ticks); // render other buttons under the huge entry button
            }

            b.renderToolTip(matrix, x, y);
        });

    }

}
