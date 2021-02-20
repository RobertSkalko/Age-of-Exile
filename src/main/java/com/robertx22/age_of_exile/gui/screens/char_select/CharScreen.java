package com.robertx22.age_of_exile.gui.screens.char_select;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.capability.player.PlayerCharCap;
import com.robertx22.age_of_exile.capability.player.data.OnePlayerCharData;
import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.gui.bases.INamedScreen;
import com.robertx22.age_of_exile.gui.screens.skill_tree.SkillTreeScreen;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.vanilla_mc.packets.CharSelectPackets;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.RequestSyncCapToClient;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.GuiUtils;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class CharScreen extends BaseScreen implements INamedScreen {

    public MinecraftClient mc;

    public CharScreen() {
        super(MinecraftClient.getInstance()
            .getWindow()
            .getScaledWidth(), MinecraftClient.getInstance()
            .getWindow()
            .getScaledHeight());
        this.mc = MinecraftClient.getInstance();
        this.data = Load.Unit(mc.player);

        Packets.sendToServer(new RequestSyncCapToClient(PlayerCaps.CHARACTERS));

    }

    public int firstSlot = 0;

    @Override
    protected void init() {
        super.init();

        PlayerCharCap cap = Load.characters(mc.player);

        int x = 0;
        int y = 0;

        int ySpace = this.height / 2;

        y += ySpace / 2;

        int slots = this.width / (CharButton.xSize + 10);

        if (slots > PlayerCharCap.MAX_CHAR_COUNT) {
            slots = PlayerCharCap.MAX_CHAR_COUNT;
        }

        x = (this.width - (CharButton.xSize + 5) * slots) / 2;

        for (int i = firstSlot; i < slots; i++) {
            this.addButton(new CharButton(cap.data.characters.get(i), x, y));

            this.addButton(new Button(this, i, CharSelectPackets.Action.LOAD, cap.data.characters.get(i), x + 6, y + CharButton.ySize - 5 - Button.ySize + 35));

            if (cap.data.characters.containsKey(i)) {
                this.addButton(new Button(this, i, CharSelectPackets.Action.DELETE, cap.data.characters.get(i), x + 6, y + CharButton.ySize - 5 - (2 + Button.ySize * 2) + 80));
            }
            x += 5 + CharButton.xSize;
        }

    }

    @Override
    public void render(MatrixStack matrix, int x, int y, float ticks) {

        SkillTreeScreen.renderBackgroundDirt(this, 0);

        super.render(matrix, x, y, ticks);

        this.buttons.forEach(b -> b.renderToolTip(matrix, x, y));
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
        public static int ySize = 175;

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

