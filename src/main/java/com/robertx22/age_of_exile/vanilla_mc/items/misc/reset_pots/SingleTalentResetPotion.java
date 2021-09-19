package com.robertx22.age_of_exile.vanilla_mc.items.misc.reset_pots;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class SingleTalentResetPotion extends AutoItem implements IShapedRecipe {

    public SingleTalentResetPotion() {
        super(new Properties().tab(CreativeTabs.MyModTab)
            .stacksTo(64));
    }

    @Override
    public String GUID() {
        return "potions/add_reset_perk_points";
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity player) {

        stack.shrink(1);

        if (player instanceof PlayerEntity) {
            PlayerEntity p = (PlayerEntity) player;
            Load.playerRPGData(p).talents.reset_points += 5;
            p.addItem(new ItemStack(Items.GLASS_BOTTLE));
        }

        return stack;
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity player, Hand handIn) {
        ItemStack itemStack = player.getItemInHand(handIn);
        player.startUsingItem(handIn);
        return ActionResult.success(itemStack);
    }

    @Override
    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 30;
    }

    @Override
    public ShapedRecipeBuilder getRecipe() {
        return shaped(this)
            .define('t', ModRegistry.MISC_ITEMS.T0_DUST())
            .define('v', Items.DIAMOND)
            .define('b', Items.GLASS_BOTTLE)
            .pattern(" v ")
            .pattern("vtv")
            .pattern(" b ")
            .unlockedBy("player_level", trigger());
    }

    @Override
    public String locNameForLangFile() {
        return "Single Talent Reset Potion";
    }

}
