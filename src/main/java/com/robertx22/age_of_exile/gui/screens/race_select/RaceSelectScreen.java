package com.robertx22.age_of_exile.gui.screens.race_select;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.races.PlayerRace;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.gui.screens.BaseSelectionScreen;
import com.robertx22.age_of_exile.gui.screens.ILeftRight;
import com.robertx22.age_of_exile.gui.screens.character_screen.CharacterScreen;
import com.robertx22.age_of_exile.gui.screens.race_select.RaceSelectScreen.CharButton;
import com.robertx22.age_of_exile.gui.screens.race_select.RaceSelectScreen.ChooseRaceButton;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.vanilla_mc.packets.ChooseRacePacket;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.CLOC;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.List;

public class RaceSelectScreen extends BaseSelectionScreen implements ILeftRight {

    int currentRace = 0;

    List<PlayerRace> races = Database.Races()
        .getList();

    public RaceSelectScreen() {

    }

    @Override
    public void goLeft() {
        if (currentRace == 0) {
            currentRace = races.size() - 1;
        } else {
            currentRace -= 1;
        }
        refresh();
    }

    @Override
    public void goRight() {
        if (currentRace == races.size() - 1) {
            currentRace = 0;
        } else {
            currentRace += 1;
        }

        refresh();
    }

    public void refresh() {

        init();
    }

    @Override
    protected void init() {
        super.init();

        this.buttons.clear();

        int x = this.width / 2 - CharButton.xSize / 2;
        int y = this.height / 2 - CharButton.ySize / 2;

        PlayerRace race = races.get(currentRace);

        this.addButton(new CharButton(this, race, x, y));
        this.addButton(new RaceImageButton(race, x + CharButton.xSize / 2 - RaceImageButton.BUTTON_SIZE_X / 2, y + 40));

        this.addButton(new LeftRightButton(this, x - 30, y + CharButton.ySize / 2, true, LeftRightButton.Type.ONE));
        this.addButton(new LeftRightButton(this, x + 30 - LeftRightButton.xSize + CharButton.xSize, y + CharButton.ySize / 2, false, LeftRightButton.Type.ONE));

        int n = 0;
        for (OptScaleExactStat stat : race.starting_stats) {
            CharacterScreen.StatButton statbutton = new CharacterScreen.StatButton(stat.getStat(), x + 55 + (35 * n), y + 80);
            statbutton.presetValue = (int) stat.v1 + "";
            this.addButton(statbutton);
            n++;
        }

        this.addButton(new ChooseRaceButton(this, x + CharButton.xSize / 2 - ChooseRaceButton.xSize / 2, y + CharButton.ySize - 5 - ChooseRaceButton.ySize + 35));

    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    static class CharButton extends TexturedButtonWidget {

        public static int xSize = 203;
        public static int ySize = 200;

        static Identifier buttonLoc = new Identifier(Ref.MODID, "textures/gui/race_select/background.png");

        RaceSelectScreen screen;
        PlayerRace race;

        public CharButton(RaceSelectScreen screen, PlayerRace race, int xPos, int yPos) {
            super(xPos, yPos, xSize, ySize, 0, 0, 0, buttonLoc, (button) -> {
            });
            this.screen = screen;
            this.race = race;
        }

        @Override
        public void renderButton(MatrixStack matrix, int x, int y, float ticks) {
            super.renderButton(matrix, x, y, ticks);

            String choose = "Choose Your Race";

            screen.mc.textRenderer.drawWithShadow(matrix, choose, this.x + xSize / 2 - screen.mc.textRenderer.getWidth(choose) / 2, this.y - 25, Formatting.WHITE.getColorValue());

            String name = CLOC.translate(race
                .locName());

            GuiUtils.renderScaledText(matrix, this.x + RaceSelectScreen.CharButton.xSize / 2, this.y + 30, 1, name, Formatting.YELLOW);

            List<OrderedText> list = screen.mc.textRenderer.wrapLines(race
                .locDesc(), xSize - 25);

            int xpos = this.x + 15;
            int ypos = this.y + 100;

            for (OrderedText txt : list) {
                screen.mc.textRenderer.drawWithShadow(matrix, txt, xpos, ypos, Formatting.WHITE.getColorValue());

                ypos += screen.mc.textRenderer.fontHeight + 2;
            }

        }
    }

    static class ChooseRaceButton extends TexturedButtonWidget {

        public static int xSize = 112;
        public static int ySize = 20;

        static Identifier buttonLoc = new Identifier(Ref.MODID, "textures/gui/char_select/button.png");
        RaceSelectScreen screen;

        public ChooseRaceButton(RaceSelectScreen screen, int xPos, int yPos) {
            super(xPos, yPos, xSize, ySize, 0, 0, 0, buttonLoc, (button) -> {

            });
            this.screen = screen;
        }

        @Override
        public void onPress() {
            Packets.sendToServer(new ChooseRacePacket(screen.races.get(screen.currentRace)));
            screen.onClose();
        }

        @Override
        public void renderButton(MatrixStack matrix, int x, int y, float ticks) {
            super.renderButton(matrix, x, y, ticks);
            String text = "Select";
            Formatting format = Formatting.GREEN;
            GuiUtils.renderScaledText(matrix, this.x + xSize / 2, this.y + 10, 1, text, format);
        }

    }

}


