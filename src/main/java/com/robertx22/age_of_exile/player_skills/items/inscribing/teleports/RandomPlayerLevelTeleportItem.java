package com.robertx22.age_of_exile.player_skills.items.inscribing.teleports;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.player_skills.items.inscribing.BaseTpItem;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import com.robertx22.library_of_exile.utils.TeleportUtils;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class RandomPlayerLevelTeleportItem extends BaseTpItem {

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.getDimension()
            .hasCeiling()) {
            user.sendMessage(new LiteralText("Can't be used in dimensions with a ceiling"), false);
            return TypedActionResult.fail(user.getStackInHand(hand));
        }

        return super.use(world, user, hand);
    }

    @Override
    public ItemStack onDoneUsing(ItemStack stack, World world, ServerPlayerEntity user) {

        try {

            BlockPos pos = getRandomZoneForLevel(world, Load.Unit(user)
                .getLevel());

            if (pos != null) {
                stack.decrement(1);
                TeleportUtils.teleport(user, pos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stack;
    }

    public static BlockPos getRandomZoneForLevel(World world, int lvl) {

        int blocks = (int) (LevelUtils.getBlocksForEachLevelDistance((ServerWorld) world) * lvl);

        int split = RandomUtils.RandomRange(0, blocks);

        int x = RandomUtils.RandomRange(0, split);
        blocks -= x;
        int z = blocks;

        Chunk chunk = world.getChunk(x / 16, z / 16);

        int y = world.getTopY(Heightmap.Type.WORLD_SURFACE, x, z);

        return new BlockPos(x, y, z);

    }

    @Override
    public String locNameForLangFile() {
        return "Random Zone Teleport Scroll";
    }

    @Override
    public ShapelessRecipeJsonFactory getRecipe() {
        ShapelessRecipeJsonFactory fac = ShapelessRecipeJsonFactory.create(this);
        fac.input(Items.PAPER);
        fac.input(Items.GOLD_INGOT);
        fac.input(ModRegistry.INSCRIBING.INK_TIER_MAP.get(SkillItemTier.TIER0));
        return fac.criterion("player_level", trigger());
    }
}
