package com.robertx22.age_of_exile.gui.screens.wiki;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.library_of_exile.gui.ItemSlotButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;

public class WikiEntryButton extends ImageButton {

    public static int xSize = 240;
    public static int ySize = 24;

    static ResourceLocation buttonLoc = new ResourceLocation(Ref.MODID, "textures/gui/wiki/wiki_entry_button.png");

    WikiEntry entry;

    public WikiEntryButton(WikiScreen screen, WikiEntry entry, int xPos, int yPos) {
        super(xPos, yPos, xSize, ySize, 0, 0, 0, buttonLoc, (button) -> {
        });
        this.entry = entry;

        ItemSlotButton item = new ItemSlotButton(entry.createMainStack(), this.x + 7, this.y + 3);
        item.renderFancyBorder = true;
        screen.publicAddButton(item);

        screen.publicAddButton(new ExtraInfoButton(entry.getExtraInfo(), this.x + 30, this.y + 3));

    }

    @Override
    public void renderButton(MatrixStack matrix, int x, int y, float ticks) {
        super.renderButton(matrix, x, y, ticks);

        Minecraft mc = Minecraft.getInstance();

        mc.font.drawShadow(matrix, entry.getName(), this.x + 60, this.y + 6, this.entry.getFormat()
            .getColor());
    }

}

