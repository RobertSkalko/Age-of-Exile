package com.robertx22.age_of_exile.uncommon.testing.tests;

import com.robertx22.age_of_exile.uncommon.testing.CommandTest;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;

public class GivePlayerCapNbt extends CommandTest {

    @Override
    public void run(ServerPlayerEntity player) {

        CompoundTag tag = new CompoundTag();
        player.toTag(tag);

        System.out.print(tag.toString());
    }

    @Override
    public String GUID() {
        return "print_player_nbt";
    }
}
