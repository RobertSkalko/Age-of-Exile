package com.robertx22.age_of_exile.vanilla_mc.blocks.cooking_station;

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

public class CookingScreen extends ModificationGui<CookingContainer, CookingTile> {

    // This is the resource location for the background image
    private static final ResourceLocation texture = new ResourceLocation(SlashRef.MODID, "textures/gui/cooking/cooking_station.png");

    public CookingScreen(CookingContainer cont, PlayerInventory invPlayer, ITextComponent comp) {
        super(texture, cont, invPlayer, new StringTextComponent(""), CookingTile.class);
        imageWidth = 176;
        imageHeight = 207;
    }

    @Override
    protected void init() {
        super.init();

        List<ITextComponent> list = new ArrayList<>();

        this.addButton(new HelpButton(list, this.leftPos + 5, this.topPos + 5));

        addButton(new CraftRequirementButton(tile, minecraft.player, this.leftPos + 116, this.topPos + 68));

        addButton(new OwnerButton(tile, putInTheMiddleX(OwnerButton.SIZE_X), this.topPos + 100));
    }

    @Override
    public void renderOther(MatrixStack matrix, int mouseX, int mouseY) {
        this.renderCookArrow(matrix, this.leftPos + 79, this.topPos + 44, tile.getCookProgress());
        this.renderCookFlame(matrix, this.leftPos + 56, this.topPos + 46, tile.getFuelProgress());
    }

}