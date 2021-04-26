package com.robertx22.age_of_exile.dimension.item;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import com.robertx22.library_of_exile.utils.SoundUtils;
import com.robertx22.library_of_exile.utils.TeleportUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class TeleportBackItem extends AutoItem {

    public TeleportBackItem() {
        super(new Settings().group(CreativeTabs.MyModTab)
            .maxCount(1));
    }

    @Override
    public String locNameForLangFile() {
        return "Teleport Back";
    }

    @Override
    public String GUID() {
        return "";
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity player) {

        stack.decrement(1);

        if (!world.isClient) {

            BlockPos pos = Load.playerMaps((PlayerEntity) player).data.tel_pos.up();

            TeleportUtils.teleport((ServerPlayerEntity) player, pos, DimensionType.OVERWORLD_ID);

            SoundUtils.playSound(player, SoundEvents.BLOCK_PORTAL_TRAVEL, 1, 1);
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
    public int getMaxUseTime(ItemStack stack) {
        return 80;
    }
}
