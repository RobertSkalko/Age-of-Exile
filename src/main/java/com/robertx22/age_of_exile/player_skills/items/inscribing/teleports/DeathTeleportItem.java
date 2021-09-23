package com.robertx22.age_of_exile.player_skills.items.inscribing.teleports;

import com.robertx22.age_of_exile.capability.player.RPGPlayerData;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.ProfessionItems;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.player_skills.items.inscribing.BaseTpItem;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.utils.TeleportUtils;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class DeathTeleportItem extends BaseTpItem {

    @Override
    public ItemStack onDoneUsing(ItemStack stack, World world, ServerPlayerEntity user) {

        try {

            RPGPlayerData data = Load.playerRPGData(user);

            if (data.deathStats.deathPos != null && data.deathStats.deathDim != null && data.deathStats.deathDim
                .equals(world.dimension()
                    .location()
                    .toString())) {
                stack.shrink(1);

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
    public ShapelessRecipeBuilder getRecipe() {
        ShapelessRecipeBuilder fac = ShapelessRecipeBuilder.shapeless(this);
        fac.requires(Items.PAPER);
        fac.requires(Items.ENDER_EYE);
        fac.requires(ProfessionItems.CONDENSED_ESSENCE_MAP.get(SkillItemTier.TIER0)
            .get());
        return fac.unlockedBy("player_level", trigger());
    }

}
