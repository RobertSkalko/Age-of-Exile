package com.robertx22.age_of_exile.dimension.item;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import com.robertx22.library_of_exile.utils.SoundUtils;
import com.robertx22.library_of_exile.utils.TeleportUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

public class TeleportBackItem extends AutoItem {

    public TeleportBackItem() {
        super(new Properties().tab(CreativeTabs.MyModTab)
            .stacksTo(1));
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
    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity player) {

        stack.shrink(1);

        if (!world.isClientSide) {
            if (WorldUtils.isMapWorldClass(world)) {
                BlockPos pos = Load.playerRPGData((PlayerEntity) player).maps.tel_pos.above();
                TeleportUtils.teleport((ServerPlayerEntity) player, pos, DimensionType.OVERWORLD_EFFECTS);
                SoundUtils.playSound(player, SoundEvents.PORTAL_TRAVEL, world.random.nextFloat() * 0.4F + 0.8F, 0.25F);
            }
        }

        return stack;
    }

    @Override
    public void onUseTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity player, Hand handIn) {
        ItemStack itemStack = player.getItemInHand(handIn);

        player.startUsingItem(handIn);
        return ActionResult.success(itemStack);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 80;
    }
}
