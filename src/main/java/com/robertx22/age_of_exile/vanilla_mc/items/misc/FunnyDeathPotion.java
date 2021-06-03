package com.robertx22.age_of_exile.vanilla_mc.items.misc;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.List;

public class FunnyDeathPotion extends AutoItem {

    static class MySource extends DamageSource {

        protected MySource(String name) {
            super(name);
            this.setBypassesArmor();
            this.setOutOfWorld();
        }
    }

    public FunnyDeathPotion() {
        super(new Item.Settings().group(CreativeTabs.MyModTab)
            .maxCount(64));
    }

    public static final DamageSource DMG_SOURCE = (new MySource("death_potion"));

    @Override
    public String GUID() {
        return "potions/death_potion";
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity en) {

        if (en instanceof PlayerEntity) {

            en.damage(DMG_SOURCE, Float.MAX_VALUE);

            stack.decrement(1);
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
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new LiteralText("Are you sure it's a good idea to drink this?"));
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
    public String locNameForLangFile() {
        return "Death Potion";
    }

}

