package com.robertx22.age_of_exile.player_skills.items.inscribing.teleports;

import com.robertx22.age_of_exile.capability.player.RPGPlayerData;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.player_skills.items.inscribing.BaseTpItem;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.utils.TeleportUtils;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class DeathTeleportItem extends BaseTpItem {

    @Override
    public ItemStack onDoneUsing(ItemStack stack, World world, ServerPlayerEntity user) {

        try {

            RPGPlayerData data = Load.playerRPGData(user);

            if (data.deathStats.deathPos != null && data.deathStats.deathDim != null && data.deathStats.deathDim
                .equals(world.getRegistryKey()
                    .getValue()
                    .toString())) {
                stack.decrement(1);

                TeleportUtils.teleport(user, data.deathStats.deathPos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stack;
    }

    @Override
    public String locNameForLangFile() {
        return "Death Teleport Scroll";
    }

    @Override
    public ShapelessRecipeJsonFactory getRecipe() {
        ShapelessRecipeJsonFactory fac = ShapelessRecipeJsonFactory.create(this);
        fac.input(Items.PAPER);
        fac.input(Items.ENDER_EYE);
        fac.input(ModRegistry.TIERED.INK_TIER_MAP.get(SkillItemTier.TIER0));
        return fac.criterion("player_level", trigger());
    }

}
