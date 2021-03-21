package com.robertx22.age_of_exile.vanilla_mc.items.misc.reset_pots;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class ResetStatsPotion extends AutoItem implements IShapedRecipe {

    public ResetStatsPotion() {
        super(new Item.Settings().group(CreativeTabs.MyModTab)
            .maxCount(10));
    }

    @Override
    public String GUID() {
        return "potions/reset_stats";
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity player) {

        stack.decrement(1);

        if (player instanceof PlayerEntity) {
            PlayerEntity p = (PlayerEntity) player;
            Load.statPoints(p).data.reset();
            p.giveItemStack(new ItemStack(Items.GLASS_BOTTLE));
        }

        return stack;
    }

    @Override
    public TypedActionResult<ItemStack> use(World worldIn, PlayerEntity player, Hand handIn) {
        ItemStack itemStack = player.getStackInHand(handIn);
        player.setCurrentHand(handIn);
        return TypedActionResult.success(itemStack);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 30;
    }

    @Override
    public ShapedRecipeJsonFactory getRecipe() {
        return shaped(this)
            .input('t', ModRegistry.MISC_ITEMS.T1_DUST())
            .input('v', Items.GOLD_INGOT)
            .input('b', Items.GLASS_BOTTLE)
            .input('c', Items.DIAMOND)
            .pattern("cvc")
            .pattern("vtv")
            .pattern("cbc")
            .criterion("player_level", trigger());
    }

    @Override
    public String locNameForLangFile() {
        return "Stat Reset Potion";
    }
}
