package com.robertx22.age_of_exile.mixin_methods;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

public class RenderSpellIcon {
    public static void drawMyGlintEnd(MatrixStack matrices, int x, int y, ItemStack stack) {

        try {
            SkillGemData gem = StackSaving.SKILL_GEMS.loadFrom(stack);

            if (gem != null) {

                String spellid = gem.getSkillGem().spell_id;

                if (Database.Spells()
                    .isRegistered(spellid)) {

                    Spell spell = Database.Spells()
                        .get(spellid);

                    // RenderSystem.enableBlend();
                    RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1F);

                    MinecraftClient.getInstance()
                        .getTextureManager()
                        .bindTexture(spell.getIconLoc());

                    DrawableHelper.drawTexture(matrices, x, y, 0, 0, 16, 16, 16, 16);
                    RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1F);
                    //RenderSystem.disableBlend();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
