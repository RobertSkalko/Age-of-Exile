package com.robertx22.age_of_exile.gui.screens;

import com.robertx22.age_of_exile.a_libraries.curios.MyCurioUtils;
import com.robertx22.age_of_exile.a_libraries.curios.RefCurio;
import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class PlayerGearButton extends TexturedButtonWidget {

    public static int xSize = 99;
    public static int ySize = 80;

    static Identifier TEX = new Identifier(Ref.MODID, "textures/gui/player_gear.png");
    BaseScreen screen;
    PlayerEntity player;

    public PlayerGearButton(PlayerEntity player, BaseScreen screen, int xPos, int yPos) {
        super(xPos, yPos, xSize, ySize, 0, 0, ySize + 1, TEX, (button) -> {
        });
        this.player = player;
        this.screen = screen;

        addItemButton(MyCurioUtils.get(RefCurio.NECKLACE, player, 0), 0, 4);
        addItemButton(MyCurioUtils.get(RefCurio.RING, player, 0), 0, 22);
        addItemButton(MyCurioUtils.get(RefCurio.RING, player, 1), 0, 40);

        addItemButton(player.getEquippedStack(EquipmentSlot.HEAD), 81, 4);
        addItemButton(player.getEquippedStack(EquipmentSlot.CHEST), 81, 22);
        addItemButton(player.getEquippedStack(EquipmentSlot.LEGS), 81, 40);
        addItemButton(player.getEquippedStack(EquipmentSlot.FEET), 81, 58);

        // addItemButton(player.getEquippedStack(EquipmentSlot.MAINHAND), 58, 69);
        //addItemButton(player.getEquippedStack(EquipmentSlot.OFFHAND), 179, 69);

    }

    @Override
    public void renderButton(MatrixStack matrix, int x, int y, float ticks) {
        super.renderButton(matrix, x, y, ticks);

        String str = "Level: " + Load.Unit(player)
            .getLevel();

        MinecraftClient mc = MinecraftClient.getInstance();

        // player 3d view
        InventoryScreen.drawEntity(this.x + 50, this.y + 72, 30, this.x - x, this.y - y, player);
        mc.textRenderer.draw(matrix, str, this.x + xSize / 2 - mc.textRenderer.getWidth(str) / 2, this.y + 5, Formatting.YELLOW.getColorValue());
        // player 3d view

    }

    private void addItemButton(ItemStack stack, int x, int y) {
        screen.publicAddButton(new ItemSlotButton(stack, this.x + x, this.y + y));
    }

}
