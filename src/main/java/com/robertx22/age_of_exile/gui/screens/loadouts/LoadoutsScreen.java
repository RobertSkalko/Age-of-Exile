package com.robertx22.age_of_exile.gui.screens.loadouts;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.capability.player.PlayerLoadoutsCap;
import com.robertx22.age_of_exile.capability.player.data.OneLoadoutData;
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
import com.robertx22.age_of_exile.vanilla_mc.packets.LoadoutSelectPackets;
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

public class LoadoutsScreen extends BaseSelectionScreen implements INamedScreen, ILeftRight {

    public LoadoutsScreen() {
        super();
        this.mc = MinecraftClient.getInstance();
        this.data = Load.Unit(mc.player);
        Packets.sendToServer(new RequestSyncCapToClient(PlayerCaps.LOADOUTS));
    }

    int index = 0;

    @Override
    public void goLeft() {
        if (index <= 0) {
            index = ModConfig.get().Server.MAX_LOADOUTS;
        } else {
            index--;
        }

        this.init();
    }

    @Override
    public void goRight() {
        if (index >= ModConfig.get().Server.MAX_LOADOUTS - 1 - 3) {
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

        PlayerLoadoutsCap cap = Load.loadouts(mc.player);

        int x = 0;
        int y = this.height / 2 - LoadoutButton.ySize / 2;

        int slots = this.width / (LoadoutButton.xSize + 50);

        if (slots > 3) {
            slots = 3;
        }

        if (slots > ModConfig.get().Server.MAX_LOADOUTS) {
            slots = ModConfig.get().Server.MAX_LOADOUTS;
        }

        x = (this.width - (LoadoutButton.xSize + 5) * slots) / 2;

        this.addButton(new RaceSelectScreen.LeftRightButton(this, x - 30, y + LoadoutButton.ySize / 2, true));

        for (int i = index; i < index + slots; i++) {

            OneLoadoutData data = cap.data.loadouts.get(i);

            this.addButton(new LoadoutButton(data, x, y));

            if (data != null) {

                // create new player so it can be rendered
                PlayerEntity player = client.interactionManager.createPlayer(client.world, new StatHandler(), new ClientRecipeBook());
                data.load(player);

                addButton(new PlayerGearButton(player, this, x + LoadoutButton.xSize / 2 - PlayerGearButton.xSize / 2, y + 30));
            }  // create new player so it can be rendered

            this.addButton(new Button(this, i, LoadoutSelectPackets.Action.LOAD, cap.data.loadouts.get(i), x + LoadoutButton.xSize / 2 - Button.xSize / 2, y + LoadoutButton.ySize - 5 - Button.ySize + 35));

            x += 5 + LoadoutButton.xSize;

        }

        this.addButton(new RaceSelectScreen.LeftRightButton(this, x + 25 - RaceSelectScreen.LeftRightButton.xSize, y + LoadoutButton.ySize / 2, false));

    }

    EntityCap.UnitData data;

    @Override
    public Identifier iconLocation() {
        return new Identifier(Ref.MODID, "textures/gui/main_hub/icons/char_select.png");
    }

    @Override
    public Words screenName() {
        return Words.Loadouts;
    }

    static class LoadoutButton extends TexturedButtonWidget {

        public static int xSize = 150;
        public static int ySize = 200;

        static Identifier buttonLoc = new Identifier(Ref.MODID, "textures/gui/char_select/background.png");
        boolean noLoadout = false;
        OneLoadoutData data;

        public LoadoutButton(@Nullable OneLoadoutData data, int xPos, int yPos) {
            super(xPos, yPos, xSize, ySize, 0, 0, 0, buttonLoc, (button) -> {
            });
            this.data = data;

            if (data == null) {
                this.noLoadout = true;
            }

        }

        @Override
        public void renderButton(MatrixStack matrix, int x, int y, float ticks) {
            super.renderButton(matrix, x, y, ticks);

            if (noLoadout) {

                GuiUtils.renderScaledText(matrix, this.x + LoadoutButton.xSize / 2, this.y + 30, 1, "Empty Loadout Slot", Formatting.YELLOW);

            } else {

                //GuiUtils.renderScaledText(matrix, this.x + CharButton.xSize / 2, this.y + 20, 1, "Level " + data.lvl, Formatting.YELLOW);

                String race = "Race Not Selected";

                if (!data.race.isEmpty()) {
                    race = CLOC.translate(Database.Races()
                        .get(data.race)
                        .locName());
                }
                GuiUtils.renderScaledText(matrix, this.x + LoadoutButton.xSize / 2, this.y + 10, 1, race, Formatting.RED);

            }

        }
    }

    static class Button extends TexturedButtonWidget {

        public static int xSize = 112;
        public static int ySize = 20;

        static Identifier buttonLoc = new Identifier(Ref.MODID, "textures/gui/char_select/button.png");
        boolean noLoadout = false;
        OneLoadoutData data;
        int num;
        LoadoutSelectPackets.Action action;

        public Button(Screen screen, int loadoutNum, LoadoutSelectPackets.Action action, @Nullable OneLoadoutData data, int xPos, int yPos) {
            super(xPos, yPos, xSize, ySize, 0, 0, 0, buttonLoc, (button) -> {
                Packets.sendToServer(new LoadoutSelectPackets(loadoutNum, action));
                screen.onClose();

            });
            this.data = data;
            this.num = loadoutNum;
            this.action = action;

            if (data == null) {
                this.noLoadout = true;
            }

        }

        @Override
        public void renderButton(MatrixStack matrix, int x, int y, float ticks) {
            super.renderButton(matrix, x, y, ticks);

            String text = "";
            Formatting format = Formatting.GREEN;

            if (noLoadout) {
                text = "Create Loadout";

            } else {
                text = "Load Loadout";

            }
            GuiUtils.renderScaledText(matrix, this.x + xSize / 2, this.y + 10, 1, text, format);
        }

    }

}

