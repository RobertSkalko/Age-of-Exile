package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.main.Ref;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class SendActionToClient extends MyPacket<SendActionToClient> {

    public SendActionToClient(BlockPos pos, Action action) {
        this.pos = pos;
        this.action = action;
    }

    public enum Action {

        OPEN_MAPS_GUI {
            @Override
            public void execute(PlayerEntity player, BlockPos pos) {
                ClientOnly.openMapsScreen(pos);
            }
        };

        public abstract void execute(PlayerEntity player, BlockPos pos);
    }

    public BlockPos pos;
    public Action action;

    public SendActionToClient() {

    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(Ref.MODID, "send_action_to_client");
    }

    @Override
    public void loadFromData(PacketBuffer tag) {
        this.action = tag.readEnum(Action.class);
        this.pos = tag.readBlockPos();
    }

    @Override
    public void saveToData(PacketBuffer tag) {
        tag.writeEnum(action);
        tag.writeBlockPos(pos);
    }

    @Override
    public void onReceived(NetworkEvent.Context ctx) {
        this.action.execute(ctx.getPlayer(), pos);
    }

    @Override
    public MyPacket<SendActionToClient> newInstance() {
        return new SendActionToClient();
    }
}
