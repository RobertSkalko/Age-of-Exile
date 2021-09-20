package com.robertx22.age_of_exile.vanilla_mc.blocks.runeword_station;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.vanilla_mc.blocks.ModificationGui;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem;
import com.robertx22.age_of_exile.vanilla_mc.packets.ModifyItemPacket;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.registry.FilterListWrap;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class RuneWordStationGui extends ModificationGui<RuneWordStationContainer, RuneWordStationTile> {

    // This is the resource location for the background image
    private static final ResourceLocation texture = new ResourceLocation(SlashRef.MODID, "textures/gui/runewordcraft.png");

    public RuneWordStationGui(RuneWordStationContainer cont, PlayerInventory invPlayer, IFormattableTextComponent comp) {
        super(texture, cont, invPlayer, new StringTextComponent(""), RuneWordStationTile.class);
        imageWidth = 276;
        imageHeight = 195;
        this.backgroundCanvasSize = 512;
    }

    List<RuneWord> words = new ArrayList<>();

    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        super.render(matrix, mouseX, mouseY, partialTicks);

        if (tile != null) {
            if (mc.player.tickCount % 20 == 0) {

                if (tile.getGearStack()
                    .isEmpty()) {
                    this.words = ExileDB.RuneWords()
                        .getList();
                } else {

                    List<ItemStack> runes = new ArrayList<>();

                    for (int slot : RuneWordStationTile.STORAGE_SLOTS) {
                        runes.add(tile.itemStacks[slot]);
                    }
                    for (int slot : RuneWordStationTile.RUNE_SLOTS) {
                        runes.add(tile.itemStacks[slot]);
                    }
                    runes.addAll(mc.player.inventory.items);

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
                }
                int count = 0;
                for (RuneWord word : words) {

                    if (count > 10) {
                        break;
                    }

                    addButton(new RuneWordButton(word, true, this.leftPos + 5, this.topPos + 18 + count * RuneWordButton.Y));

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

        this.addButton(new CraftButton(tile.getBlockPos(), this.leftPos + 176, this.topPos + 50));
    }

    private static final ResourceLocation BUTTON_TEX = new ResourceLocation(SlashRef.MODID, "textures/gui/craft_button.png");
    private static final ResourceLocation RUNEWORD_BUTTON_TEX = new ResourceLocation(SlashRef.MODID, "textures/gui/runeword_button.png");
    static int BUTTON_SIZE_X = 22;
    static int BUTTON_SIZE_Y = 20;

    public class CraftButton extends ImageButton {
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
                    List<ITextComponent> tooltip = new ArrayList<>();

                    if (tooltip.isEmpty()) {
                        tooltip.add(new StringTextComponent("Modify Item"));
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

    public static class RuneWordButton extends ImageButton {
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
                    List<ITextComponent> tooltip = new ArrayList<>();

                    UniqueGear uniq = ExileDB.UniqueGears()
                        .get(word.uniq_id);

                    tooltip.add(uniq.locName()
                        .withStyle(uniq.getUniqueRarity()
                            .textFormatting()));

                    String runes = "";

                    for (String rune : word.runes) {
                        if (!runes.isEmpty()) {
                            runes += " - ";
                        }
                        runes += rune.toUpperCase(Locale.ROOT);
                    }

                    tooltip.add(new StringTextComponent(runes).withStyle(TextFormatting.GOLD));

                    String usedOn = "";
                    for (String slot : word.slots) {
                        usedOn += ExileDB.GearSlots()
                            .get(slot)
                            .translate();
                    }
                    tooltip.add(new StringTextComponent(TextFormatting.GREEN + "Can be used on: " + usedOn));

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

            GuiUtils.renderScaledText(matrices, this.x + X / 2, this.y + Y / 2, 1D, name, TextFormatting.YELLOW);

        }

        public boolean isInside(int x, int y) {
            return GuiUtils.isInRect(this.x, this.y, X, Y, x, y);
        }

    }

}
