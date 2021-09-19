package com.robertx22.age_of_exile.vanilla_mc.items.misc;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class FunnyDeathPotion extends AutoItem {

    static class MySource extends DamageSource {

        protected MySource(String name) {
            super(name);
            this.bypassArmor();
            this.bypassInvul();
        }
    }

    public FunnyDeathPotion() {
        super(new Item.Properties().tab(CreativeTabs.MyModTab)
            .stacksTo(64));
    }

    public static final DamageSource DMG_SOURCE = (new MySource("death_potion"));

    @Override
    public String GUID() {
        return "potions/death_potion";
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity en) {
        if (en instanceof PlayerEntity) {
            en.hurt(DMG_SOURCE, Float.MAX_VALUE);
            stack.shrink(1);
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
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag context) {
        tooltip.add(new StringTextComponent("Are you sure it's a good idea to drink this?"));
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
    public String locNameForLangFile() {
        return "Death Potion";
    }

}

