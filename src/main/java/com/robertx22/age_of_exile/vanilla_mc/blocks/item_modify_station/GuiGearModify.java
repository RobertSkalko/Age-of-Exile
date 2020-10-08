package com.robertx22.age_of_exile.vanilla_mc.blocks.item_modify_station;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.vanilla_mc.packets.ModifyItemPacket;
import com.robertx22.library_of_exile.gui.HelpButton;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.tile_bases.TileGui;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class GuiGearModify extends TileGui<ContainerGearModify, TileGearModify> {

    // This is the resource location for the background image
    private static final Identifier texture = new Identifier(Ref.MODID, "textures/gui/modify_station.png");

    public GuiGearModify(ContainerGearModify cont, PlayerInventory invPlayer, MutableText comp) {
        super(cont, invPlayer, new LiteralText(""), TileGearModify.class);

        backgroundWidth = 176;
        backgroundHeight = 207;

    }

    @Override
    protected void init() {
        super.init();

        List<Text> list = new ArrayList<>();

        list.add(new LiteralText("Here you can modify items with currencies."));
        list.add(new LiteralText(""));
        list.add(new LiteralText("Put the gear on one side, and the currency-"));
        list.add(new LiteralText("on the other."));

        this.addButton(new HelpButton(list, this.x + this.backgroundWidth - 25, this.y + 5));

        this.addButton(new CraftButton(tile.getPos(), this.x + 58, this.y + 85));
    }

    @Override
    protected void drawBackground(MatrixStack matrix, float partialTicks, int x, int y) {

        // Bind the image texture
        MinecraftClient.getInstance()
            .getTextureManager()
            .bindTexture(texture);
        // Draw the image
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        drawTexture(matrix, this.x, this.y, 0, 0, backgroundWidth, backgroundHeight);

        this.buttons.forEach(b -> b.renderToolTip(matrix, x, y));

    }

    @Override
    protected void drawForeground(MatrixStack matrix, int mouseX, int mouseY) {
        super.drawForeground(matrix, mouseX, mouseY);

    }

    private static final Identifier BUTTON_TEX = new Identifier(Ref.MODID, "textures/gui/craftbutton.png");
    static int BUTTON_SIZE_X = 61;
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

                    LocReqContext context = tile.getLocReqContext();
                    if (context.effect != null && context.hasStack()) {
                        boolean add = true;

                        if (context.isGear()) {
                            if (context.effect.getInstability() > 0) {

                                GearItemData gear = (GearItemData) context.data;
                                if (gear.sealed) {
                                    tooltip.add(new LiteralText("Sealed items can't be modified until they are unsealed.").formatted(Formatting.RED));
                                }
                            }
                        }

                        for (BaseLocRequirement req : context.effect.requirements()) {
                            if (req.isNotAllowed(context)) {

                                if (add) {
                                    tooltip.add(Words.RequirementsNotMet.locName()
                                        .formatted(Formatting.RED));
                                    add = false;
                                }
                                tooltip.add(req.getText()
                                    .formatted(Formatting.GOLD));

                            }
                        }
                    }

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

}