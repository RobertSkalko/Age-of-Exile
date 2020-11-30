package com.robertx22.age_of_exile.uncommon.testing;

import com.robertx22.age_of_exile.database.data.IGUID;
import net.minecraft.server.network.ServerPlayerEntity;

public abstract class CommandTest implements IGUID {

    public abstract void run(ServerPlayerEntity player);
}
