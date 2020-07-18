package com.robertx22.mine_and_slash.vanilla_mc.items.bags;

import com.robertx22.mine_and_slash.database.base.CreativeTabs;
import com.robertx22.mine_and_slash.vanilla_mc.items.ItemSingle;
import com.robertx22.mine_and_slash.uncommon.item_filters.bases.ItemFilterGroup;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.container.NameableContainerFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;





import java.util.List;

public abstract class BaseBagItem extends Item {

    public abstract ItemFilterGroup filterGroup();

    public abstract NameableContainerFactory getNamedContainer(ItemStack stack);

    public int size = 9 * 2;

    public BaseBagItem(String name) {
        super(new ItemSingle().group(CreativeTabs.MyModTab));

    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World worldIn, List<Text> tooltip,
                               TooltipContext flagIn) {

        tooltip.add(TooltipUtils.color(Formatting.GREEN, Words.PicksUpItemsAuto.locName()));
        tooltip.add(TooltipUtils.color(Formatting.YELLOW, Words.HoldToPreventPickup.locName()));

    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player,  Hand hand) {
        if (!world.isClient) {
            if (hand == Hand.MAIN_HAND && player.getStackInHand(hand)
                .getItem() instanceof BaseBagItem) {
                player.openContainer(getNamedContainer(player.getStackInHand(hand)));
            }
        }
        return new TypedActionResult<ItemStack>(ActionResult.SUCCESS, player.getStackInHand(hand));
    }

    public BaseInventory getInventory(ItemStack bag, ItemStack stack) {
        if (stack.getCount() > 0 && filterGroup().anyMatchesFilter(stack)) {
            return newInventory(bag);
        }
        return null;

    }


    public BaseInventory newInventory(ItemStack bag) {
        return new BaseInventory(bag);
    }

}