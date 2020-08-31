package com.robertx22.forgotten_magic.teleport;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class TeleportItem extends Item {

    public TeleportItem() {
        super(new Settings().maxCount(1)
            .group(CreativeTabs.MyModTab));
    }

    @Override
    public Text getName() {
        return new TranslatableText(this.getTranslationKey()).formatted(Formatting.DARK_PURPLE);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {

        tooltip.add(new LiteralText("Right click to teleport to bound altar.").formatted(Formatting.LIGHT_PURPLE));
        tooltip.add(new LiteralText(""));

        tooltip.add(new LiteralText("Right click the altar with this item").formatted(Formatting.LIGHT_PURPLE));
        tooltip.add(new LiteralText("to go back to where you recalled from.").formatted(Formatting.LIGHT_PURPLE));
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        try {
            PlayerEntity playerEntity = user instanceof PlayerEntity ? (PlayerEntity) user : null;
            if (playerEntity instanceof ServerPlayerEntity) {

                TeleportComponent tel = Load.teleport(playerEntity);
                if (tel.data.altar_pos != null) {

                    tel.data.last_recall_pos = user.getBlockPos();

                    EntityUtils.setLoc(user, tel.data.altar_pos);

                } else {
                    playerEntity.sendMessage(new LiteralText("You did not bind to any altar."), false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stack;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 100;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }

    @Override
    public TypedActionResult<ItemStack> use(World worldIn, PlayerEntity player, Hand handIn) {
        ItemStack itemStack = player.getStackInHand(handIn);
        player.setCurrentHand(handIn);
        return TypedActionResult.success(itemStack);

    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        Random random = world.random;
        BlockPos pos = user.getBlockPos();

        for (int i = 0; i < 6; ++i) {
            int j = random.nextInt(2) * 2 - 1;
            int k = random.nextInt(2) * 2 - 1;
            double d = (double) pos.getX() + 0.5D + 0.25D * (double) j;
            double e = (double) ((float) pos.getY() + random.nextFloat());
            double f = (double) pos.getZ() + 0.5D + 0.25D * (double) k;
            double g = (double) (random.nextFloat() * (float) j);
            double h = ((double) random.nextFloat() - 0.5D) * 0.125D;
            double l = (double) (random.nextFloat() * (float) k);
            world.addParticle(ParticleTypes.PORTAL, d, e, f, g, h, l);
        }
    }

}
