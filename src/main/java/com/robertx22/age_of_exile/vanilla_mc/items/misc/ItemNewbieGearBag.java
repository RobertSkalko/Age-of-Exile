package com.robertx22.age_of_exile.vanilla_mc.items.misc;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulItem;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.VanillaMaterial;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ItemNewbieGearBag extends Item {

    public ItemNewbieGearBag() {
        super(new Properties().tab(CreativeTabs.MyModTab));

    }

    public static void give(PlayerEntity player) {

        ItemStack soul = StatSoulItem.ofAnySlotOfRarity(IRarity.COMMON_ID);
        PlayerUtils.giveItem(soul, player);

        ItemStack sword = Items.STONE_SWORD.getDefaultInstance();
        EnchantedBookItem.addEnchantment(sword, new EnchantmentData(Enchantments.UNBREAKING, 3));
        PlayerUtils.giveItem(sword, player);

        ItemStack staff = SlashItems.GearItems.STAFFS.get(VanillaMaterial.WOOD)
            .get()
            .getDefaultInstance();
        EnchantedBookItem.addEnchantment(staff, new EnchantmentData(Enchantments.UNBREAKING, 3));
        PlayerUtils.giveItem(staff, player);

        ItemStack bow = Items.STONE_SWORD.getDefaultInstance();
        EnchantedBookItem.addEnchantment(bow, new EnchantmentData(Enchantments.UNBREAKING, 3));
        PlayerUtils.giveItem(bow, player);
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {

        if (!worldIn.isClientSide) {
            try {

                give(playerIn);

                playerIn.getItemInHand(handIn)
                    .shrink(1);

                return new ActionResult<ItemStack>(ActionResultType.PASS, playerIn.getItemInHand(handIn));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ActionResult<ItemStack>(ActionResultType.PASS, playerIn.getItemInHand(handIn));
    }

}
