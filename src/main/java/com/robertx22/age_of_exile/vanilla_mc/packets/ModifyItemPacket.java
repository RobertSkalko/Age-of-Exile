package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.BaseModificationStation;
import com.robertx22.library_of_exile.main.MyPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class ModifyItemPacket extends MyPacket<ModifyItemPacket> {

    public static ModifyItemPacket EMPTY = new ModifyItemPacket();

    BlockPos pos;

    int num = 0;

    public ModifyItemPacket() {

    }

    public ModifyItemPacket(BlockPos pos, int num) {
        this.pos = pos;
        this.num = num;
    }

    public ModifyItemPacket(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public void loadFromData(PacketBuffer buf) {
        this.pos = buf.readBlockPos();
        this.num = buf.readInt();
    }

    @Override
    public void saveToData(PacketBuffer buf) {
        buf.writeBlockPos(pos);
        buf.writeInt(num);
    }

    @Override
    public void onReceived(Context ctx) {

        try {
            BaseModificationStation modify = (BaseModificationStation) ctx.getPlayer().level.getBlockEntity(pos);
            modify.modifyItem(num, ctx.getPlayer());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public MyPacket<ModifyItemPacket> newInstance() {
        return new ModifyItemPacket();
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(Ref.MODID, "modifyitem");
    }

}