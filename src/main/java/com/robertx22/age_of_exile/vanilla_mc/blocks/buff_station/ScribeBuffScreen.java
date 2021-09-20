package com.robertx22.age_of_exile.vanilla_mc.blocks.buff_station;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.robertx22.age_of_exile.database.data.scroll_buff.ScrollBuff;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.player_skills.items.SkillRequirement;
import com.robertx22.age_of_exile.player_skills.items.inscribing.EssenceInkItem;
import com.robertx22.age_of_exile.vanilla_mc.blocks.ModificationGui;
import com.robertx22.age_of_exile.vanilla_mc.packets.ModifyItemPacket;
import com.robertx22.library_of_exile.gui.HelpButton;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.CLOC;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.Minecraft;
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

public class ScribeBuffScreen extends ModificationGui<ScribeBuffContainer, ScribeBuffTile> {

    // This is the resource location for the background image
    private static final ResourceLocation texture = new ResourceLocation(SlashRef.MODID, "textures/gui/inscribing/buff_station.png");

    public ScribeBuffScreen(ScribeBuffContainer cont, PlayerInventory invPlayer, IFormattableTextComponent comp) {
        super(texture, cont, invPlayer, new StringTextComponent(""), ScribeBuffTile.class);
        imageWidth = 176;
        imageHeight = 207;
    }

    @Override
    protected void init() {
        super.init();

        List<ITextComponent> list = new ArrayList<>();

        this.addButton(new HelpButton(list, this.leftPos + 5, this.topPos + 5));

        List<ScrollBuff> buffs = ScribeBuffTile.getCurrentSelection(minecraft.player);

        int x = this.leftPos + 58;
        int y = this.topPos + 16;

        for (int i = 0; i < buffs.size(); i++) {

            addButton(new OutcomeButton(i, tile.getBlockPos(), x, y));
            y += BUTTON_SIZE_Y;
        }

    }

    private static final ResourceLocation BUTTON_TEX = new ResourceLocation(SlashRef.MODID, "textures/gui/inscribing/button.png");
    static int BUTTON_SIZE_X = 109;
    static int BUTTON_SIZE_Y = 19;

    public class OutcomeButton extends ImageButton {
        BlockPos pos;
        int num;

        public OutcomeButton(int number, BlockPos pos, int xPos, int yPos) {
            super(xPos, yPos, BUTTON_SIZE_X, BUTTON_SIZE_Y, 0, 0, BUTTON_SIZE_Y, BUTTON_TEX, (button) -> {
                Packets.sendToServer(new ModifyItemPacket(pos, number));
            });
            this.pos = pos;
            this.num = number;

        }

        @Override
        public void renderToolTip(MatrixStack matrix, int x, int y) {
            if (isInside(x, y)) {

                try {
                    List<ITextComponent> tooltip = new ArrayList<>();
                    ItemStack stack = tile.itemStacks[ScribeBuffTile.MAT_SLOT];
                    if (ScribeBuffTile.isValidInk(stack)) {
                        EssenceInkItem ink = (EssenceInkItem) stack.getItem();
                        SkillRequirement req = ink.getSkillRequirement();

                        if (req.meetsRequirement(minecraft.player)) {
                            tooltip.add(new StringTextComponent("Choose?"));
                        } else {
                            tooltip.add(req.getUnmetReqText());
                        }
                    }

                    GuiUtils.renderTooltip(matrix,
                        tooltip, x, y);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }

        @Override
        public void renderButton(MatrixStack matrix, int x, int y, float ticks) {
            super.renderButton(matrix, x, y, ticks);

            ItemStack stack = tile.itemStacks[ScribeBuffTile.MAT_SLOT];
            if (ScribeBuffTile.isValidInk(stack)) {

                Minecraft mc = Minecraft.getInstance();

                ScrollBuff buff = ScribeBuffTile.getCurrentSelection(minecraft.player)
                    .get(this.num);

                String str = CLOC.translate(buff.locDesc()) + "...";

                mc.font.drawShadow(matrix,
                    str, this.x + 22, this.y + 3, TextFormatting.YELLOW
                        .getColor());
            }
        }

        public boolean isInside(int x, int y) {
            return GuiUtils.isInRect(this.x, this.y, BUTTON_SIZE_X, BUTTON_SIZE_Y, x, y);
        }

    }
}