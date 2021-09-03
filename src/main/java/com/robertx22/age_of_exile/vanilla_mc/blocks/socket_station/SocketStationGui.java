package com.robertx22.age_of_exile.vanilla_mc.blocks.socket_station;

import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.vanilla_mc.blocks.ModificationGui;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem;
import com.robertx22.age_of_exile.vanilla_mc.packets.ModifyItemPacket;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.registry.FilterListWrap;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SocketStationGui extends ModificationGui<SocketStationContainer, SocketStationBlockEntity> {

    // This is the resource location for the background image
    private static final Identifier texture = new Identifier(Ref.MODID, "textures/gui/runewordcraft.png");

    public SocketStationGui(SocketStationContainer cont, PlayerInventory invPlayer, MutableText comp) {
        super(texture, cont, invPlayer, new LiteralText(""), SocketStationBlockEntity.class);
        backgroundWidth = 276;
        backgroundHeight = 195;
        this.backgroundCanvasSize = 512;
    }

    List<RuneWord> words = new ArrayList<>();

    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        super.render(matrix, mouseX, mouseY, partialTicks);

        if (tile != null) {
            if (mc.player.age % 20 == 0) {

                List<ItemStack> runes = new ArrayList<>();

                for (int slot : SocketStationBlockEntity.STORAGE_SLOTS) {
                    runes.add(tile.itemStacks[slot]);
                }
                for (int slot : SocketStationBlockEntity.RUNE_SLOTS) {
                    runes.add(tile.itemStacks[slot]);
                }
                runes.addAll(mc.player.inventory.main);

                runes.removeIf(x -> x.isEmpty() || x.getItem() instanceof RuneItem == false);

                List<String> runesStrings = runes.stream()
                    .map(x -> ((RuneItem) x.getItem()).type.id)
                    .collect(Collectors.toList());

                FilterListWrap<RuneWord> wrap = ExileDB.RuneWords()
                    .getFilterWrapped(x -> x.runesCanActivateRuneWord(runesStrings, false));

                if (!tile.getGearStack()
                    .isEmpty()) {
                    wrap.of(x -> x.canApplyOnItem(tile.getGearStack()));
                }

                words = wrap.list;

                int count = 0;
                for (RuneWord word : words) {

                    if (count > 10) {
                        break;
                    }

                    addButton(new RuneWordButton(word, true, this.x + 5, this.y + 18 + count * RuneWordButton.Y));

                    count++;

                }

            }
        }
    }

    @Override
    protected void init() {
        super.init();

        /*
        List<Text> list = new ArrayList<>();

        list.add(new LiteralText("Here you can socket gems and runes into items!"));

        this.addButton(new HelpButton(list, this.x + this.backgroundWidth - 25, this.y + 5));

         */

        this.addButton(new CraftButton(tile.getPos(), this.x + 176, this.y + 50));
    }

    private static final Identifier BUTTON_TEX = new Identifier(Ref.MODID, "textures/gui/craft_button.png");
    private static final Identifier RUNEWORD_BUTTON_TEX = new Identifier(Ref.MODID, "textures/gui/runeword_button.png");
    static int BUTTON_SIZE_X = 22;
    static int BUTTON_SIZE_Y = 20;

    public class CraftButton extends TexturedButtonWidget {
        BlockPos pos;

        public CraftButton(BlockPos pos, int xPos, int yPos) {
            super(xPos, yPos, BUTTON_SIZE_X, BUTTON_SIZE_Y, 0, 0, BUTTON_SIZE_Y, BUTTON_TEX, (button) -> {
                Packets.sendToServer(new ModifyItemPacket(pos));
            });
            this.pos = pos;
        }

        @Override
        public void renderToolTip(MatrixStack matrix, int x, int y) {
            if (isInside(x, y)) {

                try {
                    List<Text> tooltip = new ArrayList<>();

                    if (tooltip.isEmpty()) {
                        tooltip.add(new LiteralText("Modify Item"));
                    }

                    GuiUtils.renderTooltip(matrix,
                        tooltip, x, y);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

        public boolean isInside(int x, int y) {
            return GuiUtils.isInRect(this.x, this.y, BUTTON_SIZE_X, BUTTON_SIZE_Y, x, y);
        }

    }

    public static class RuneWordButton extends TexturedButtonWidget {
        static int X = 95;
        static int Y = 18;

        RuneWord word;
        boolean canCreate = false;

        public RuneWordButton(RuneWord word, boolean canCreate, int xPos, int yPos) {
            super(xPos, yPos, X, Y, 0, 0, Y + 1, RUNEWORD_BUTTON_TEX, (button) -> {
            });
            this.word = word;
            this.canCreate = canCreate;
        }

        @Override
        public void renderToolTip(MatrixStack matrix, int x, int y) {
            if (isInside(x, y)) {

                try {
                    List<Text> tooltip = new ArrayList<>();

                    tooltip.add(new LiteralText("RuneWord"));

                    GuiUtils.renderTooltip(matrix,
                        tooltip, x, y);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

        @Override
        public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {

            super.render(matrices, mouseX, mouseY, delta);

            String name = ExileDB.UniqueGears()
                .get(word.uniq_id)
                .translate();

            GuiUtils.renderScaledText(matrices, this.x + X / 2, this.y + Y / 2, 1D, name, Formatting.YELLOW);

        }

        public boolean isInside(int x, int y) {
            return GuiUtils.isInRect(this.x, this.y, X, Y, x, y);
        }

    }

}
