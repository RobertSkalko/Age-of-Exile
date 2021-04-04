package com.robertx22.age_of_exile.vanilla_mc.blocks.cooking_station;

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

public class CookingScreen extends ModificationGui<CookingContainer, CookingTile> {

    // This is the resource location for the background image
    private static final Identifier texture = new Identifier(Ref.MODID, "textures/gui/cooking/cooking_station.png");

    public CookingScreen(CookingContainer cont, PlayerInventory invPlayer, MutableText comp) {
        super(texture, cont, invPlayer, new LiteralText(""), CookingTile.class);
        backgroundWidth = 176;
        backgroundHeight = 207;
    }

    @Override
    protected void init() {
        super.init();

        List<Text> list = new ArrayList<>();

        this.addButton(new HelpButton(list, this.x + 5, this.y + 5));

        addButton(new CraftRequirementButton(tile, client.player, this.x + 116, this.y + 68));

        addButton(new OwnerButton(tile, putInTheMiddleX(OwnerButton.SIZE_X), this.y + 100));
    }

    @Override
    public void renderOther(MatrixStack matrix, int mouseX, int mouseY) {
        this.renderCookArrow(matrix, this.x + 79, this.y + 44, tile.getCookProgress());
        this.renderCookFlame(matrix, this.x + 56, this.y + 46, tile.getFuelProgress());
    }

}