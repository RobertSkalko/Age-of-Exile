package com.robertx22.age_of_exile.dimension.teleporter;

import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.OpaqueBlock;
import com.robertx22.age_of_exile.vanilla_mc.packets.SendActionToClient;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class TeleporterBlock extends OpaqueBlock implements ITileEntityProvider {

    public TeleporterBlock() {
        super(Properties.of(Material.STONE)
            .strength(5F, 2));
    }

    /*
    @Override
    @Deprecated
    public List<ItemStack> getDroppedStacks(BlockState blockstate, LootContext.Builder context) {
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();
        items.add(new ItemStack(this));
        return items;
    }

     */

    @Override
    public TileEntity newBlockEntity(IBlockReader world) {
        return new TeleportedBlockEntity();
    }

    @Override
    @Deprecated
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player,
                                Hand hand, BlockRayTraceResult ray) {

        ItemStack stack = player.getItemInHand(hand);

        TileEntity tile = world.getBlockEntity(pos);

        if (tile instanceof TeleportedBlockEntity) {

            TeleportedBlockEntity be = (TeleportedBlockEntity) tile;

            if (!world.isClientSide) {

                if (!be.data.activated) {
                    if (be.data.type.isDungeon()) {
                        Load.playerRPGData(player)
                            .createRandomDungeon(ExileDB.Difficulties()
                                .random());
                        be.data.activated = true;
                    }
                }

                Load.playerRPGData(player)
                    .syncToClient(player);

                Packets.sendToClient(player, new SendActionToClient(pos, SendActionToClient.Action.OPEN_MAPS_GUI));

                return ActionResultType.SUCCESS;
            }

            return ActionResultType.SUCCESS;
        }

        return ActionResultType.CONSUME;
    }
}
