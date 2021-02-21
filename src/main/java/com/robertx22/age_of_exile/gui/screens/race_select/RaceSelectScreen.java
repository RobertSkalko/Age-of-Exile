package com.robertx22.age_of_exile.gui.screens.race_select;

import com.robertx22.age_of_exile.database.data.races.PlayerRace;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.gui.screens.skill_tree.SkillTreeScreen;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.vanilla_mc.packets.ChooseRacePacket;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.CLOC;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.HashMap;
import java.util.List;

public class RaceSelectScreen extends BaseScreen {

    public MinecraftClient mc;

    public RaceSelectScreen() {
        super(MinecraftClient.getInstance()
            .getWindow()
            .getScaledWidth(), MinecraftClient.getInstance()
            .getWindow()
            .getScaledHeight());
        this.mc = MinecraftClient.getInstance();

    }

    int scrollY = 0;

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {

        this.scrollY += deltaY;

        scrollY = MathHelper.clamp(scrollY, -3333, 3333);

        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    public void goToCenter() {

        this.scrollY = 0;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 32) { // space
            this.goToCenter();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);

    }

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

            this.addButton(new ChooseRaceButton(this, race, x + 6, y + RaceSelectScreen.CharButton.ySize - 5 - ChooseRaceButton.ySize + 35));

            x += 5 + RaceSelectScreen.CharButton.xSize;
        }

    }

    HashMap<AbstractButtonWidget, PointData> originalButtonLocMap = new HashMap<>();

    @Override
    protected <T extends AbstractButtonWidget> T addButton(T b) {
        super.addButton(b);
        originalButtonLocMap.put(b, new PointData(b.x, b.y));
        return b;
    }

    @Override
    public void render(MatrixStack matrix, int x, int y, float ticks) {

        this.buttons.forEach(b -> {
            if (originalButtonLocMap.containsKey(b)) {
                b.y = (this.originalButtonLocMap.get(b)
                    .y + scrollY);
            }
        });

        SkillTreeScreen.renderBackgroundDirt(this, 0);

        super.render(matrix, x, y, ticks);

        this.buttons.forEach(b -> b.renderToolTip(matrix, x, y));
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


