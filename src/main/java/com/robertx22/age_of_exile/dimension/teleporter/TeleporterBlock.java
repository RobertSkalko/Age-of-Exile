package com.robertx22.age_of_exile.dimension.teleporter;

import com.robertx22.age_of_exile.dimension.item.DungeonKeyItem;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.OpaqueBlock;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.RequestSyncCapToClient;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class TeleporterBlock extends OpaqueBlock implements BlockEntityProvider {

    public TeleporterBlock() {
        super(Settings.of(Material.STONE)
            .strength(5F, 2));
    }

    @Override
    @Deprecated
    public List<ItemStack> getDroppedStacks(BlockState blockstate, LootContext.Builder context) {
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();
        items.add(new ItemStack(this));
        return items;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new TeleportedBlockEntity();
    }

    @Override
    @Deprecated
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player,
                              Hand hand, BlockHitResult ray) {

        ItemStack stack = player.getStackInHand(hand);

        BlockEntity tile = world.getBlockEntity(pos);

        if (tile instanceof TeleportedBlockEntity) {

            if (world.isClient) {
                if (stack.getItem() instanceof DungeonKeyItem) {
                    ClientOnly.openChooseTierScreen(pos);
                } else {
                    Packets.sendToServer(new RequestSyncCapToClient(PlayerCaps.MAPS));
                    ClientOnly.openMapsScreen(pos);
                }
                return ActionResult.SUCCESS;
            } else {
                Load.playerMaps(player)
                    .syncToClient(player);
            }

            return ActionResult.SUCCESS;
        }

        return ActionResult.CONSUME;
    }
}
