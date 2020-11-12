package com.robertx22.age_of_exile.player_skills.items.inscribing.teleports;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.player_skills.items.inscribing.BaseTpItem;
import com.robertx22.library_of_exile.utils.TeleportUtils;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpawnTeleportItem extends BaseTpItem {

    @Override
    public ItemStack onDoneUsing(ItemStack stack, World world, ServerPlayerEntity user) {

        try {
            BlockPos spawn = user.getSpawnPointPosition();

            if (spawn != null && user.getSpawnPointDimension()
                .getValue()
                .equals(world.getRegistryKey()
                    .getValue())) {
                stack.decrement(1);

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
    public ShapelessRecipeJsonFactory getRecipe() {
        ShapelessRecipeJsonFactory fac = ShapelessRecipeJsonFactory.create(this);
        fac.input(Items.PAPER);
        fac.input(Items.ENDER_PEARL);
        fac.input(ModRegistry.INSCRIBING.INK_TIER_MAP.get(SkillItemTier.SPIRITUAL));
        return fac.criterion("player_level", trigger());
    }
}
