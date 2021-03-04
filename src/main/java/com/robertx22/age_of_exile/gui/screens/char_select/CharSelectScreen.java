package com.robertx22.age_of_exile.gui.screens.char_select;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.capability.player.PlayerCharCap;
import com.robertx22.age_of_exile.capability.player.data.OnePlayerCharData;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.gui.bases.INamedScreen;
import com.robertx22.age_of_exile.gui.screens.BaseSelectionScreen;
import com.robertx22.age_of_exile.gui.screens.ILeftRight;
import com.robertx22.age_of_exile.gui.screens.PlayerGearButton;
import com.robertx22.age_of_exile.gui.screens.race_select.RaceSelectScreen;
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
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stat.StatHandler;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class CharSelectScreen extends BaseSelectionScreen implements INamedScreen, ILeftRight {

    //this.client.player = this.client.interactionManager.createPlayer(this.world, new StatHandler(), new ClientRecipeBook());
    //

    public CharSelectScreen() {
        super();
        this.mc = MinecraftClient.getInstance();
        this.data = Load.Unit(mc.player);
        Packets.sendToServer(new RequestSyncCapToClient(PlayerCaps.CHARACTERS));
    }

    int index = 0;

    @Override
    public void goLeft() {
        if (index <= 0) {
            index = ModConfig.get().Server.MAX_CHARACTERS;
        } else {
            index--;
        }

        this.init();
    }

    @Override
    public void goRight() {
        if (index >= ModConfig.get().Server.MAX_CHARACTERS - 1 - 3) {
            index = 0;
        } else {
            index++;
        }
        this.init();
    }

    @Override
    protected void init() {
        super.init();

        this.buttons.clear();

        PlayerCharCap cap = Load.characters(mc.player);

        int x = 0;
        int y = this.height / 2 - CharButton.ySize / 2;

        int slots = this.width / (CharButton.xSize + 50);

        if (slots > 3) {
            slots = 3;
        }

        x = (this.width - (CharButton.xSize + 5) * slots) / 2;

        this.addButton(new RaceSelectScreen.LeftRightButton(this, x - 30, y + CharButton.ySize / 2, true));

        for (int i = index; i < index + slots; i++) {

            OnePlayerCharData data = cap.data.characters.get(i);

            this.addButton(new CharButton(data, x, y));

            if (data != null) {

                // create new player so it can be rendered
                PlayerEntity player = client.interactionManager.createPlayer(client.world, new StatHandler(), new ClientRecipeBook());
                data.load(player);

                addButton(new PlayerGearButton(player, this, x + CharButton.xSize / 2 - PlayerGearButton.xSize / 2, y + 30));
            }  // create new player so it can be rendered

            this.addButton(new Button(this, i, CharSelectPackets.Action.LOAD, cap.data.characters.get(i), x + CharButton.xSize / 2 - Button.xSize / 2, y + CharButton.ySize - 5 - Button.ySize + 35));

            if (cap.data.characters.containsKey(i)) {
                this.addButton(new Button(this, i, CharSelectPackets.Action.DELETE, cap.data.characters.get(i), x + CharButton.xSize / 2 - Button.xSize / 2, y + CharButton.ySize - 5 - (2 + Button.ySize * 2) + 80));
            }
            x += 5 + CharButton.xSize;

        }

        this.addButton(new RaceSelectScreen.LeftRightButton(this, x + 25 - RaceSelectScreen.LeftRightButton.xSize, y + CharButton.ySize / 2, false));

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

        public static int xSize = 150;
        public static int ySize = 200;

        static Identifier buttonLoc = new Identifier(Ref.MODID, "textures/gui/char_select/background.png");
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

                GuiUtils.renderScaledText(matrix, this.x + CharButton.xSize / 2, this.y + 30, 1, "Empty Character Slot", Formatting.YELLOW);

            } else {

                //GuiUtils.renderScaledText(matrix, this.x + CharButton.xSize / 2, this.y + 20, 1, "Level " + data.lvl, Formatting.YELLOW);

                String race = "Race not selected";

                if (!data.race.isEmpty()) {
                    race = CLOC.translate(Database.Races()
                        .get(data.race)
                        .locName());
                }
                GuiUtils.renderScaledText(matrix, this.x + CharButton.xSize / 2, this.y + 10, 1, race, Formatting.RED);

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

