package com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.weapons;

import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.bases.BaseWeaponItem;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.HashMap;

public class ItemWand extends BaseWeaponItem {
    public static HashMap<Integer, Item> Items = new HashMap<Integer, Item>();

    public ItemWand(int rar) {
        super(rar);
        this.attackSpeed = -2.6F;
    }

    @Override
    public String locNameForLangFile() {
        Rarity rar = Rarities.Gears.get(rarity);
        return rar.textFormatting() + "Staff";
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 0;
    }

    @Override
    public TypedActionResult<ItemStack> use(World worldIn, PlayerEntity player, Hand handIn) {
        ItemStack stack = player.getStackInHand(handIn);
        return new TypedActionResult<>(ActionResult.PASS, stack);
    }

}