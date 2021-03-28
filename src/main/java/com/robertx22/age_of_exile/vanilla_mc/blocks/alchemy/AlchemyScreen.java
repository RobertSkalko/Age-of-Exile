package com.robertx22.age_of_exile.vanilla_mc.blocks.alchemy;

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

public class AlchemyScreen extends ModificationGui<AlchemyContainer, AlchemyTile> {

    // This is the resource location for the background image
    private static final Identifier texture = new Identifier(Ref.MODID, "textures/gui/alchemy/alchemy_station.png");

    public AlchemyScreen(AlchemyContainer cont, PlayerInventory invPlayer, MutableText comp) {
        super(texture, cont, invPlayer, new LiteralText(""), AlchemyTile.class);
        backgroundWidth = 176;
        backgroundHeight = 207;
    }

    @Override
    protected void init() {
        super.init();

        List<Text> list = new ArrayList<>();

        this.addButton(new HelpButton(list, this.x + 5, this.y + 5));

        addButton(new CraftRequirementButton(tile, client.player, this.x + 81, this.y + 92));

    }

    @Override
    public void renderOther(MatrixStack matrix, int mouseX, int mouseY) {
        renderAlchemyArrow(matrix, this.x + 98, this.y + 62, tile.getCookProgress());
    }

}
