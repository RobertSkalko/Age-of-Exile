package com.robertx22.age_of_exile.gui.screens.favor;

import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.gui.bases.INamedScreen;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public class FavorScreen extends BaseScreen implements INamedScreen {

    private static final Identifier TEX = new Identifier(Ref.MODID, "textures/gui/favor.png");

    static int sizeX = 256;
    static int sizeY = 166;

    MinecraftClient mc = MinecraftClient.getInstance();

    protected FavorScreen() {
        super(sizeX, sizeY);
    }

    @Override
    public Identifier iconLocation() {
        return new Identifier(Ref.MODID, "textures/gui/main_hub/icons/favor.png");
    }

    @Override
    public Words screenName() {
        return Words.AzunasFavor;
    }
}
