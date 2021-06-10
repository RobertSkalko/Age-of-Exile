package com.robertx22.age_of_exile.dimension.packets;

import com.robertx22.age_of_exile.dimension.item.DungeonKeyItem;
import com.robertx22.age_of_exile.dimension.player_data.PlayerMapsCap;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.MyPacket;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class StartDelveMapPacket extends MyPacket<StartDelveMapPacket> {

    int tier;
    BlockPos pos;

    public StartDelveMapPacket() {

    }

    public StartDelveMapPacket(int tier, BlockPos pos) {
        this.tier = tier;
        this.pos = pos;
    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "start_delve");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        pos = tag.readBlockPos();
        tier = tag.readInt();
    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeBlockPos(pos);
        tag.writeInt(tier);

    }

    @Override
    public void onReceived(PacketContext ctx) {

        PlayerMapsCap maps = Load.playerMaps(ctx.getPlayer());

        ItemStack stack = ctx.getPlayer()
            .getMainHandStack();

        if (stack.getItem() instanceof DungeonKeyItem && stack.getCount() > 0) {
            int maxlvl = ((DungeonKeyItem) stack.getItem()).tier.levelRange.getMaxLevel();

            maps.initRandomDelveCave(maxlvl, tier);

            stack.decrement(1);

        }

    }

    @Override
    public MyPacket<StartDelveMapPacket> newInstance() {
        return new StartDelveMapPacket();
    }
}

