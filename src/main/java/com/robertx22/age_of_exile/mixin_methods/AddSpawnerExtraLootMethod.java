package com.robertx22.age_of_exile.mixin_methods;

import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.MasterLootGen;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

public class AddSpawnerExtraLootMethod {

    public static void hookLoot(LootContext context, CallbackInfoReturnable<List<ItemStack>> ci) {

        try {
            if (!context.hasParameter(LootContextParameters.BLOCK_STATE)) {
                return;
            }
            if (!context.hasParameter(LootContextParameters.TOOL)) {
                return;
            }
            if (!context.hasParameter(LootContextParameters.ORIGIN)) {
                return;
            }
            if (!context.hasParameter(LootContextParameters.THIS_ENTITY)) {
                return;
            }
            if (context.get(LootContextParameters.BLOCK_STATE)
                .getBlock() != Blocks.SPAWNER) {
                return;
            }

            Entity en = context.get(LootContextParameters.THIS_ENTITY);

            PlayerEntity player = null;
            if (en instanceof PlayerEntity) {
                player = (PlayerEntity) en;
            }
            if (player == null) {
                return;
            }

            ItemStack stack = context.get(LootContextParameters.TOOL);
            if (EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) != 0) {
                return;
            }

            Vec3d p = context.get(LootContextParameters.ORIGIN);
            BlockPos pos = new BlockPos(p.x, p.y, p.z);

            LootInfo info = LootInfo.ofSpawner(player, context.getWorld(), pos);
            info.multi += 15;
            List<ItemStack> list = MasterLootGen.generateLoot(info);

            ci.getReturnValue()
                .addAll(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
