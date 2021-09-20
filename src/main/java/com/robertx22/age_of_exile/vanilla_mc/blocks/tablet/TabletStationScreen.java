package com.robertx22.age_of_exile.vanilla_mc.blocks.tablet;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.robertx22.age_of_exile.gui.buttons.OwnerButton;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.vanilla_mc.blocks.ModificationGui;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.CraftRequirementButton;
import com.robertx22.library_of_exile.gui.HelpButton;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;

public class TabletStationScreen extends ModificationGui<TabletStationContainer, TabletStationTile> {

    // This is the resource location for the background image
    private static final ResourceLocation texture = new ResourceLocation(SlashRef.MODID, "textures/gui/tablet/tablet_station.png");

    public TabletStationScreen(TabletStationContainer cont, PlayerInventory invPlayer, ITextComponent comp) {
        super(texture, cont, invPlayer, new StringTextComponent(""), TabletStationTile.class);
        imageWidth = 176;
        imageHeight = 207;
    }

    @Override
    protected void init() {
        super.init();

        List<ITextComponent> list = new ArrayList<>();

        this.addButton(new HelpButton(list, this.leftPos + this.imageWidth - 25, this.topPos + 5));

        int x = this.leftPos + 58;
        int y = this.topPos + 16;

        addButton(new CraftRequirementButton(tile, minecraft.player, this.leftPos + 123, this.topPos + 59));

        addButton(new OwnerButton(tile, putInTheMiddleX(OwnerButton.SIZE_X) + 30, this.topPos + 100));

    }

    @Override
    public void renderOther(MatrixStack matrix, int mouseX, int mouseY) {
        this.renderCookArrow(matrix, this.leftPos + 87, this.topPos + 38, tile.getCookProgress());
        this.renderCookFlame(matrix, this.leftPos + 45, this.topPos + 82, tile.getFuelProgress());
    }

}