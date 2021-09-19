package com.robertx22.age_of_exile.player_skills.items.inscribing.teleports;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.player_skills.items.inscribing.BaseTpItem;
import com.robertx22.library_of_exile.utils.TeleportUtils;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpawnTeleportItem extends BaseTpItem {

    @Override
    public ItemStack onDoneUsing(ItemStack stack, World world, ServerPlayerEntity user) {
        try {
            BlockPos spawn = user.getRespawnPosition();

            if (spawn != null && user.getRespawnDimension()
                .location()
                .equals(world.dimension()
                    .location())) {
                stack.shrink(1);

                TeleportUtils.teleport(user, spawn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stack;
    }

    @Override
    public String locNameForLangFile() {
        return "Spawn Teleport Scroll";
    }

    @Override
    public ShapelessRecipeBuilder getRecipe() {
        ShapelessRecipeBuilder fac = ShapelessRecipeBuilder.shapeless(this);
        fac.requires(Items.PAPER);
        fac.requires(Items.ENDER_PEARL);
        fac.requires(ModRegistry.TIERED.INK_TIER_MAP.get(SkillItemTier.TIER0));
        return fac.unlockedBy("player_level", trigger());
    }
}
