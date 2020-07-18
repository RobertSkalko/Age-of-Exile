package com.robertx22.mine_and_slash.vanilla_mc.blocks.bases;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.container.NameableContainerFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;


import net.minecraftforge.fml.network.NetworkHooks;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseInventoryBlock extends NonFullBlock {

    protected BaseInventoryBlock(Settings prop) {
        super(prop);

    }

    @Override
    @Deprecated
    public List<ItemStack> getDroppedStacks(BlockState blockstate, LootContext.Builder context) {

        ArrayList<ItemStack> items = new ArrayList<ItemStack>();
        items.add(new ItemStack(this));

        BlockEntity tileentity = context.getNullable(LootContextParameters.BLOCK_ENTITY);

        if (tileentity instanceof BaseTile) {

            BaseTile inv = (BaseTile) tileentity;

            for (ItemStack stack : inv.itemStacks) {
                if (stack.isEmpty() == false) {
                    items.add(stack);
                }
            }

            //inv.itemStacks = new ItemStack[inv.itemStacks.length];// destroy the stacks
            //Arrays.fill(inv.itemStacks, ItemStack.EMPTY); tterag says use the container canInteractWith method instead
            // unsure if i should use both to be extra sure

        }

        return items;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView worldIn, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView reader, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isSimpleFullBlock(BlockState state, BlockView worldIn, BlockPos pos) {
        return false;
    }

    @Override
    public boolean hasBlockEntity( ) {
        return true;
    }

    @Override
    @Deprecated
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player,
                                             Hand hand, BlockHitResult ray) {
        if (world.isClient) {
            return ActionResult.CONSUME;
        }

        BlockEntity tile = world.getBlockEntity(pos);

        if (tile instanceof BaseTile) {

            NetworkHooks.openGui((ServerPlayerEntity) player, (NameableContainerFactory) tile, extraData -> {
                extraData.writeBlockPos(tile.getPos());
            });
            return ActionResult.SUCCESS;
        }

        return ActionResult.CONSUME;
    }

}
