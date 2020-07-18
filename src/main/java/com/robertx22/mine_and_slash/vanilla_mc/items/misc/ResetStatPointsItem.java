package com.robertx22.mine_and_slash.vanilla_mc.items.misc;

import com.robertx22.mine_and_slash.database.base.CreativeTabs;
import com.robertx22.mine_and_slash.database.data.IGUID;
import com.robertx22.mine_and_slash.database.data.currency.base.IShapedRecipe;
import com.robertx22.mine_and_slash.mmorpg.ModRegistry;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;
import com.robertx22.mine_and_slash.vanilla_mc.items.SimpleMatItem;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.UseAction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class ResetStatPointsItem extends Item implements IShapedRecipe, IAutoLocName, IGUID {

    public ResetStatPointsItem() {
        super(new Settings().group(CreativeTabs.MyModTab)
            .maxCount(10));
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity player) {

        stack.decrement(1);

        if (player instanceof PlayerEntity) {
            PlayerEntity p = (PlayerEntity) player;
            Load.statPoints(p)
                .resetStats();
            p.giveItemStack(new ItemStack(Items.GLASS_BOTTLE));
        }

        return stack;
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
        return shaped(ModRegistry.MISC_ITEMS.RESET_STATS_POTION)
            .input('#', SimpleMatItem.INFUSED_IRON)
            .input('t', ModRegistry.MISC_ITEMS.MAGIC_ESSENCE)
            .input('v', Items.GOLD_INGOT)
            .input('b', Items.GLASS_BOTTLE)
            .input('c', Items.COAL)
            .pattern("#v#")
            .pattern("vtv")
            .pattern("cbc")
            .criterion("player_level", trigger());
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public String locNameLangFileGUID() {
        return Registry.ITEM.getId(this)
            .toString();
    }

    @Override
    public String locNameForLangFile() {
        return "Reset Stat Points Potion";
    }

    @Override
    public String GUID() {
        return "potions/reset_stats_potion";
    }
}
