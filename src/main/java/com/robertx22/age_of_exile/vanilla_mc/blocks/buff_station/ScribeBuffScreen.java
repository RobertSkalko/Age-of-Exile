package com.robertx22.age_of_exile.vanilla_mc.blocks.buff_station;

import com.robertx22.age_of_exile.database.data.scroll_buff.ScrollBuff;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.player_skills.items.SkillRequirement;
import com.robertx22.age_of_exile.player_skills.items.inscribing.EssenceInkItem;
import com.robertx22.age_of_exile.vanilla_mc.blocks.ModificationGui;
import com.robertx22.age_of_exile.vanilla_mc.packets.ModifyItemPacket;
import com.robertx22.library_of_exile.gui.HelpButton;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.CLOC;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.MinecraftClient;
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

public class ScribeBuffScreen extends ModificationGui<ScribeBuffContainer, ScribeBuffTile> {

    // This is the resource location for the background image
    private static final Identifier texture = new Identifier(Ref.MODID, "textures/gui/inscribing/buff_station.png");

    public ScribeBuffScreen(ScribeBuffContainer cont, PlayerInventory invPlayer, MutableText comp) {
        super(texture, cont, invPlayer, new LiteralText(""), ScribeBuffTile.class);
        backgroundWidth = 176;
        backgroundHeight = 207;
    }

    @Override
    protected void init() {
        super.init();

        List<Text> list = new ArrayList<>();

        this.addButton(new HelpButton(list, this.x + 5, this.y + 5));

        List<ScrollBuff> buffs = ScribeBuffTile.getCurrentSelection(client.player);

        int x = this.x + 58;
        int y = this.y + 16;

        for (int i = 0; i < buffs.size(); i++) {

            addButton(new OutcomeButton(i, tile.getPos(), x, y));
            y += BUTTON_SIZE_Y;
        }

    }

    private static final Identifier BUTTON_TEX = new Identifier(Ref.MODID, "textures/gui/inscribing/button.png");
    static int BUTTON_SIZE_X = 109;
    static int BUTTON_SIZE_Y = 19;

    public class OutcomeButton extends TexturedButtonWidget {
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
                    List<Text> tooltip = new ArrayList<>();
                    ItemStack stack = tile.itemStacks[ScribeBuffTile.MAT_SLOT];
                    if (ScribeBuffTile.isValidInk(stack)) {
                        EssenceInkItem ink = (EssenceInkItem) stack.getItem();
                        SkillRequirement req = ink.getSkillRequirement();

                        if (req.meetsRequirement(client.player)) {
                            tooltip.add(new LiteralText("Choose?"));
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

                MinecraftClient mc = MinecraftClient.getInstance();

                ScrollBuff buff = ScribeBuffTile.getCurrentSelection(client.player)
                    .get(this.num);

                String str = CLOC.translate(buff.locDesc()) + "...";

                mc.textRenderer.drawWithShadow(matrix,
                    str, this.x + 22, this.y + 3, Formatting.YELLOW
                        .getColorValue());
            }
        }

        public boolean isInside(int x, int y) {
            return GuiUtils.isInRect(this.x, this.y, BUTTON_SIZE_X, BUTTON_SIZE_Y, x, y);
        }

    }
}