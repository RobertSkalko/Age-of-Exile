package com.robertx22.age_of_exile.vanilla_mc.blocks.tablet;

import com.robertx22.age_of_exile.gui.buttons.OwnerButton;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.vanilla_mc.blocks.ModificationGui;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.CraftRequirementButton;
import com.robertx22.library_of_exile.gui.HelpButton;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class TabletStationScreen extends ModificationGui<TabletStationContainer, TabletStationTile> {

    // This is the resource location for the background image
    private static final Identifier texture = new Identifier(Ref.MODID, "textures/gui/tablet/tablet_station.png");

    public TabletStationScreen(TabletStationContainer cont, PlayerInventory invPlayer, MutableText comp) {
        super(texture, cont, invPlayer, new LiteralText(""), TabletStationTile.class);
        backgroundWidth = 176;
        backgroundHeight = 207;
    }

    @Override
    protected void init() {
        super.init();

        List<Text> list = new ArrayList<>();

        this.addButton(new HelpButton(list, this.x + this.backgroundWidth - 25, this.y + 5));

        int x = this.x + 58;
        int y = this.y + 16;

        addButton(new CraftRequirementButton(tile, client.player, this.x + 123, this.y + 59));

        addButton(new OwnerButton(tile, putInTheMiddleX(OwnerButton.SIZE_X) + 30, this.y + 100));

    }

    @Override
    public void renderOther(MatrixStack matrix, int mouseX, int mouseY) {
        this.renderCookArrow(matrix, this.x + 87, this.y + 38, tile.getCookProgress());
        this.renderCookFlame(matrix, this.x + 45, this.y + 82, tile.getFuelProgress());
    }

}