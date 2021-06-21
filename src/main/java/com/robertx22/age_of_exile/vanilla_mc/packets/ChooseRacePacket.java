package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.database.data.races.PlayerRace;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.MyPacket;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class ChooseRacePacket extends MyPacket<ChooseRacePacket> {

    public String race = "";

    public ChooseRacePacket() {

    }

    public ChooseRacePacket(PlayerRace race) {
        this.race = race.GUID();
    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "choose_race");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        race = tag.readString(500);
    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeString(race, 500);
    }

    @Override
    public void onReceived(PacketContext ctx) {
        PlayerEntity p = ctx.getPlayer();

        if (!Load.Unit(p)
            .hasRace()) {
            if (ExileDB.Races()
                .isRegistered(race)) {
                Load.Unit(p)
                    .setRace(race);
            }
        }
    }

    @Override
    public MyPacket<ChooseRacePacket> newInstance() {
        return new ChooseRacePacket();
    }
}

