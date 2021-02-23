package com.robertx22.age_of_exile.gui.screens.char_select;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.capability.player.PlayerCharCap;
import com.robertx22.age_of_exile.capability.player.data.OnePlayerCharData;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.gui.bases.INamedScreen;
import com.robertx22.age_of_exile.gui.screens.BaseSelectionScreen;
import com.robertx22.age_of_exile.gui.screens.race_select.RaceImageButton;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.vanilla_mc.packets.CharSelectPackets;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.RequestSyncCapToClient;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.CLOC;
import com.robertx22.library_of_exile.utils.GuiUtils;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class CharSelectScreen extends BaseSelectionScreen implements INamedScreen {

    public CharSelectScreen() {
        super();
        this.mc = MinecraftClient.getInstance();
        this.data = Load.Unit(mc.player);
        Packets.sendToServer(new RequestSyncCapToClient(PlayerCaps.CHARACTERS));
    }

    @Override
    protected void init() {
        super.init();

        PlayerCharCap cap = Load.characters(mc.player);

        int x = 0;
        int y = 0;

        int ySpace = this.height / 50;

        y += ySpace / 2;

        int slots = this.width / (CharButton.xSize + 10);

        if (slots > PlayerCharCap.MAX_CHAR_COUNT) {
            slots = PlayerCharCap.MAX_CHAR_COUNT;

        }

        x = (this.width - (CharButton.xSize + 5) * slots) / 2;

        int num = 0;

        for (int i = 0; i < PlayerCharCap.MAX_CHAR_COUNT; i++) {

            if (num >= slots) {
                num = 0;
                y += 20 + CharButton.ySize + Button.ySize * 2;
                x = (this.width - (CharButton.xSize + 5) * slots) / 2;
            }

            num++;

            OnePlayerCharData data = cap.data.characters.get(i);

            this.addButton(new CharButton(data, x, y));

            if (data != null && Database.Races()
                .isRegistered(data.race)) {
                this.addButton(new RaceImageButton(Database.Races()
                    .get(data.race), x + CharButton.xSize / 2 - RaceImageButton.BUTTON_SIZE_X / 2, y + 40));
            }

            this.addButton(new Button(this, i, CharSelectPackets.Action.LOAD, cap.data.characters.get(i), x + 6, y + CharButton.ySize - 5 - Button.ySize + 35));

            if (cap.data.characters.containsKey(i)) {
                this.addButton(new Button(this, i, CharSelectPackets.Action.DELETE, cap.data.characters.get(i), x + 6, y + CharButton.ySize - 5 - (2 + Button.ySize * 2) + 80));
            }
            x += 5 + CharButton.xSize;
        }

    }

    EntityCap.UnitData data;

    @Override
    public Identifier iconLocation() {
        return new Identifier(Ref.MODID, "textures/gui/main_hub/icons/char_select.png");
    }

    @Override
    public Words screenName() {
        return Words.CharacterSelect;
    }

    static class CharButton extends TexturedButtonWidget {

        public static int xSize = 125;
        public static int ySize = 139;

        static Identifier buttonLoc = new Identifier(Ref.MODID, "textures/gui/char_select/one_char.png");
        boolean noChar = false;
        OnePlayerCharData data;

        public CharButton(@Nullable OnePlayerCharData data, int xPos, int yPos) {
            super(xPos, yPos, xSize, ySize, 0, 0, 0, buttonLoc, (button) -> {
            });
            this.data = data;

            if (data == null) {
                this.noChar = true;
            }

        }

        @Override
        public void renderButton(MatrixStack matrix, int x, int y, float ticks) {
            super.renderButton(matrix, x, y, ticks);

            if (noChar) {

                GuiUtils.renderScaledText(matrix, this.x + CharButton.xSize / 2, this.y + 20, 1, "Empty Character Slot", Formatting.YELLOW);

            } else {

                GuiUtils.renderScaledText(matrix, this.x + CharButton.xSize / 2, this.y + 20, 1, "Level " + data.lvl, Formatting.YELLOW);

                String race = "Race not selected";

                if (!data.race.isEmpty()) {
                    race = CLOC.translate(Database.Races()
                        .get(data.race)
                        .locName());
                }
                GuiUtils.renderScaledText(matrix, this.x + CharButton.xSize / 2, this.y + 35, 1, race, Formatting.RED);

            }

        }
    }

    static class Button extends TexturedButtonWidget {

        public static int xSize = 112;
        public static int ySize = 20;

        static Identifier buttonLoc = new Identifier(Ref.MODID, "textures/gui/char_select/button.png");
        boolean noChar = false;
        OnePlayerCharData data;
        int num;
        CharSelectPackets.Action action;

        public Button(Screen screen, int charnum, CharSelectPackets.Action action, @Nullable OnePlayerCharData data, int xPos, int yPos) {
            super(xPos, yPos, xSize, ySize, 0, 0, 0, buttonLoc, (button) -> {
                Packets.sendToServer(new CharSelectPackets(charnum, action));
                screen.onClose();

            });
            this.data = data;
            this.num = charnum;
            this.action = action;

            if (data == null) {
                this.noChar = true;
            }

        }

        @Override
        public void renderButton(MatrixStack matrix, int x, int y, float ticks) {
            super.renderButton(matrix, x, y, ticks);

            String text = "";
            Formatting format = Formatting.GREEN;

            if (noChar) {
                text = "Create Character";

            } else {
                if (this.action == CharSelectPackets.Action.DELETE) {
                    text = "Delete Character";
                    format = Formatting.RED;
                } else {
                    text = "Load Character";

                }

            }
            GuiUtils.renderScaledText(matrix, this.x + xSize / 2, this.y + 10, 1, text, format);
        }

    }

}

