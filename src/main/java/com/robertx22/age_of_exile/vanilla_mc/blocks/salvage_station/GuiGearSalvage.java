package com.robertx22.age_of_exile.vanilla_mc.blocks.salvage_station;

import com.robertx22.age_of_exile.gui.buttons.OwnerButton;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.vanilla_mc.blocks.ModificationGui;
import com.robertx22.library_of_exile.gui.HelpButton;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

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

        this.addButton(new HelpButton(list, this.x + this.backgroundWidth - 25, this.y + 5));

        addButton(new OwnerButton(tile, putInTheMiddleX(OwnerButton.SIZE_X), this.y + 10));

    }

    @Override
    public void renderOther(MatrixStack matrix, int mouseX, int mouseY) {
        this.renderCookArrow(matrix, this.x + 79, this.y + 58, tile.getCookProgress());
        this.renderCookFlame(matrix, this.x + 80, this.y + 79, tile.getFuelProgress());
    }
}