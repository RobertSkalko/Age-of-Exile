package com.robertx22.age_of_exile.gui.screens.race_select;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.races.PlayerRace;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.gui.screens.BaseSelectionScreen;
import com.robertx22.age_of_exile.gui.screens.character_screen.CharacterScreen;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.vanilla_mc.packets.ChooseRacePacket;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.CLOC;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.List;

public class RaceSelectScreen extends BaseSelectionScreen {

    @Override
    protected void init() {
        super.init();

        int x = 0;
        int y = this.height / 50;

        int slots = this.width / (RaceSelectScreen.CharButton.xSize + 10);

        if (slots > Database.Races()
            .getSize()) {
            slots = Database.Races()
                .getSize();
        }

        List<PlayerRace> races = Database.Races()
            .getList();

        int num = 0;

        x = (this.width - (RaceSelectScreen.CharButton.xSize + 5) * slots) / 2;

        for (int i = 0; i < races.size(); i++) {

            if (num >= slots) {
                num = 0;
                y += 15 + CharButton.ySize + ChooseRaceButton.ySize;
                x = (this.width - (RaceSelectScreen.CharButton.xSize + 5) * slots) / 2;

            }

            num++;
            PlayerRace race = races.get(i);

            this.addButton(new RaceSelectScreen.CharButton(race, x, y));
            this.addButton(new RaceImageButton(race, x + CharButton.xSize / 2 - RaceImageButton.BUTTON_SIZE_X / 2, y + 40));

            int n = 0;
            for (OptScaleExactStat stat : race.starting_stats) {
                CharacterScreen.StatButton statbutton = new CharacterScreen.StatButton(stat.getStat(), x + 15 + (35 * n), y + 80);
                statbutton.presetValue = (int) stat.v1 + "";
                this.addButton(statbutton);
                n++;
            }

            this.addButton(new ChooseRaceButton(this, race, x + 6, y + RaceSelectScreen.CharButton.ySize - 5 - ChooseRaceButton.ySize + 35));

            x += 5 + RaceSelectScreen.CharButton.xSize;
        }

    }

    static class CharButton extends TexturedButtonWidget {

        public static int xSize = 125;
        public static int ySize = 113;

        static Identifier buttonLoc = new Identifier(Ref.MODID, "textures/gui/char_select/one_race.png");

        PlayerRace race;

        public CharButton(PlayerRace race, int xPos, int yPos) {
            super(xPos, yPos, xSize, ySize, 0, 0, 0, buttonLoc, (button) -> {
            });
            this.race = race;

        }

        @Override
        public void renderButton(MatrixStack matrix, int x, int y, float ticks) {
            super.renderButton(matrix, x, y, ticks);

            String name = CLOC.translate(race.locName());

            GuiUtils.renderScaledText(matrix, this.x + RaceSelectScreen.CharButton.xSize / 2, this.y + 20, 1, name, Formatting.YELLOW);

        }
    }

    static class ChooseRaceButton extends TexturedButtonWidget {

        public static int xSize = 112;
        public static int ySize = 20;

        static Identifier buttonLoc = new Identifier(Ref.MODID, "textures/gui/char_select/button.png");
        PlayerRace race;

        public ChooseRaceButton(Screen screen, PlayerRace race, int xPos, int yPos) {
            super(xPos, yPos, xSize, ySize, 0, 0, 0, buttonLoc, (button) -> {
                Packets.sendToServer(new ChooseRacePacket(race));
                screen.onClose();
            });
            this.race = race;

        }

        @Override
        public void renderButton(MatrixStack matrix, int x, int y, float ticks) {
            super.renderButton(matrix, x, y, ticks);
            String text = "Choose Race";
            Formatting format = Formatting.GREEN;
            GuiUtils.renderScaledText(matrix, this.x + xSize / 2, this.y + 10, 1, text, format);
        }

    }

}


