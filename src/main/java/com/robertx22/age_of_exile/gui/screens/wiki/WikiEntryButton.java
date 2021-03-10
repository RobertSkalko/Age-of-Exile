package com.robertx22.age_of_exile.gui.screens.wiki;

import com.robertx22.age_of_exile.gui.screens.ItemSlotButton;
import com.robertx22.age_of_exile.mmorpg.Ref;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class WikiEntryButton extends TexturedButtonWidget {

    public static int xSize = 240;
    public static int ySize = 24;

    static Identifier buttonLoc = new Identifier(Ref.MODID, "textures/gui/wiki/wiki_entry_button.png");

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

        MinecraftClient mc = MinecraftClient.getInstance();

        mc.textRenderer.drawWithShadow(matrix, entry.getName(), this.x + 60, this.y + 6, Formatting.RED.getColorValue());
    }

}

