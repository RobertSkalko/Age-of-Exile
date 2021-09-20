package com.robertx22.age_of_exile.vanilla_mc.blocks.salvage_station;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.robertx22.age_of_exile.gui.buttons.OwnerButton;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.vanilla_mc.blocks.ModificationGui;
import com.robertx22.library_of_exile.gui.HelpButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class GuiGearSalvage extends ModificationGui<ContainerGearSalvage, TileGearSalvage> {

    // This is the resource location for the background image
    private static final ResourceLocation texture = new ResourceLocation(SlashRef.MODID, "textures/gui/salvage_station.png");

    public GuiGearSalvage(ContainerGearSalvage cont, PlayerInventory invPlayer,
                          IFormattableTextComponent comp) {
        super(texture, cont, invPlayer, new StringTextComponent("Salvage"), TileGearSalvage.class);

        imageWidth = 176;
        imageHeight = 207;
    }

    @Override
    protected void init() {
        super.init();

        List<ITextComponent> list = new ArrayList<>();

        list.add(new StringTextComponent("Here you can salvage gears with Age of Exile stats."));
        list.add(new StringTextComponent("Gears are turned into scraps, which is used to fuel gear."));

        list.add(new StringTextComponent("There are some secret recipes."));

        this.addButton(new HelpButton(list, this.leftPos + this.imageWidth - 25, this.topPos + 5));

        addButton(new OwnerButton(tile, putInTheMiddleX(OwnerButton.SIZE_X), this.topPos + 10));

    }

    @Override
    public void renderOther(MatrixStack matrix, int mouseX, int mouseY) {
        this.renderCookArrow(matrix, this.leftPos + 79, this.topPos + 58, tile.getCookProgress());
        this.renderCookFlame(matrix, this.leftPos + 80, this.topPos + 79, tile.getFuelProgress());
    }
}