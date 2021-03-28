package com.robertx22.age_of_exile.vanilla_mc.blocks.bases;

import com.robertx22.age_of_exile.database.data.crafting_req.CraftingReq;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class CraftRequirementButton extends TexturedButtonWidget {

    public static int xSize = 14;
    public static int ySize = 14;

    static Identifier ANVIL = new Identifier(Ref.MODID, "textures/gui/craft.png");
    static Identifier BARRIER = new Identifier(Ref.MODID, "textures/gui/barrier.png");
    PlayerEntity player;

    BaseSkillStation station;

    public CraftRequirementButton(BaseSkillStation station, PlayerEntity player, int xPos, int yPos) {
        super(xPos, yPos, xSize, ySize, 0, 0, ySize + 1, new Identifier(""), (button) -> {
        });
        this.player = player;
        this.station = station;

    }

    @Override
    public void renderButton(MatrixStack matrix, int x, int y, float ticks) {

        CraftingReq req = getReq();

        if (!req.item_id.isEmpty()) {

            MinecraftClient mc = MinecraftClient.getInstance();

            //RenderSystem.enableDepthTest();

            mc.getTextureManager()
                .bindTexture(ANVIL);
            drawTexture(matrix, this.x, this.y, 0, 0, xSize, ySize);

            if (!req.meets(player)) {
                mc.getTextureManager()
                    .bindTexture(BARRIER);
                drawTexture(matrix, this.x, this.y, 0, 0, xSize, ySize);
            }
        }
    }

    CraftingReq getReq() {
        ItemStack stack = station.getStackBeingCrafted();
        String key = Registry.ITEM.getId(stack.getItem())
            .toString();

        if (Database.ItemCraftReq()
            .isRegistered(key)) {

            return Database.ItemCraftReq()
                .get(key);
        }
        return new CraftingReq();
    }

    @Override
    public void renderToolTip(MatrixStack matrix, int x, int y) {
        if (isInside(x, y)) {
            List<Text> tooltip = new ArrayList<>();
            CraftingReq req = getReq();

            if (req.item_id.isEmpty()) {
                return;
            }

            if (req.meets(player)) {

                tooltip.add(new LiteralText("").append(Words.Level.locName())
                    .append(" " + req.lvl + " ")
                    .append(Database.PlayerSkills()
                        .get(req.skill).type_enum.word.locName())
                    .append(" Recipe"));
            } else {
                tooltip.add(new LiteralText("Profession Level too low"));

                tooltip.add(new LiteralText("Needs ").append(Words.Level.locName())
                    .append(" " + req.lvl + " ")
                    .append(Database.PlayerSkills()
                        .get(req.skill).type_enum.word.locName()));

            }
            GuiUtils.renderTooltip(matrix, tooltip, x, y);

        }
    }

    public boolean isInside(int x, int y) {
        return GuiUtils.isInRect(this.x, this.y, xSize, ySize, x, y);
    }

}
