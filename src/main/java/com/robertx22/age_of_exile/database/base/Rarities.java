package com.robertx22.age_of_exile.database.base;

import com.robertx22.age_of_exile.database.data.rarities.RarityTypeEnum;
import com.robertx22.age_of_exile.database.data.rarities.containers.GearRarities;
import com.robertx22.age_of_exile.database.data.rarities.containers.MobRarities;
import com.robertx22.age_of_exile.vanilla_mc.packets.RarityPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.server.network.ServerPlayerEntity;

public class Rarities {

    public static final GearRarities Gears = new GearRarities();
    public static final MobRarities Mobs = new MobRarities();

    public static void sendAllPacketsToClientOnLogin(ServerPlayerEntity player) {
        Packets.sendToClient(player, new RarityPacket(RarityTypeEnum.GEAR));
    }
}
