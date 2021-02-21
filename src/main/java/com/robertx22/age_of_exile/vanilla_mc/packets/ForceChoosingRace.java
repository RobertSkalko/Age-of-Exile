package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.library_of_exile.main.MyPacket;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class ForceChoosingRace extends MyPacket<ForceChoosingRace> {

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "force_c_r");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
    }

    @Override
    public void saveToData(PacketByteBuf tag) {
    }

    @Override
    public void onReceived(PacketContext ctx) {
        ClientOnly.openRaceSelection();
    }

    @Override
    public MyPacket<ForceChoosingRace> newInstance() {
        return new ForceChoosingRace();
    }

    public ForceChoosingRace() {

    }
}