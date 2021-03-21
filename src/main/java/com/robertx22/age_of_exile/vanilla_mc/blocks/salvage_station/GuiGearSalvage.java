package com.robertx22.age_of_exile.vanilla_mc.blocks.salvage_station;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.vanilla_mc.blocks.ModificationGui;
import com.robertx22.age_of_exile.vanilla_mc.packets.ModifyItemPacket;
import com.robertx22.library_of_exile.gui.HelpButton;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class GuiGearSalvage extends ModificationGui<ContainerGearSalvage, TileGearSalvage> {

    // This is the resource location for the background image
    private static final Identifier texture = new Identifier(Ref.MODID, "textures/gui/salvage_station.png");

    public GuiGearSalvage(ContainerGearSalvage cont, PlayerInventory invPlayer,
                          MutableText comp) {
        super(texture, cont, invPlayer, new LiteralText("Salvage"), TileGearSalvage.class);

        backgroundWidth = 176;
        backgroundHeight = 207;
    }

    @Override
    protected void init() {
        super.init();

        List<Text> list = new ArrayList<>();

        list.add(new LiteralText("Here you can salvage gears with Age of Exile stats."));
        list.add(new LiteralText("Gears are turned into scraps, which is used to fuel gear."));

        list.add(new LiteralText("There are some secret recipes."));

        this.addButton(new HelpButton(list, this.x + this.backgroundWidth, this.y));
        this.addButton(new CraftButton(tile.getPos(), this.x + this.backgroundWidth / 2 - BUTTON_SIZE_X / 2, this.y + 35));

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

                    if (tooltip.isEmpty()) {
                        tooltip.add(new LiteralText("Salvage Item"));
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