package com.robertx22.age_of_exile.vanilla_mc.blocks.bases;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.robertx22.age_of_exile.database.data.crafting_req.CraftingReq;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;

public class CraftRequirementButton extends ImageButton {

    public static int xSize = 14;
    public static int ySize = 14;

    static ResourceLocation ANVIL = new ResourceLocation(SlashRef.MODID, "textures/gui/craft.png");
    static ResourceLocation BARRIER = new ResourceLocation(SlashRef.MODID, "textures/gui/barrier.png");
    PlayerEntity player;

    BaseSkillStation station;

    public CraftRequirementButton(BaseSkillStation station, PlayerEntity player, int xPos, int yPos) {
        super(xPos, yPos, xSize, ySize, 0, 0, ySize + 1, new ResourceLocation(""), (button) -> {
        });
        this.player = player;
        this.station = station;

    }

    @Override
    public void renderButton(MatrixStack matrix, int x, int y, float ticks) {

        if (station == null) {
            return;
        }

        CraftingReq req = getReq();

        if (!req.item_id.isEmpty()) {

            Minecraft mc = Minecraft.getInstance();

            //RenderSystem.enableDepthTest();

            mc.getTextureManager()
                .bind(ANVIL);
            blit(matrix, this.x, this.y, 0, 0, xSize, ySize);

            if (!req.meets(player)) {
                mc.getTextureManager()
                    .bind(BARRIER);
                blit(matrix, this.x, this.y, 0, 0, xSize, ySize);
            }
        }
    }

    CraftingReq getReq() {
        ItemStack stack = station.getStackBeingCrafted();
        String key = Registry.ITEM.getKey(stack.getItem())
            .toString();

        if (ExileDB.ItemCraftReq()
            .isRegistered(key)) {

            return ExileDB.ItemCraftReq()
                .get(key);
        }
        return new CraftingReq();
    }

    @Override
    public void renderToolTip(MatrixStack matrix, int x, int y) {
        if (isInside(x, y)) {
            List<ITextComponent> tooltip = new ArrayList<>();
            CraftingReq req = getReq();

            if (req.item_id.isEmpty()) {
                return;
            }

            if (req.meets(player)) {

                tooltip.add(new StringTextComponent("").append(Words.Level.locName())
                    .append(" " + req.lvl + " ")
                    .append(ExileDB.PlayerSkills()
                        .get(req.skill).type_enum.word.locName())
                    .append(" Recipe"));
            } else {
                tooltip.add(new StringTextComponent("Profession Level too low"));

                tooltip.add(new StringTextComponent("Needs ").append(Words.Level.locName())
                    .append(" " + req.lvl + " ")
                    .append(ExileDB.PlayerSkills()
                        .get(req.skill).type_enum.word.locName()));

            }
            GuiUtils.renderTooltip(matrix, tooltip, x, y);

        }
    }

    public boolean isInside(int x, int y) {
        return GuiUtils.isInRect(this.x, this.y, xSize, ySize, x, y);
    }

}
