package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.main.Ref;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class SendActionToClient extends MyPacket<SendActionToClient> {

    public SendActionToClient(BlockPos pos, Action action) {
        this.pos = pos;
        this.action = action;
    }

    public enum Action {
        OPEN_CHOOSE_DIFFICULTY_GUI {
            @Override
            public void execute(PlayerEntity player, BlockPos pos) {
                ClientOnly.openChooseTierScreen(pos);
            }
        },

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
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "send_action_to_client");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        this.action = tag.readEnumConstant(Action.class);
        this.pos = tag.readBlockPos();
    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeEnumConstant(action);
        tag.writeBlockPos(pos);
    }

    @Override
    public void onReceived(PacketContext ctx) {
        this.action.execute(ctx.getPlayer(), pos);
    }

    @Override
    public MyPacket<SendActionToClient> newInstance() {
        return new SendActionToClient();
    }
}
