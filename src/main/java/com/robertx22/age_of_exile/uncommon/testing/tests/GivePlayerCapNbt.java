package com.robertx22.age_of_exile.uncommon.testing.tests;

import com.robertx22.age_of_exile.uncommon.testing.CommandTest;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;

public class GivePlayerCapNbt extends CommandTest {

    @Override
    public void run(ServerPlayerEntity player) {

        CompoundNBT tag = new CompoundNBT();
        player.saveWithoutId(tag);

        System.out.print(tag.toString());
    }

    @Override
    public String GUID() {
        return "print_player_nbt";
    }
}
