package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.mmorpg.Ref;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class TileUpdatePacket extends MyPacket<TileUpdatePacket> {

    public BlockPos pos;
    public CompoundTag nbt;

    public TileUpdatePacket() {

    }

    public TileUpdatePacket(BlockEntity be) {
        this.pos = be.getPos();
        this.nbt = be.toTag(new CompoundTag());
    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "givetiledata");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        pos = tag.readBlockPos();
        nbt = tag.readCompoundTag();

    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeBlockPos(pos);
        tag.writeCompoundTag(nbt);

    }

    @Override
    public void onReceived(PacketContext ctx) {
        PlayerEntity player = ctx.getPlayer();
        BlockEntity tile = player.world.getBlockEntity(pos);
        tile.fromTag(player.world.getBlockState(pos), nbt);
    }

    @Override
    public MyPacket<TileUpdatePacket> newInstance() {
        return new TileUpdatePacket();
    }
}
